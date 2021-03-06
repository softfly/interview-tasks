package yapily.marvel.rest.client.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class EventSummary {

    /**
     * The name of the event
     */
    private String name;

    public EventSummary() {
    }

}
