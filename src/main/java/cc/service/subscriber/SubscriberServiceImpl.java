package cc.service.subscriber;

import cc.common.model.Subscriber;
import cc.persistence.subscriber.SubscriberRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Maxim Neverov
 */
@Service
public class SubscriberServiceImpl implements SubscriberService {

    private final SubscriberRepository repository;
    private final SubscriberMapper mapper;

    @Inject
    public SubscriberServiceImpl(SubscriberRepository repository, SubscriberMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void create(Subscriber subscriber) {
        repository.create(mapper.mapToDto(subscriber));
    }

    @Override
    public List<Subscriber> getSubscribers() {
        return mapper.mapToDomain(repository.getSubscribers());
    }

}
