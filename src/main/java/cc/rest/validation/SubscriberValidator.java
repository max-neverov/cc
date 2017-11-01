package cc.rest.validation;

import cc.common.model.Subscriber;
import cc.rest.exception.NewsletterBadRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * @author Maxim Neverov
 */
@Component
public class SubscriberValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Subscriber.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Subscriber subscriber = (Subscriber) target;
        String email = subscriber.getEmail();
        StringBuilder sb = new StringBuilder();
        boolean badRequest = false;

        if (email == null || email.isEmpty()) {
            sb.append("email,");
            badRequest = true;
        }
        List<String> codes = subscriber.getCategoryCodes();
        if (codes == null || codes.isEmpty()) {
            sb.append("categoryCodes");
            badRequest = true;
        }
        if (badRequest) {
            throw new NewsletterBadRequest(sb.toString());
        }
    }

}
