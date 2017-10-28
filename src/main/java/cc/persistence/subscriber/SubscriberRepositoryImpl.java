package cc.persistence.subscriber;

import cc.common.model.Subscriber;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * @author Maxim Neverov
 */
@Repository
public class SubscriberRepositoryImpl implements SubscriberRepository {

    @Override
    public List<Subscriber> getSubscribers() {
        return Collections.emptyList();
    }

    @Override
    public void saveSubscriber(Subscriber subscriber) {
        // todo Neverov: implement
    }


}
