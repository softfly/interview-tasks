package yapily.marvel.rest.client.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class CharacterDataWrapper {

    /**
     * The HTTP status code of the returned result.
     */
    private String code;

    /**
     * The results returned by the call.
     */
    private CharacterDataContainer data;

    public CharacterDataWrapper() {
    }

}
