package yapily.marvel.explorer.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@XmlRootElement
public class CharacterDataContainer {

    /**
     * The requested offset (number of skipped results) of the call.
     */
    private Integer offset;

    /**
     * The requested result limit.
     */
    private Integer limit;

    /**
     * The total number of resources available given the current filter set.
     */
    private Integer total;

    /**
     * The list of characters returned by the call.
     */
    private List<MarvelCharacter> results;

    public CharacterDataContainer() {
    }

}
