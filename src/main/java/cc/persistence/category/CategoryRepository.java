package cc.persistence.category;

import cc.persistence.dto.CategoryDto;

import java.util.List;

/**
 * @author Maxim Neverov
 */
public interface CategoryRepository {

    void create(CategoryDto category);
    List<CategoryDto> getAllCategories();

}
