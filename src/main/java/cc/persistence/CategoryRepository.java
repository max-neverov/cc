package cc.persistence;

import cc.persistence.dto.CategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author Maxim Neverov
 */
public interface CategoryRepository extends CategoryBaseRepository<CategoryDto, String> {
}

@NoRepositoryBean
interface CategoryBaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    List<T> findAll();

    <S extends T> S save(S entity);

}
