package cc.rest.resource;

import cc.common.model.Newsletter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Neverov
 */
@NoArgsConstructor
@Data
public class NewsletterResource {

    List<Newsletter> newsletters = new ArrayList<>();

    public NewsletterResource(List<Newsletter> newsletters) {
        if (newsletters != null) {
            this.newsletters.addAll(newsletters);
        }
    }

}
