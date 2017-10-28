package cc.service;

import cc.common.model.CategoryNode;
import cc.common.model.Newsletter;
import cc.common.model.Subscriber;
import cc.persistence.book.BookRepository;
import cc.persistence.subscriber.SubscriberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

/**
 * @author Maxim Neverov
 */
@Slf4j
@Service
public class NewsletterServiceImpl implements NewsletterService {

    private final List<CategoryNode> categories;
    private final SubscriberRepository subscriberRepository;
    private final BookRepository bookRepository;

    @Inject
    public NewsletterServiceImpl(List<CategoryNode> categories,
                                 SubscriberRepository subscriberRepository,
                                 BookRepository bookRepository) {
        this.categories = categories;
        this.subscriberRepository = subscriberRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Newsletter> getNewsletters() {
        List<Subscriber> subscribers = subscriberRepository.getSubscribers();

        return Collections.emptyList();
    }

}
