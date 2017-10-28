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
public class Newsletter {

    private String recipient;
    private List<Notification> notifications;
    
}
