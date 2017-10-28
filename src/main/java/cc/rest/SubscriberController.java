package cc.rest;

import cc.common.model.Subscriber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Maxim Neverov
 */
@Slf4j
@Controller
@RequestMapping("/subscribers")
public class SubscriberController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void submitSubscriber(@RequestBody Subscriber subscriber) {
        log.info("Got the subscriber {}", subscriber);
    }

}
