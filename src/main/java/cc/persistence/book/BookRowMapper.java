package cc.persistence.book;

import cc.persistence.dto.BookDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Maxim Neverov
 */
@Component
public class BookRowMapper implements RowMapper<BookDto> {

    @Override
    public BookDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new BookDto(rs.getInt("id"),
                rs.getString("title"),
                rs.getString("category_code"));
    }

}
