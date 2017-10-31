package cc.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Maxim Neverov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriberDto {

    private Integer id;
    private String email;
    private String categoryCode;

}
