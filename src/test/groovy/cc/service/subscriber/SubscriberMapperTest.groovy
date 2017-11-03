package cc.service.subscriber

import cc.common.model.Subscriber
import cc.persistence.dto.SubscriberDto
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author Maxim Neverov
 */
class SubscriberMapperTest extends Specification {

    @Shared SubscriberMapper mapper = new SubscriberMapper()

    def "all fields from dto should be mapped to model"() {
        setup:
        def dtoSameEmail1 = new SubscriberDto(1, "same_email", "cat_code")
        def dtoSameEmail2 = new SubscriberDto(2, "same_email", "cat_code2")
        def dto3 = new SubscriberDto(3, "diff_email", "cat_code")

        def subscriber1 = new Subscriber("same_email", ["cat_code",
                                                        "cat_code2"])
        def subscriber2 = new Subscriber("diff_email", ["cat_code"])

        when:
        def result = mapper.mapToDomain([dtoSameEmail1,
                                         dtoSameEmail2,
                                         dto3])

        then:
        result.size() == 2
        result.contains(subscriber1)
        result.contains(subscriber2)
    }


    def "all fields from model should be mapped to dto"() {
        setup:
        def model = new Subscriber("email", ["cat_code", "cat_code2"])
        def dto1 = new SubscriberDto(null, "email", "cat_code")
        def dto2 = new SubscriberDto(null, "email", "cat_code2")

        when:
        def result = mapper.mapToDto(model)

        then:
        result.size() == 2
        result.contains(dto1)
        result.contains(dto2)
    }
}
