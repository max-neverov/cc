package cc.rest;

import cc.common.model.Subscriber;
import cc.rest.validation.SubscriberValidator;
import cc.service.subscriber.SubscriberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

/**
 * @author Maxim Neverov
 */
@Slf4j
@RestController
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
        service.create(subscriber);
    }

    @GetMapping
    public List<Subscriber> getSubscribers() {
        List<Subscriber> result = service.getSubscribers();
        result.sort(Comparator.comparing(Subscriber::getEmail));
        return result;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

}
