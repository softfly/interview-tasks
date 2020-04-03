package yapily.marvel.explorer.backend;

import java.util.List;
import java.util.Optional;

import yapily.marvel.explorer.model.MarvelCharacter;

public interface CharactersService {

    List<MarvelCharacter> getCharacters(CharactersSearchParams params) throws Exception;

    Optional<MarvelCharacter> getCharacter(Long id) throws Exception;

    Integer countCharacters(CharactersSearchParams params) throws Exception;

}
