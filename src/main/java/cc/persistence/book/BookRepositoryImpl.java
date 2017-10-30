package cc.persistence.book;

import cc.persistence.dto.BookDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.sql.DataSource;

/**
 * @author Maxim Neverov
 */
@Slf4j
@Repository
public class BookRepositoryImpl implements BookRepository {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Inject
    public BookRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(BookDto book) {
        String SQL = "insert into BOOK (title, ) values (?, ?)";
        jdbcTemplate.update( SQL, book.getTitle(), book.getCategoryCodes());
        log.info("Book {} created", book);
    }

}
