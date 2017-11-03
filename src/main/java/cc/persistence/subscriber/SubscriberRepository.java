package cc.persistence.subscriber;

import cc.persistence.dto.SubscriberDto;

import java.util.List;

/**
 * @author Maxim Neverov
 */
public interface SubscriberRepository {

    List<SubscriberDto> getSubscribers();
    void create(List<SubscriberDto> subscriber);

}
