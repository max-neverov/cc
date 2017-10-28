package cc.common.model;

import lombok.Value;

import java.util.List;

/**
 * @author Maxim Neverov
 */
@Value
public class CategoryNode {

    private final String categoryCode;
    private final String categoryTitle;
    private final List<CategoryNode> childCategories;

}
