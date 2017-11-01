package cc.rest.validation;

import cc.common.model.Book;
import cc.rest.exception.NewsletterBadRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * @author Maxim Neverov
 */
@Component
public class BookValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        String title = book.getTitle();
        StringBuilder sb = new StringBuilder();
        boolean badRequest = false;

        if (title == null || title.isEmpty()) {
            sb.append("title,");
            badRequest = true;
        }
        List<String> codes = book.getCategoryCodes();
        if (codes == null || codes.isEmpty()) {
            sb.append("categoryCodes");
            badRequest = true;
        }
        if (badRequest) {
            throw new NewsletterBadRequest(sb.toString());
        }
    }

}
