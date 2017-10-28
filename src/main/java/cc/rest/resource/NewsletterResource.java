package cc.rest.resource;

import cc.common.model.Newsletter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Maxim Neverov
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewsletterResource {

    List<Newsletter> newsletters;

}
