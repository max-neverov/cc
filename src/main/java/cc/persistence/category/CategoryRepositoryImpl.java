package cc.persistence.category;

import cc.persistence.dto.CategoryDto;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.List;

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
        String sql = "insert into category(code, title, super_category_code)" +
                    " values (:code, :title, :superCategoryCode)";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(category));
    }

    /**
     * An example how to use lambda as result set mapper.
     * While looking pretty and concise such mappers are hard to test.
     */
    @Override
    public List<CategoryDto> getAllCategories() {
        String sql = "select * from category";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                                       new CategoryDto(rs.getString("code"),
                                                       rs.getString("title"),
                                                       rs.getString("super_category_code")));
    }

}
