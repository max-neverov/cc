package cc.service.category;

import cc.common.model.Category;
import cc.persistence.category.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

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
    public Category create(Category category) {
        repository.save(mapper.mapToDto(category));
        return category;
    }

    @Override
    public List<Category> getCategories() {
        return repository.findAll().stream()
                .map(mapper::mapToDomain)
                .collect(Collectors.toList());
    }

}
