package cc.persistence.book;

import cc.persistence.dto.BookDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Maxim Neverov
 */
@Slf4j
@Repository
public class BookRepositoryImpl implements BookRepository {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Inject
    public BookRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new NamedParameterJdbcTemplate(this.dataSource);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void create(List<BookDto> books) {
        String sql = "insert into BOOK (title, category_code) values (:title, :categoryCode)";
        List<Map<String, Object>> batchValues = new ArrayList<>(books.size());

        for (BookDto book : books) {
            batchValues.add(new MapSqlParameterSource("title", book.getTitle())
                                .addValue("categoryCode", book.getCategoryCode())
                                .getValues());
        }

        jdbcTemplate.batchUpdate(sql, batchValues.toArray(new Map[books.size()]));
    }

}
