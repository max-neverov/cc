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
public class BookDto {

    private Integer id;
    private String title;
    private String categoryCode;

}
