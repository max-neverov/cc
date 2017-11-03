package cc.persistence.subscriber;

import cc.persistence.dto.SubscriberDto;
import cc.persistence.hystrix.HystrixDbCommand;
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

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SubscriberRowMapper mapper;
    public static final String SUBSCRIBER_GROUP = "SubscriberGroup";

    @Inject
    public SubscriberRepositoryImpl(DataSource dataSource,
                                    NamedParameterJdbcTemplate jdbcTemplate,
                                    SubscriberRowMapper mapper) {
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

        HystrixDbCommand<int[]> createCmd = new HystrixDbCommand<>("CreateSubscriber", SUBSCRIBER_GROUP);
        createCmd.execute(cmd -> jdbcTemplate.batchUpdate(sql, batchValues.toArray(new Map[subscribers.size()])));
    }

    @Override
    public List<SubscriberDto> getSubscribers() {
        String sql = "select * from subscriber";
        HystrixDbCommand<List<SubscriberDto>> getCmd = new HystrixDbCommand<>("GetSubscribers", SUBSCRIBER_GROUP);
        return getCmd.execute(cmd -> jdbcTemplate.query(sql, mapper));
    }

}
