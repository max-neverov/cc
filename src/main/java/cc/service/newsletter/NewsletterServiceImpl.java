package cc.service.newsletter;

import cc.common.model.Category;
import cc.common.model.CategoryNode;
import cc.common.model.CategoryPath;
import cc.common.model.Newsletter;
import cc.persistence.book.BookRepository;
import cc.persistence.subscriber.SubscriberRepository;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * @author Maxim Neverov
 */
@Slf4j
@Service
public class NewsletterServiceImpl implements NewsletterService {

    private final SubscriberRepository subscriberRepository;
    private final BookRepository bookRepository;

    @Inject
    public NewsletterServiceImpl(SubscriberRepository subscriberRepository,
                                 BookRepository bookRepository) {
        this.subscriberRepository = subscriberRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Newsletter> getNewsletters() {
        return Collections.emptyList();
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
        List<String> prefix = null;
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
                String categoryCode = root.getCategoryCode();
                path.addCategory(categoryCode);

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
        private final List<String> pathPrefix;
    }

}
