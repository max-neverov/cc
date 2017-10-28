package cc.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Maxim Neverov
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {

    private String title;
    private List<String> categoryCodes;

}
