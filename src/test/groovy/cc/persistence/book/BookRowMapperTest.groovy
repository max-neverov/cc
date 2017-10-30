package cc.persistence.book

import cc.persistence.book.BookRowMapper
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import spock.lang.Specification

import java.sql.ResultSet

/**
 * @author Maxim Neverov
 */
class BookRowMapperTest extends Specification {

    @Mock ResultSet rs
    BookRowMapper mapper = new BookRowMapper()

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "book dto should be mapped"() {
        setup:
        Mockito.when(rs.getInt("id")).thenReturn(1)
        Mockito.when(rs.getString(title)).thenReturn(title)
        Mockito.when(rs.getString(categoryCode)).thenReturn(categoryCode)

        when:
        def result = mapper.mapRow(rs, 1)

        then:
        result.title == title
        result.categoryCode == categoryCode

        where:
        title   | categoryCode
        "title" | "category_code"
    }
    
}
