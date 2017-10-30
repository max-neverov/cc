package cc.service.newsletter;

import cc.common.model.Newsletter;

import java.util.List;

/**
 * @author Maxim Neverov
 */
public interface NewsletterService {

    List<Newsletter> getNewsletters();

}
