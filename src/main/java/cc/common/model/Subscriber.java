package cc.common.model;

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
public class Subscriber {

    private String email;
    private List<String> categoryCodes;

}
