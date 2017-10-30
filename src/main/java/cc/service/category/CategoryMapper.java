package cc.service.category;

import cc.common.model.Category;
import cc.persistence.dto.CategoryDto;
import org.springframework.stereotype.Component;

/**
 * @author Maxim Neverov
 */
@Component
public class CategoryMapper {

    public CategoryDto mapToDto(Category category) {
        CategoryDto result = new CategoryDto();
        result.setCode(category.getCode());
        result.setTitle(category.getTitle());
        result.setSuperCategoryCode(category.getSuperCategoryCode());

        return result;
    }

    public Category mapToDomain(CategoryDto categoryDto) {
        Category result = new Category();
        result.setCode(categoryDto.getCode());
        result.setTitle(categoryDto.getTitle());
        result.setSuperCategoryCode(categoryDto.getSuperCategoryCode());

        return result;
    }

}
