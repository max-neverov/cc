package cc.persistence.subscriber;

import cc.persistence.dto.SubscriberDto;
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
@Repository
public class SubscriberRepositoryImpl implements SubscriberRepository {

    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SubscriberRowMapper mapper;

    @Inject
    public SubscriberRepositoryImpl(DataSource dataSource,
                                    NamedParameterJdbcTemplate jdbcTemplate,
                                    SubscriberRowMapper mapper) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void create(List<SubscriberDto> subscribers) {
        String sql = "insert into subscriber (email, category_code) values (:email, :categoryCode)";
        List<Map<String, Object>> batchValues = new ArrayList<>(subscribers.size());

        for (SubscriberDto subscriber : subscribers) {
            batchValues.add(new MapSqlParameterSource("email", subscriber.getEmail())
                    .addValue("categoryCode", subscriber.getCategoryCode())
                    .getValues());
        }

        jdbcTemplate.batchUpdate(sql, batchValues.toArray(new Map[subscribers.size()]));
    }

    @Override
    public List<SubscriberDto> getSubscribers() {
        String sql = "select * from subscriber";
        return jdbcTemplate.query(sql, mapper);
    }

}
