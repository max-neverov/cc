package cc.rest;

import cc.common.model.Category;
import cc.rest.validation.CategoryValidator;
import cc.service.category.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * @author Maxim Neverov
 */
@Slf4j
@Controller
@RequestMapping("/categories")
public class CategoryController {
    
    private final CategoryService service;
    private final CategoryValidator validator;

    @Inject
    public CategoryController(CategoryService service, CategoryValidator validator) {
        this.service = service;
        this.validator = validator;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void submitCategory(@Valid @RequestBody Category category) {
        log.info("Got the category {}", category);
        service.create(category);
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

}
