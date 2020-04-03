package yapily.marvel.rest.client.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class SeriesList {

    /**
     * The list of returned series in this collection.
     */
    private List<SeriesSummary> items;

    public SeriesList() {

    }

}
