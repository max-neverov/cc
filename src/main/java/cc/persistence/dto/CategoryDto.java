package cc.persistence.dto;

import lombok.Data;

/**
 * @author Maxim Neverov
 */
@Data
public class CategoryDto {

    private String code;
    private String title;
    private String superCategoryCode;

}
