package cc.rest;

import cc.common.model.Subscriber;
import cc.rest.validation.SubscriberValidator;
import cc.service.subscriber.SubscriberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * @author Maxim Neverov
 */
@Slf4j
@Controller
@RequestMapping("/subscribers")
public class SubscriberController {

    private final SubscriberService service;
    private final SubscriberValidator validator;

    @Inject
    public SubscriberController(SubscriberService service, SubscriberValidator validator) {
        this.service = service;
        this.validator = validator;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void submitSubscriber(@Valid @RequestBody Subscriber subscriber) {
        log.info("Got the subscriber {}", subscriber);
        service.create(subscriber);
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

}
