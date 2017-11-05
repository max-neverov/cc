package cc.rest;

import cc.common.model.Category;
import cc.rest.validation.CategoryValidator;
import cc.service.category.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

/**
 * @author Maxim Neverov
 */
@Slf4j
@RestController
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
        service.create(category);
    }

    @GetMapping
    public List<Category> getCategories() {
        List<Category> result = service.getCategories();
        result.sort(Comparator.comparing(Category::getTitle));
        return result;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

}
