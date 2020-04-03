package yapily.marvel.explorer.backend.impl;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import yapily.marvel.explorer.backend.CharactersSearchParams;
import yapily.marvel.explorer.backend.CharactersService;
import yapily.marvel.explorer.model.MarvelCharacter;

@Service
@Primary
public class CharactersServiceMarvelRestClientCacheableImpl extends CharactersServiceMarvelRestClientImpl implements CharactersService {

	@Autowired
	protected CacheManager cacheManager;

	@Override
	@Cacheable(value = "characters", key="T(java.util.Objects).hash(#params)")
	public List<MarvelCharacter> getCharacters(CharactersSearchParams params)
			throws NoSuchAlgorithmException, CloneNotSupportedException {
		yapily.marvel.rest.client.model.response.CharacterDataContainer source = getMarvelCharactersRestClient()
				.getCharacters(getCharactersSearchParamsMapper().map(params)).getData();

		List<MarvelCharacter> target = getCharacterMapper().map(source).getResults();

		Cache characterCache = cacheManager.getCache("character");
		for (MarvelCharacter character : target) {
			characterCache.put(character.getId(), character.clone());

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
	@Cacheable(value = "character")
	public Optional<MarvelCharacter> getCharacter(Long id) throws RestClientException, NoSuchAlgorithmException {
		return super.getCharacter(id);
	}

}
