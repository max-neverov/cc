package cc.rest;

import cc.common.model.Newsletter;
import cc.rest.resource.NewsletterResource;
import cc.service.NewsletterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Maxim Neverov
 */
@Slf4j
@RestController
@RequestMapping("/newsletters")
public class NewsletterController {

    private final NewsletterService service;

    @Inject
    public NewsletterController(NewsletterService service) {
        this.service = service;
    }

    @GetMapping
    public NewsletterResource getNewsletters() {
        List<Newsletter> newsletters = service.getNewsletters();
        return new NewsletterResource(newsletters);
    }
    
}
