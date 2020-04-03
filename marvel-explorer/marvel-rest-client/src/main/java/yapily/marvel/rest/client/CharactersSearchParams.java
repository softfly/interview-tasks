package yapily.marvel.rest.client;

import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class CharactersSearchParams implements Cloneable {

    public final static String PARAM_NAME_STARTS_WITH = "nameStartsWith";

    public final static String PARAM_OFFSET = "offset";

    public final static String PARAM_LIMIT = "limit";

    /**
     * Return characters with names that begin with the specified string (e.g. Sp).
     */
    private String nameStartsWith;

    /**
     * Skip the specified number of resources in the result set.
     */
    @Min(0)
    private Integer offset;

    /**
     * Limit the result set to the specified number of resources.
     */
    @Min(0)
    private Integer limit;

    public CharactersSearchParams() {
    }

    @Override
    public CharactersSearchParams clone() throws CloneNotSupportedException {
        return (CharactersSearchParams) super.clone();
    }

}
