package cc.service.newsletter;

import cc.common.model.Book;
import cc.common.model.Category;
import cc.common.model.CategoryPathElement;
import cc.common.model.CategoryNode;
import cc.common.model.CategoryPath;
import cc.common.model.Newsletter;
import cc.common.model.Notification;
import cc.common.model.Subscriber;
import cc.service.book.BookService;
import cc.service.category.CategoryService;
import cc.service.subscriber.SubscriberService;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Maxim Neverov
 */
@Slf4j
@Service
public class NewsletterServiceImpl implements NewsletterService {

    private final BookService bookService;
    private final SubscriberService subscriberService;
    private final CategoryService categoryService;

    @Inject
    public NewsletterServiceImpl(BookService bookService,
                                 SubscriberService subscriberService,
                                 CategoryService categoryService) {
        this.bookService = bookService;
        this.subscriberService = subscriberService;
        this.categoryService = categoryService;
    }

    @Override
    public List<Newsletter> getNewsletters() {
        // get all categories to build a tree
        List<Category> categories = categoryService.getCategories();
        List<CategoryNode> trees = buildTrees(categories);

        // get all subscribers and build their paths
        List<Subscriber> subscribers = subscriberService.getSubscribers();
        Map<String, List<CategoryPath>> emailToPaths = new HashMap<>();

        for (Subscriber subscriber : subscribers) {
            List<CategoryPath> pathsForSubscriber = getPathsForCategories(subscriber.getCategoryCodes(), trees);
            emailToPaths.put(subscriber.getEmail(), pathsForSubscriber);
        }

        return getNewsletters(emailToPaths);
    }

    private List<Newsletter> getNewsletters(Map<String, List<CategoryPath>> emailToPaths) {
        List<Newsletter> result = new ArrayList<>();

        for (Map.Entry<String, List<CategoryPath>> entry : emailToPaths.entrySet()) {
            List<CategoryPath> paths = entry.getValue();

            // these categories are not the same as just a subscriber's categories: they contain all child categories as well
            Set<String> categoryCodes = paths.stream()
                    .map(CategoryPath::getPath)
                    .flatMap(List::stream)
                    .map(CategoryPathElement::getCode)
                    .collect(Collectors.toSet());

            Map<String, List<Book>> booksWithCategories = bookService.getBooksWithCategories(categoryCodes);

            Map<Book, List<CategoryPath>> booksMap = getBookListMap(paths, booksWithCategories);
            Newsletter newsletter = new Newsletter();
            newsletter.setRecipient(entry.getKey());

            List<Notification> notifications = booksMap.entrySet().stream()
                    .map(e -> new Notification(e.getKey().getTitle(), e.getValue()))
                    .collect(Collectors.toList());
            newsletter.setNotifications(notifications);
            result.add(newsletter);
        }

        return result;
    }

    // Visible for testing
    Map<Book, List<CategoryPath>> getBookListMap(List<CategoryPath> paths, Map<String, List<Book>> booksWithCategories) {
        Map<Book, List<CategoryPath>> result = new HashMap<>();
        Set<String> processedCategories = new HashSet<>();
        for (CategoryPath path : paths) {
            List<CategoryPathElement> pathCategories = path.getPath();
            for (int i = 0; i < pathCategories.size(); i++) {
                CategoryPathElement currentCategory = pathCategories.get(i);
                if (processedCategories.contains(currentCategory.getCode())) {
                    continue;
                }
                List<Book> categoryBooks = booksWithCategories.get(currentCategory.getCode());
                if (categoryBooks == null) {
                    continue;
                }
                for (Book categoryBook : categoryBooks) {
                    List<CategoryPathElement> bookPath = pathCategories.subList(0, i + 1);
                    List<CategoryPath> bookPaths = result.get(categoryBook);
                    if (bookPaths == null) {
                        bookPaths = new ArrayList<>();
                        bookPaths.add(new CategoryPath(bookPath));
                        result.put(categoryBook, bookPaths);
                    } else {
                        bookPaths.add(new CategoryPath(bookPath));
                    }
                }
                processedCategories.add(currentCategory.getCode());
            }
        }
        return result;
    }

