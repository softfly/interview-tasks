package yapily.marvel.explorer.backend.impl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import yapily.marvel.Application;
import yapily.marvel.explorer.backend.CharactersSearchParams;
import yapily.marvel.explorer.backend.CharactersService;
import yapily.marvel.explorer.model.EventSummary;
import yapily.marvel.explorer.model.MarvelCharacter;
import yapily.marvel.explorer.model.SeriesSummary;
import yapily.marvel.explorer.model.StorySummary;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CharactersServiceMockedImplTest {

    @Autowired
    private CharactersServiceMockedImpl charactersService;

    @Test
    public void getCharactersNullTest() throws Exception {
        List<MarvelCharacter> characters = getCharactersService().getCharacters(null);
        assertTrue(characters.size() > 0);
        assertNotNullCharater(characters.get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCharactersMinusOffsetTest() throws Exception {
        CharactersSearchParams searchParams = new CharactersSearchParams();
        searchParams.setOffset(-1);
        getCharactersService().getCharacters(searchParams);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCharactersMinusLimitTest() throws Exception {
        CharactersSearchParams searchParams = new CharactersSearchParams();
        searchParams.setLimit(-1);
        getCharactersService().getCharacters(searchParams);
    }

    @Test
    public void getCharactersFullTest() throws Exception {
        CharactersSearchParams searchParams = new CharactersSearchParams();
        searchParams.setOffset(10);
        searchParams.setLimit(1);

        List<MarvelCharacter> characters = getCharactersService().getCharacters(searchParams);
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
    public void countCharactersTest() throws Exception {
        CharactersSearchParams searchParams = new CharactersSearchParams();
        searchParams.setOffset(10);
        searchParams.setLimit(1);

        Integer count = getCharactersService().countCharacters(searchParams);
        assertNotNull(count);
    }

    protected CharactersService getCharactersService() {
        return charactersService;
    }

    protected void setCharactersService(CharactersServiceMockedImpl charactersService) {
        this.charactersService = charactersService;
    }

}
