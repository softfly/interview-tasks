package yapily.marvel.explorer.backend;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@Component
@Scope("prototype")
public class CharactersSearchParams {

    /**
     * Return characters with names that begin with the specified string (e.g. Sp).
     */
    private String nameStartsWith;

    /**
     * Skip the specified number of resources in the result set.
     */
    private Integer offset;

    /**
     * Limit the result set to the specified number of resources.
     */
    private Integer limit;

    public CharactersSearchParams() {
    }

}
