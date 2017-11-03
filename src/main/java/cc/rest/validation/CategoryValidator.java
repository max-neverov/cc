package cc.rest.validation;

import cc.common.model.Category;
import cc.rest.exception.NewsletterBadRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Maxim Neverov
 */
@Component
public class CategoryValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Category.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Category category = (Category) target;
        StringBuilder sb = new StringBuilder();

        boolean badRequest = false;
        if (isFieldAbsent(category.getCode())) {
            sb.append("code,");
            badRequest = true;
        }
        if (isFieldAbsent(category.getTitle())) {
            sb.append("title");
            badRequest = true;
        }

        if (badRequest) {
            throw new NewsletterBadRequest(sb.toString());
        }

    }

    private boolean isFieldAbsent(String fieldValue) {
        if (fieldValue == null || fieldValue.isEmpty()) {
            return true;
        }
        return false;
    }

}
