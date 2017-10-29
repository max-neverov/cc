package cc.rest;

import cc.common.model.Category;
import cc.service.category.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.inject.Inject;

/**
 * @author Maxim Neverov
 */
@Slf4j
@Controller
@RequestMapping("/categories")
public class CategoryController {
    
    private final CategoryService service;

    @Inject
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void submitCategory(@RequestBody Category category) {
        log.info("Got the category {}", category);
        service.create(category);
    }
    
}
