package cc.common.model;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Neverov
 */
@Value
public class CategoryNode {

    private final String categoryCode;
    private final String categoryTitle;
    private final List<CategoryNode> childCategories = new ArrayList<>();

    public CategoryNode(String categoryCode, String categoryTitle) {
        this.categoryCode = categoryCode;
        this.categoryTitle = categoryTitle;
    }

    public void addChildCategoryNode(CategoryNode categoryNode) {
        childCategories.add(categoryNode);
    }

    public boolean hasChildren() {
        return !childCategories.isEmpty();
    }

}
