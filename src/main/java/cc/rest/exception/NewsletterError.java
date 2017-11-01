package cc.rest.exception;

import lombok.Value;

/**
 * @author Maxim Neverov
 */
@Value
public class NewsletterError {

    private final String errorCode;
    private final String message;

}
