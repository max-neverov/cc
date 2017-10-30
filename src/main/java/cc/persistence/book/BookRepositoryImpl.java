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

    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final BookRowMapper mapper;

    @Inject
    public BookRepositoryImpl(DataSource dataSource, BookRowMapper mapper) {
        this.dataSource = dataSource;
        jdbcTemplate = new NamedParameterJdbcTemplate(this.dataSource);
        this.mapper = mapper;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void create(List<BookDto> books) {
        String sql = "insert into book (title, category_code) values (:title, :categoryCode)";
        List<Map<String, Object>> batchValues = new ArrayList<>(books.size());

        for (BookDto book : books) {
            batchValues.add(new MapSqlParameterSource("title", book.getTitle())
                                .addValue("categoryCode", book.getCategoryCode())
                                .getValues());
        }

        jdbcTemplate.batchUpdate(sql, batchValues.toArray(new Map[books.size()]));
    }

    @Override
    public List<BookDto> getAllBooksBelongToCategory(String category) {
        String sql = "select * from book" +
                    " where category_code = :categoryCode";
        
        return jdbcTemplate.query(sql,
                new MapSqlParameterSource("categoryCode", category), mapper);
    }

}
