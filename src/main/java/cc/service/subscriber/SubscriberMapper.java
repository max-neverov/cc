package cc.service.subscriber;

import cc.common.model.Subscriber;
import cc.persistence.dto.SubscriberDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Maxim Neverov
 */
@Component
public class SubscriberMapper {

    public List<SubscriberDto> mapToDto(Subscriber subscriber) {
        return subscriber.getCategoryCodes().stream()
                .map(it -> new SubscriberDto(null, subscriber.getEmail(), it))
                .collect(Collectors.toList());
    }

    public List<Subscriber> mapToDomain(List<SubscriberDto> subscribers) {
        List<Subscriber> result = new ArrayList<>();
        Map<String, List<SubscriberDto>> subscriberMap = subscribers.stream()
                                                         .collect(Collectors
                                                         .groupingBy(SubscriberDto::getEmail));

        for (Map.Entry<String, List<SubscriberDto>> entry : subscriberMap.entrySet()) {
            List<String> codes = entry.getValue().stream()
                    .map(SubscriberDto::getCategoryCode)
                    .collect(Collectors.toList());
            result.add(new Subscriber(entry.getKey(), codes));
        }

        return result;
    }

}
