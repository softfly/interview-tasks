package yapily.marvel.rest.client.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class MarvelCharacter {

    /**
     * The unique ID of the character resource.
     */
    private Long id;

    /**
     * The name of the character.
     */
    private String name;

    /**
     * A short bio or description of the character.
     */
    private String description;

    /**
     * The representative image for this character.
     */
    private Image thumbnail;

    /**
     * A resource list of stories in which this character appears.
     */
    private StoryList stories;

    /**
     * A resource list of events in which this character appears.
     */
    private EventList events;

    /**
     * A resource list of series in which this character appears.
     */
    private SeriesList series;

    public MarvelCharacter() {
    }

}
