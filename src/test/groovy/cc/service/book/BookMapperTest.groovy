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
        def dto = new BookDto(1, title, categories)

        when:
        def result = mapper.mapToDomain(dto)

        then:
        result.getTitle() == title
        result.getCategoryCodes() == categories

        where:
        title   | categories
        "title" | ["cat_code"]
    }


    def "all fields from model should be mapped to dto"() {
        setup:
        def model = new Book(title, categories)

        when:
        def result = mapper.mapToDto(model)

        then:
        result.getTitle() == title
        result.getCategoryCodes() == categories

        where:
        title   | categories
        "title" | ["cat_code"]
    }
}
