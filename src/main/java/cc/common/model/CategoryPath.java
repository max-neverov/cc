package cc.common.model;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Neverov
 */
@Value
public class CategoryPath {

    private final List<CategoryPathElement> path = new ArrayList<>();

    public CategoryPath(List<CategoryPathElement> path) {
        if (path != null) {
            this.path.addAll(path);
        }
    }

    public void addCategory(CategoryPathElement category) {
        path.add(category);
    }

}
