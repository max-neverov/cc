package cc.persistence.subscriber

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import spock.lang.Specification

import java.sql.ResultSet

/**
 * @author Maxim Neverov
 */
class SubscriberRowMapperTest extends Specification {

    @Mock ResultSet rs
    SubscriberRowMapper mapper = new SubscriberRowMapper()

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "subscriber dto should be mapped"() {
        setup:
        Mockito.when(rs.getInt("id")).thenReturn(1)
        Mockito.when(rs.getString(email)).thenReturn(email)
        Mockito.when(rs.getString(categoryCode)).thenReturn(categoryCode)

        when:
        def result = mapper.mapRow(rs, 1)

        then:
        result.email == email
        result.categoryCode == categoryCode

        where:
        email   | categoryCode
        "email" | "category_code"
    }

}
