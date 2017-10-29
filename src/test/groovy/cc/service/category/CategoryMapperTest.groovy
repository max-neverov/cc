package cc.service.category

import cc.common.model.Category
import cc.persistence.dto.CategoryDto
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author Maxim Neverov
 */
class CategoryMapperTest extends Specification {

    @Shared CategoryMapper mapper = new CategoryMapper()

    def "all fields from dto should be mapped to model"() {
        setup:
        def dto = new CategoryDto(code, title, superCode)

        when:
        def result = mapper.mapToCategory(dto)

        then:
        result.getCode() == code
        result.getTitle() == title
        result.getSuperCategoryCode() == superCode

        where:
        code      | title   | superCode
        "cat_code"| "title" | "super_cat_code"
    }


    def "all fields from model should be mapped to dto"() {
        setup:
        def model = new Category(code, title, superCode)

        when:
        def result = mapper.mapToDto(model)

        then:
        result.getCode() == code
        result.getTitle() == title
        result.getSuperCategoryCode() == superCode

        where:
        code      | title   | superCode
        "cat_code"| "title" | "super_cat_code"
    }
}
