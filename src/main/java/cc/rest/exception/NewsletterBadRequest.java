package cc.rest.exception;

/**
 * @author Maxim Neverov
 */
public class NewsletterBadRequest extends RuntimeException {

    public NewsletterBadRequest(String message) {
        super(message);
    }

}
