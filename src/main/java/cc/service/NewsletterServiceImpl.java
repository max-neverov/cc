package cc.service;

import cc.common.model.Newsletter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Maxim Neverov
 */
@Slf4j
@Service
public class NewsletterServiceImpl implements NewsletterService {

    @Override
    public List<Newsletter> getNewsletters() {
        return Collections.emptyList();
    }

}
