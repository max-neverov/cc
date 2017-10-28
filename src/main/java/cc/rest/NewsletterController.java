package cc.rest;

import cc.rest.resource.NewsletterResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Maxim Neverov
 */
@Slf4j
@RestController
@RequestMapping("/newsletters")
public class NewsletterController {

    @GetMapping
    public NewsletterResource getNewsletters() {
        return new NewsletterResource();
    }
    
}
