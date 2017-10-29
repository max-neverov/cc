package cc.service.category;

import cc.common.model.Category;
import cc.persistence.category.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author Maxim Neverov
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Inject
    public CategoryServiceImpl(CategoryRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void create(Category category) {
        repository.create(mapper.mapToDto(category));
    }
    
}
