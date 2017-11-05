package cc.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Value;

/**
 * @author Maxim Neverov
 */
@Value
public class CategoryPathElement {

    @JsonIgnore
    private String code;
    private String title;

}
