package cc.persistence.category;

import cc.persistence.dto.CategoryDto;

/**
 * @author Maxim Neverov
 */
public interface CategoryRepository {

    void create(CategoryDto category);

}
