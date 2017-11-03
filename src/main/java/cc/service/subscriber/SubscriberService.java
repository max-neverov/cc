package cc.service.subscriber;

import cc.common.model.Subscriber;

import java.util.List;

/**
 * @author Maxim Neverov
 */
public interface SubscriberService {

    void create(Subscriber subscriber);
    List<Subscriber> getSubscribers();

}
