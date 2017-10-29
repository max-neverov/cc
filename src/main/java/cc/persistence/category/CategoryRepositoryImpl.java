package cc.persistence.category;

import cc.persistence.dto.CategoryDto;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.sql.DataSource;

/**
 * @author Maxim Neverov
 */
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Inject
    public CategoryRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void create(CategoryDto category) {
        String SQL = "insert into category(code, title, super_category_code)" +
                    " values (:code, :title, :superCategoryCode)";
        jdbcTemplate.update( SQL, new BeanPropertySqlParameterSource(category));
    }

}
