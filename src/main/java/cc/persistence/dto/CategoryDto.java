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
public class CategoryDto {

    private String code;
    private String title;
    private String superCategoryCode;

}
