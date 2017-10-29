package cc.service

import cc.common.model.CategoryNode
import cc.common.model.CategoryPath
import cc.persistence.book.BookRepository
import cc.persistence.subscriber.SubscriberRepository
import org.mockito.*
import spock.lang.Specification
import spock.lang.Unroll

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
        def root = getCategoryTreeFrom1To11()

        when:
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

    def "empty node category's path should return empty list"() {
        setup:
        CategoryNode root = null

        when:
        def paths = service.getAllPathsStartedBy(root)

        then:
        paths.isEmpty()
    }

    def "single node category's path should contain one element"() {
        setup:
        def root = new CategoryNode("1", "1")

        when:
        def paths = service.getAllPathsStartedBy(root)

        then:
        paths.size() == 1
        paths.contains(new CategoryPath(["1"]))
    }

    def "desired categories should be found in existing categories"() {
        setup:
        def root = getCategoryTreeFrom1To11()
        def desiredCats = ["5", "6", "not existing category"]

        when:
        def paths = service.getPathsForCategories(desiredCats, root)

        then:
        paths.size() == 4
        paths.contains(new CategoryPath(["5","9"]))
        paths.contains(new CategoryPath(["5","10"]))
        paths.contains(new CategoryPath(["5","11"]))
        paths.contains(new CategoryPath(["6"]))
    }

    def "null root node should return empty list"() {
        setup:
        CategoryNode root = null
        def desiredCats = ["5", "6", "not existing category"]

        when:
        def paths = service.getPathsForCategories(desiredCats, root)

        then:
        paths.isEmpty()
    }

    def "single node categories path should contain one element"() {
        setup:
        def root = new CategoryNode("1", "1")
        def desiredCats = ["1", "6", "not existing category"]

        when:
        def paths = service.getPathsForCategories(desiredCats, root)

        then:
        paths.size() == 1
        paths.contains(new CategoryPath(["1"]))
    }

    @Unroll
    def "empty desired categories should return empty list"() {
        setup:
        def root = getCategoryTreeFrom1To11()

        when:
        def paths = service.getPathsForCategories(desiredCats, root)

        then:
        paths.size() == resultSize

        where:
        desiredCats | resultSize
        []          |  0
        null        |  0
    }

    private static List<CategoryNode> getListOfCategoryTrees() {
        [getCategoryTreeFrom1To11(), getCategoryTreeFrom20To25()]
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
    private static CategoryNode getCategoryTreeFrom1To11() {
        CategoryNode _9 = new CategoryNode("9", "9")
        CategoryNode _10 = new CategoryNode("10", "10")
        CategoryNode _11 = new CategoryNode("11", "11")

        CategoryNode five = new CategoryNode("5", "5")
        five.addChildCategoryNode(_9)
        five.addChildCategoryNode(_10)
        five.addChildCategoryNode(_11)

        CategoryNode _8 = new CategoryNode("8", "8")
        CategoryNode _7 = new CategoryNode("7", "7")

        CategoryNode _4 = new CategoryNode("4", "4")
        _4.addChildCategoryNode(_7)
        _4.addChildCategoryNode(_8)

        CategoryNode _6 = new CategoryNode("6", "6")
        CategoryNode _3 = new CategoryNode("3", "3")
        _3.addChildCategoryNode(_6)

        CategoryNode _2 = new CategoryNode("2", "2")
        _2.addChildCategoryNode(_4)
        _2.addChildCategoryNode(five)

        CategoryNode root = new CategoryNode("1", "1")
        root.addChildCategoryNode(_2)
        root.addChildCategoryNode(_3)

        root
    }

    /**
     * @return category tree as it depicted below
     *
     *                20
     *               /   \
     *             21    22
     *           /   \    \
     *         23    24   25
     *
     */
    private static CategoryNode getCategoryTreeFrom20To25() {
        CategoryNode _23 = new CategoryNode("23", "23")
        CategoryNode _24 = new CategoryNode("24", "24")
        CategoryNode _21 = new CategoryNode("21", "21")
        _21.addChildCategoryNode(_23)
        _21.addChildCategoryNode(_24)

        CategoryNode _22 = new CategoryNode("22", "22")
        CategoryNode _25 = new CategoryNode("25", "25")
        _22.addChildCategoryNode(_25)

        CategoryNode root = new CategoryNode("20", "20")
        root.addChildCategoryNode(_21)
        root.addChildCategoryNode(_22)

        root
    }

}
