package cc.service

import cc.common.model.CategoryNode
import cc.common.model.CategoryPath
import cc.persistence.book.BookRepository
import cc.persistence.subscriber.SubscriberRepository
import org.mockito.*
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.eq

/**
 * @author Maxim Neverov
 */
class NewsletterServiceTest extends Specification {

    @Mock
    private List<CategoryNode> categoryTrees;
    @Mock
    private SubscriberRepository subscriberRepository;
    @Mock
    private BookRepository bookRepository;

    @Spy
    @InjectMocks
    private NewsletterServiceImpl service

    def setup() {
        MockitoAnnotations.initMocks(this);
    }

    def "check category paths fill correctly"() {
        setup:
        def root = getRootNode()

        when:
        Mockito.doCallRealMethod().when(service).getAllPathsStartedBy(eq(root))
        def paths = service.getAllPathsStartedBy(root)

        then:
        paths.size() == 6
        paths.contains(new CategoryPath(["1","2","4","7"]))
        paths.contains(new CategoryPath(["1","2","4","8"]))
        paths.contains(new CategoryPath(["1","2","5","9"]))
        paths.contains(new CategoryPath(["1","2","5","10"]))
        paths.contains(new CategoryPath(["1","2","5","11"]))
        paths.contains(new CategoryPath(["1","3","6"]))

    }

    /**
     * @return category tree as it depicted below
     *
     *                  1
     *               /     \
     *              2       3
     *           /     \     \
     *          4       5     6
     *        /  \    / |  \
     *       7    8  9  10  11
     *
     */
    private static CategoryNode getRootNode() {
        CategoryNode nine = new CategoryNode("9", "9")
        CategoryNode ten = new CategoryNode("10", "10")
        CategoryNode eleven = new CategoryNode("11", "11")

        CategoryNode five = new CategoryNode("5", "5")
        five.addChildCategoryNode(nine)
        five.addChildCategoryNode(ten)
        five.addChildCategoryNode(eleven)

        CategoryNode eight = new CategoryNode("8", "8")
        CategoryNode seven = new CategoryNode("7", "7")

        CategoryNode four = new CategoryNode("4", "4")
        four.addChildCategoryNode(seven)
        four.addChildCategoryNode(eight)

        CategoryNode six = new CategoryNode("6", "6")
        CategoryNode three = new CategoryNode("3", "3")
        three.addChildCategoryNode(six)

        CategoryNode two = new CategoryNode("2", "2")
        two.addChildCategoryNode(four)
        two.addChildCategoryNode(five)

        CategoryNode one = new CategoryNode("1", "1")
        one.addChildCategoryNode(two)
        one.addChildCategoryNode(three)

        one
    }

}
