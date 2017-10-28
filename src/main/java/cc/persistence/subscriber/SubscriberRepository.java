package cc.persistence.subscriber;

import cc.common.model.Subscriber;

import java.util.List;

/**
 * @author Maxim Neverov
 */
public interface SubscriberRepository {

    List<Subscriber> getSubscribers();
    void saveSubscriber(Subscriber subscriber);

}
