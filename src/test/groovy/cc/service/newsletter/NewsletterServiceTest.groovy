package cc.service.newsletter

import cc.common.model.Book
import cc.common.model.Category
import cc.common.model.CategoryNode
import cc.common.model.CategoryPath
import cc.persistence.book.BookRepository
import cc.persistence.subscriber.SubscriberRepository
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Spy
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

    def "buildTree should return valid tree"() {
        setup:
        def cat1 = new Category("cat1", "title1")
        def cat2 = new Category("cat2", "title2", "cat1")
        def cat3 = new Category("cat3", "title3", "cat2")
        def cat4 = new Category("cat4", "title4", "cat2")
        def cat5 = new Category("cat5", "title5", "cat2")
        def cat6 = new Category("cat6", "title6", "cat1")
        def cat7 = new Category("cat7", "title7")
        def list = [cat1, cat2, cat3, cat4, cat5, cat6, cat7]

        when:
        def result = service.buildTrees(list)

        then:
        result.size() == 2
        def path1 = service.getAllPathsStartedBy(result.get(0))
        path1.size() == 4
        path1.get(0).getPath() == ["cat1", "cat2", "cat3"]
        path1.get(1).getPath() == ["cat1", "cat2", "cat5"]
        path1.get(2).getPath() == ["cat1", "cat2", "cat4"]
        path1.get(3).getPath() == ["cat1", "cat6"]

        def path2 = service.getAllPathsStartedBy(result.get(1))
        path2.size() == 1
        path2.get(0).getPath() == ["cat7"]
    }

    def "getBookListMap should return books with their pahts"() {
        setup:
        def paths = getPaths()
        Map<String, List<Book>> bookMap = new HashMap<>()
        Book book3 = new Book("book3", ["cat11", "cat8"])
        Book book5 = new Book("book5", ["cat10", "cat11"])
        Book book6 = new Book("book6", ["cat11", "cat12"])
        Book book4 = new Book("book4", ["cat7"])
        bookMap.put("cat7", [book4])
        bookMap.put("cat8", [book3])
        bookMap.put("cat10", [book5])
        bookMap.put("cat11", [book3, book5, book6])
        bookMap.put("cat12", [book6])

        when:
        def resultMap = service.getBookListMap(paths, bookMap)

        then:
        resultMap.size() == 4
        resultMap.get(book3) == [new CategoryPath(["cat7", "cat8"]),
                                 new CategoryPath(["cat7", "cat9", "cat11"])]
        resultMap.get(book4) == [new CategoryPath(["cat7"])]
        resultMap.get(book5) == [new CategoryPath(["cat7", "cat8", "cat10"]),
                                 new CategoryPath(["cat7", "cat9", "cat11"])]
        resultMap.get(book6) == [new CategoryPath(["cat7", "cat9", "cat11"]),
                                 new CategoryPath(["cat7", "cat9", "cat12"])]
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

    private static List<CategoryPath> getPaths() {
        def path1 = new CategoryPath(["cat7", "cat8", "cat10"])
        def path2 = new CategoryPath(["cat7", "cat9", "cat11"])
        def path3 = new CategoryPath(["cat7", "cat9", "cat12"])
        def paths = [path1, path2, path3]
        paths
    }

}
