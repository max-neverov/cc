package cc.service;

import cc.common.model.CategoryNode;
import cc.common.model.CategoryPath;
import cc.common.model.Newsletter;
import cc.common.model.Subscriber;
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
import java.util.List;

/**
 * @author Maxim Neverov
 */
@Slf4j
@Service
public class NewsletterServiceImpl implements NewsletterService {

    private final List<CategoryNode> categoryTrees;
    private final SubscriberRepository subscriberRepository;
    private final BookRepository bookRepository;

    @Inject
    public NewsletterServiceImpl(List<CategoryNode> categoryTrees,
                                 SubscriberRepository subscriberRepository,
                                 BookRepository bookRepository) {
        this.categoryTrees = categoryTrees;
        this.subscriberRepository = subscriberRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Newsletter> getNewsletters() {
        List<Subscriber> subscribers = subscriberRepository.getSubscribers();

        return Collections.emptyList();
    }


    /**
     * Visible for testing.
     * Simple DFS algorithm with tracking already processed nodes.
     *
     * @param root Tree root
     * @return List of categories paths for given tree
     */
    List<CategoryPath> getAllPathsStartedBy(CategoryNode root) {
        List<CategoryPath> result = new ArrayList<>();
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

    @Value
    private final class PathHolder {
        private final CategoryNode node;
        private final List<String> pathPrefix;
    }

}
