package cc.service.category;

import cc.common.model.Category;

import java.util.List;

/**
 * @author Maxim Neverov
 */
public interface CategoryService {

    void create(Category category);
    List<Category> getAllCategories();

}
