package yapily.marvel.explorer.backend.impl;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import yapily.marvel.explorer.backend.CharactersSearchParams;
import yapily.marvel.explorer.backend.CharactersService;
import yapily.marvel.explorer.model.MarvelCharacter;
import yapily.marvel.rest.client.MarvelCharactersClientImpl;

@Service
public class CharactersServiceMarvelRestClientImpl implements CharactersService {

    @Autowired
    private MarvelCharactersClientImpl marvelCharactersRestClient;

    @Autowired
    private CharacterMapper characterMapper;

    @Autowired
    private CharactersSearchParamsMapper charactersSearchParamsMapper;

    @Override
    public List<MarvelCharacter> getCharacters(CharactersSearchParams params) throws Exception {
        yapily.marvel.rest.client.model.response.CharacterDataContainer source = getMarvelCharactersRestClient()
                .getCharacters(getCharactersSearchParamsMapper().map(params)).getData();

        List<MarvelCharacter> target = getCharacterMapper().map(source).getResults();

        for (MarvelCharacter character: target) {

        	// Unnecessary data for listing
            // Use getCharacter to get detailed information
        	character.setDescription(null);
        	character.setEvents(null);
        	character.setSeries(null);
        	character.setStories(null);
        	character.setThumbnail(null);
        }

        return target;
    }

    @Override
    public Optional<MarvelCharacter> getCharacter(Long id) throws RestClientException, NoSuchAlgorithmException {
		yapily.marvel.rest.client.model.response.CharacterDataContainer source = getMarvelCharactersRestClient()
				.getCharacter(id).getData();

		return Optional.of(getCharacterMapper().map(source).getResults().get(0));
    }

    @Override
    public Integer countCharacters(CharactersSearchParams params)
            throws NoSuchAlgorithmException, CloneNotSupportedException {
        return getMarvelCharactersRestClient().countCharacters(getCharactersSearchParamsMapper().map(params));
    }

    protected MarvelCharactersClientImpl getMarvelCharactersRestClient() {
        return marvelCharactersRestClient;
    }

    protected void setMarvelCharactersRestClient(MarvelCharactersClientImpl marvelCharactersRestClient) {
        this.marvelCharactersRestClient = marvelCharactersRestClient;
    }

    protected CharacterMapper getCharacterMapper() {
        return characterMapper;
    }

    protected void setCharacterMapper(CharacterMapper characterMapper) {
        this.characterMapper = characterMapper;
    }

    protected CharactersSearchParamsMapper getCharactersSearchParamsMapper() {
        return charactersSearchParamsMapper;
    }

    protected void setCharactersSearchParamsMapper(CharactersSearchParamsMapper charactersSearchParamsMapper) {
        this.charactersSearchParamsMapper = charactersSearchParamsMapper;
    }

}
