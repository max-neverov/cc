package cc.rest;

import cc.common.model.Subscriber;
import cc.service.subscriber.SubscriberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.inject.Inject;

/**
 * @author Maxim Neverov
 */
@Slf4j
@Controller
@RequestMapping("/subscribers")
public class SubscriberController {

    private final SubscriberService service;

    @Inject
    public SubscriberController(SubscriberService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void submitSubscriber(@RequestBody Subscriber subscriber) {
        log.info("Got the subscriber {}", subscriber);
        service.create(subscriber);
    }

}
