package cc.common.model;

import lombok.Value;

import java.util.List;

/**
 * @author Maxim Neverov
 */
@Value
public class Notification {

    private String book;
    private List<CategoryPath> categoryPaths;

}
