package yapily.marvel.rest.client;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import yapily.marvel.commons.utils.ValidatorUtil;
import yapily.marvel.rest.client.model.response.CharacterDataWrapper;

@Service
public class MarvelCharactersClientImpl {

    protected final static String MARVEL_API_URL = "https://gateway.marvel.com:443/";

    protected final static String MARVEL_API_CHARACTERS_URL = MARVEL_API_URL + "v1/public/characters";

    protected final static String MARVEL_API_CHARACTER_URL = MARVEL_API_URL + "v1/public/characters/{characterId}";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ValidatorUtil validator;

    @Autowired
    private MarvelAuthClient marvelAuthClient;

    /**
     * https://developer.marvel.com/docs#!/public/getCreatorCollection_get_0
     */
    public CharacterDataWrapper getCharacters(CharactersSearchParams params) throws NoSuchAlgorithmException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(MARVEL_API_CHARACTERS_URL);

        if (params != null) {
            getValidator().valid(params);
            if (StringUtils.isNotEmpty(params.getNameStartsWith())) {
                builder = builder.queryParam(CharactersSearchParams.PARAM_NAME_STARTS_WITH, params.getNameStartsWith());
            }
            if (params.getLimit() != null) {
                builder = builder.queryParam(CharactersSearchParams.PARAM_LIMIT, params.getLimit());
            }
            if (params.getOffset() != null) {
                builder = builder.queryParam(CharactersSearchParams.PARAM_OFFSET, params.getOffset());
            }
        }
        builder = getMarvelAuthClient().enrichAuthParams(builder);

        return getRestTemplate().getForObject(builder.toUriString(), CharacterDataWrapper.class);
    }

    /**
     * https://developer.marvel.com/docs#!/public/getCharacterIndividual_get_1
     */
    public CharacterDataWrapper getCharacter(Long id) throws RestClientException, NoSuchAlgorithmException {
    	Objects.requireNonNull(id);

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("characterId", Long.toString(id));

    	UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(MARVEL_API_CHARACTER_URL);
    	builder = getMarvelAuthClient().enrichAuthParams(builder);

        return getRestTemplate().getForObject(builder.build(uriVariables), CharacterDataWrapper.class);

    }

    public Integer countCharacters(CharactersSearchParams params) throws NoSuchAlgorithmException, CloneNotSupportedException {
        CharactersSearchParams countParams = params.clone();
        countParams.setLimit(1);
        countParams.setOffset(0);

        CharacterDataWrapper response = getCharacters(countParams);
        return response.getData().getTotal();
    }

    protected RestTemplate getRestTemplate() {
        return restTemplate;
    }

    protected void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    protected ValidatorUtil getValidator() {
        return validator;
    }

    protected void setValidator(ValidatorUtil validator) {
        this.validator = validator;
    }

    protected MarvelAuthClient getMarvelAuthClient() {
        return marvelAuthClient;
    }

    protected void setMarvelAuthClient(MarvelAuthClient marvelAuthClient) {
        this.marvelAuthClient = marvelAuthClient;
    }

}