    private List<CategoryPath> getPathsForCategories(final List<String> desiredCategories, final List<CategoryNode> trees) {
        return trees.stream()
                .map(it -> getPathsForCategories(desiredCategories, it))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    /**
     * Visible for testing.
     * Simple BFS algorithm to find all categories paths in a given tree
     *
     * @param desiredCategories Subscriber's desired categories
     * @param root A category tree to traverse
     * @return List of paths for desired categories if found, empty list otherwise
     */
    List<CategoryPath> getPathsForCategories(List<String> desiredCategories, CategoryNode root) {
        final List<CategoryPath> result = new ArrayList<>();
        if (desiredCategories == null || desiredCategories.isEmpty() || root == null) {
            return result;
        }
        final Queue<CategoryNode> queue = new LinkedList<>();

        // counter to stop tree traversing when all desired categories are found
        int categoriesNumberToFind = desiredCategories.size();
        do {
            String currentCategoryCode = root.getCategoryCode();

            if (desiredCategories.contains(currentCategoryCode)) {
                result.addAll(getAllPathsStartedBy(root));
                categoriesNumberToFind -= 1;
            } else {
                queue.addAll(root.getChildCategories());
            }

            if (!queue.isEmpty()) {
                root = queue.poll();
            }
        } while (!queue.isEmpty() && categoriesNumberToFind > 0);

        return result;
    }

    /**
     * Visible for testing.
     * Simple DFS algorithm with tracking already processed nodes.
     *
     * @param root Tree root
     * @return List of categories paths for a given tree
     */
    List<CategoryPath> getAllPathsStartedBy(CategoryNode root) {
        final List<CategoryPath> result = new ArrayList<>();
        final Deque<PathHolder> stack = new ArrayDeque<>();
        List<CategoryPathElement> prefix = null;
        while (root != null || !stack.isEmpty()) {
            if (!stack.isEmpty()) {
                PathHolder pathHolder = stack.pop();
                root = pathHolder.getNode();
                prefix = pathHolder.getPathPrefix();
            }

            if (root == null) {
                break;
            }

            // create a separate path for each node from stack
            CategoryPath path = new CategoryPath(prefix);
            while (root != null) {
                // add current node's category code to current path
                path.addCategory(new CategoryPathElement(root.getCategoryCode(), root.getCategoryTitle()));

                if (root.hasChildren()) {
                    // add all children except first to the stack to iterate later
                    List<CategoryNode> childCategories = root.getChildCategories();
                    childCategories.stream().skip(1).forEach(it -> stack.push(new PathHolder(it, new ArrayList<>(path.getPath()))));

                    root = childCategories.get(0);
                } else {
                    root = null;
                }
            }
            // reset root to get out of the loop if this is the last node
            root = null;
            result.add(path);
        }

        return result;
    }

    // Visible for testing
    List<CategoryNode> buildTrees(List<Category> categories) {
        List<Category> roots = categories.stream()
                .filter(c -> c.getSuperCategoryCode() == null)
                .collect(Collectors.toList());

        categories.removeAll(roots);

        List<CategoryNode> result = roots.stream()
                .map(c -> new CategoryNode(c.getCode(), c.getTitle()))
                .collect(Collectors.toList());

        List<Category> categoriesAdded = new ArrayList<>();
        List<CategoryNode> leafs = new ArrayList<>(result);
        for (int i = 0; i < categories.size(); i++) {
            categoriesAdded.clear();
            for (Category category : categories) {
                ListIterator<CategoryNode> iterator = leafs.listIterator();
                while (iterator.hasNext()) {
                    CategoryNode categoryNode = iterator.next();
                    if (categoryNode.getCategoryCode().equals(category.getSuperCategoryCode())) {
                        CategoryNode leaf = new CategoryNode(category.getCode(), category.getTitle());
                        categoryNode.addChildCategoryNode(leaf);
                        categoriesAdded.add(category);
                        iterator.add(leaf);
                    }
                }
            }
            categories.removeAll(categoriesAdded);
        }

        return result;
    }

    @Value
    private final class PathHolder {
        private final CategoryNode node;
        private final List<CategoryPathElement> pathPrefix;
    }

}
