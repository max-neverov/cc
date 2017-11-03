package cc.persistence.subscriber;

import cc.persistence.dto.SubscriberDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Maxim Neverov
 */
@Component
public class SubscriberRowMapper implements RowMapper<SubscriberDto> {


    @Override
    public SubscriberDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SubscriberDto(rs.getInt("id"),
                                 rs.getString("email"),
                                 rs.getString("category_code"));
    }

}
