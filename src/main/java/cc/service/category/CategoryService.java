package cc.service.category;

import java.util.List;

import cc.common.model.Category;

/**
 * @author Maxim Neverov
 */
public interface CategoryService {

    Category create(Category category);
    List<Category> getAllCategories();

}
