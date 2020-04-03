package yapily.marvel.explorer.backend.impl;

import org.springframework.stereotype.Component;

@Component
public class CharactersSearchParamsMapper {

    public yapily.marvel.rest.client.CharactersSearchParams map(yapily.marvel.explorer.backend.CharactersSearchParams source) {
        if (source != null) {
            yapily.marvel.rest.client.CharactersSearchParams target = new yapily.marvel.rest.client.CharactersSearchParams();
            target.setNameStartsWith(source.getNameStartsWith());
            target.setOffset(source.getOffset());
            target.setLimit(source.getLimit());
            return target;
        } else {
            return null;
        }
    }

}
