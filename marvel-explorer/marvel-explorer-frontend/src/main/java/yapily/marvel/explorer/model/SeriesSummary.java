package yapily.marvel.explorer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class SeriesSummary {

    /**
     * The canonical name of the series.
     */
    private String name;

    public SeriesSummary() {

    }

}
