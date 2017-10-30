package cc.service.book

import cc.common.model.Book
import cc.persistence.dto.BookDto
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author Maxim Neverov
 */
class BookMapperTest extends Specification {

    @Shared BookMapper mapper = new BookMapper()

    def "all fields from dto should be mapped to model"() {
        setup:
        def dtoSameTitle1 = new BookDto(1, "same_title", "cat_code")
        def dtoSameTitle2 = new BookDto(2, "same_title", "cat_code2")
        def dto3 = new BookDto(3, "diff_title", "cat_code")

        def book1 = new Book("same_title", ["cat_code",
                                            "cat_code2"])
        def book2 = new Book("diff_title", ["cat_code"])

        when:
        def result = mapper.mapToDomain([dtoSameTitle1,
                                         dtoSameTitle2,
                                         dto3])

        then:
        result.size() == 2
        result.contains(book1)
        result.contains(book2)
    }


    def "all fields from model should be mapped to dto"() {
        setup:
        def model = new Book("title", ["cat_code", "cat_code2"])
        def dto1 = new BookDto(null, "title", "cat_code")
        def dto2 = new BookDto(null, "title", "cat_code2")

        when:
        def result = mapper.mapToDto(model)

        then:
        result.size() == 2
        result.contains(dto1)
        result.contains(dto2)
    }

}
