package cc.persistence.category;

import cc.persistence.dto.CategoryDto;
import cc.persistence.hystrix.HystrixDbCommand;
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

    public static final String CATEGORY_GROUP = "CategoryGroup";
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Inject
    public CategoryRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void create(CategoryDto category) {
        String sql = "insert into category(code, title, super_category_code)" +
                    " values (:code, :title, :superCategoryCode)";
        HystrixDbCommand<Integer> createCmd = new HystrixDbCommand<>("CreateCategory", CATEGORY_GROUP);

        createCmd.execute(cmd -> jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(category)));
    }

    /**
     * An example how to use lambda as result set mapper.
     * While looking pretty and concise such mappers are hard to test.
     */
    @Override
    public List<CategoryDto> getAllCategories() {
        String sql = "select * from category";
        HystrixDbCommand<List<CategoryDto>> getCmd = new HystrixDbCommand<>("GetCategories", CATEGORY_GROUP);

        return getCmd.execute(cmd -> jdbcTemplate.query(sql, (rs, rowNum) ->
                                                        new CategoryDto(rs.getString("code"),
                                                                        rs.getString("title"),
                                                                        rs.getString("super_category_code"))));
    }

}
