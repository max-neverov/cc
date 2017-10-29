package cc.common.model;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Neverov
 */
@Value
public class CategoryPath {

    private final List<String> path = new ArrayList<>();

    public CategoryPath(List<String> path) {
        if (path != null) {
            this.path.addAll(path);
        }
    }

    public void addCategory(String category) {
        path.add(category);
    }

}
