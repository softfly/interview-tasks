package yapily.marvel.explorer.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class EventList {

    /**
     * The list of returned events in this collection
     */
    private List<EventSummary> items;

    public EventList() {
    }

}
