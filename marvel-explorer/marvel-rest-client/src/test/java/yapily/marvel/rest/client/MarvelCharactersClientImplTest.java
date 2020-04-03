package yapily.marvel.rest.client;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import yapily.marvel.Application;
import yapily.marvel.rest.client.model.response.CharacterDataWrapper;
import yapily.marvel.rest.client.model.response.EventSummary;
import yapily.marvel.rest.client.model.response.MarvelCharacter;
import yapily.marvel.rest.client.model.response.SeriesSummary;
import yapily.marvel.rest.client.model.response.StorySummary;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MarvelCharactersClientImplTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MarvelCharactersClientImpl charactersService;

    @Test
    public void contextLoads() {
        assertThat(restTemplate).isNotNull();
    }

    @Test
    public void getCharactersNullTest() throws NoSuchAlgorithmException {
        CharacterDataWrapper response = getCharactersService().getCharacters(null);
        assertEquals("200", response.getCode());
        assertTrue(response.getData().getResults().size() > 0);
        for (MarvelCharacter character : response.getData().getResults()) {
            assertNotNullCharater(character);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCharactersMinusOffsetTest() throws NoSuchAlgorithmException {
        CharactersSearchParams searchParams = new CharactersSearchParams();
        searchParams.setOffset(-1);
        getCharactersService().getCharacters(searchParams);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCharactersMinusLimitTest() throws NoSuchAlgorithmException {
        CharactersSearchParams searchParams = new CharactersSearchParams();
        searchParams.setLimit(-1);
        getCharactersService().getCharacters(searchParams);
    }

    @Test
    public void getCharactersFullTest() throws NoSuchAlgorithmException {
        CharactersSearchParams searchParams = new CharactersSearchParams();
        searchParams.setOffset(10);
        searchParams.setLimit(1);

        CharacterDataWrapper response = getCharactersService().getCharacters(searchParams);

        assertEquals("200", response.getCode());
        assertEquals(searchParams.getOffset(), response.getData().getOffset());
        assertEquals(searchParams.getLimit(), response.getData().getLimit());

        List<MarvelCharacter> characters = response.getData().getResults();
        assertEquals(1, characters.size());
        for (MarvelCharacter character : characters) {
            assertNotNullCharater(character);
        }
    }

    protected void assertNotNullCharater(MarvelCharacter character) {
        assertNotNull(character.getId());
        assertNotNull(character.getName());
        assertNotNull(character.getDescription());
        assertNotNull(character.getThumbnail().getExtension());
        assertNotNull(character.getThumbnail().getPath());

        assertNotNull(character.getEvents());
        for (EventSummary event : character.getEvents().getItems()) {
            assertNotNull(event.getName());
        }

        assertNotNull(character.getSeries());
        for (SeriesSummary series : character.getSeries().getItems()) {
            assertNotNull(series.getName());
        }

        assertNotNull(character.getStories());
        for (StorySummary story : character.getStories().getItems()) {
            assertNotNull(story.getName());
        }
    }

    @Test
    public void getCharacterTest() throws RestClientException, NoSuchAlgorithmException {
        CharacterDataWrapper response = getCharactersService().getCharacter(1011334l);
        assertEquals("200", response.getCode());
        assertTrue(response.getData().getResults().size() > 0);
        for (MarvelCharacter character : response.getData().getResults()) {
            assertNotNullCharater(character);
        }
    }

    @Test
    public void countCharactersTest() throws NoSuchAlgorithmException, CloneNotSupportedException {
        CharactersSearchParams searchParams = new CharactersSearchParams();
        searchParams.setOffset(10);
        searchParams.setLimit(1);

        Integer count = getCharactersService().countCharacters(searchParams);
        assertNotNull(count);
    }

    protected MarvelCharactersClientImpl getCharactersService() {
        return charactersService;
    }

    protected void setCharactersService(MarvelCharactersClientImpl charactersService) {
        this.charactersService = charactersService;
    }

}
