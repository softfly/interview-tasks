package yapily.marvel.rest.client.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class StoryList {

    /**
     * The list of returned stories in this collection.
     */
    private List<StorySummary> items;

    public StoryList() {

    }

}
