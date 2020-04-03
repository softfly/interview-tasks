package yapily.marvel.explorer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class StorySummary {

    /**
     * The canonical name of the story.
     */
    private String name;

    public StorySummary() {

    }

}
