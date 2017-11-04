package cc.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Maxim Neverov
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {

    private String code;
    private String title;
    private String superCategoryCode;

    public Category(String code, String title) {
        this.code = code;
        this.title = title;
    }

}
