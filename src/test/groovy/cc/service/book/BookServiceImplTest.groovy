package cc.service.book

import cc.common.model.Book
import cc.persistence.book.BookRepository
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import spock.lang.Specification

/**
 * @author Maxim Neverov
 */
class BookServiceImplTest extends Specification {

    @Mock
    private BookRepository repository

    private BookServiceImpl service

    def setup() {
        MockitoAnnotations.initMocks(this);
        service = new BookServiceImpl(repository, new BookMapper())
    }

    def "test books mapping"() {
        setup:
        def book1 = new Book("book1", ["cat1", "cat2"])
        def book2 = new Book("book2", ["cat1", "cat3"])
        def list = [book1, book2]

        when:
        def resultMap = service.getBooksByCategoryMap(list)

        then:
        resultMap.get("cat1").size() == 2
        resultMap.get("cat1").contains(book1)
        resultMap.get("cat1").contains(book2)

        resultMap.get("cat2").size() == 1
        resultMap.get("cat2").contains(book1)

        resultMap.get("cat3").size() == 1
        resultMap.get("cat3").contains(book2)
    }
    
}
