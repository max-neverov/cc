package cc.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Maxim Neverov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscriber")
public class SubscriberDto {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "email")
    private String email;
    @Column(name = "category_code")
    private String categoryCode;

}
