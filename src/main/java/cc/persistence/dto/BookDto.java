package cc.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Maxim Neverov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private Integer id;
    private String title;
    private List<String> categoryCodes;

}
