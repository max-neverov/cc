package cc.persistence.subscriber;

import cc.persistence.dto.SubscriberDto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Maxim Neverov
 */
public interface SubscriberRepository extends JpaRepository<SubscriberDto, Integer> {
}
