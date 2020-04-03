package yapily.marvel.explorer.backend.impl;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import yapily.marvel.Application;
import yapily.marvel.explorer.backend.CharactersSearchParams;
import yapily.marvel.explorer.backend.CharactersService;
import yapily.marvel.explorer.model.EventSummary;
import yapily.marvel.explorer.model.MarvelCharacter;
import yapily.marvel.explorer.model.SeriesSummary;
import yapily.marvel.explorer.model.StorySummary;

/**
 * @TODO MarvelCharactersClientImpl should be mocked.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public abstract class AbstractCharactersServiceTest {

    @Test
    public void getCharactersNullTest() throws Exception {
        List<MarvelCharacter> characters = getCharactersService().getCharacters(null);
        assertTrue(characters.size() > 0);
        for (MarvelCharacter character : characters) {
        	assertNotNullGetCharaters(character);
        }
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
        	assertNotNullGetCharaters(character);
        }
    }

    protected void assertNotNullGetCharaters(MarvelCharacter character) {
        assertNotNull(character.getId());
        assertNotNull(character.getName());
        assertNull(character.getDescription());
        assertNull(character.getThumbnail());
        assertNull(character.getEvents());
        assertNull(character.getSeries());
        assertNull(character.getStories());
    }

    @Test
    public void getCharacterTest() throws Exception {
        Optional<MarvelCharacter> response = getCharactersService().getCharacter(1011334l);
        assertNotNullGetCharater(response.get());
    }

    protected void assertNotNullGetCharater(MarvelCharacter character) {
    	assertNotNull(character);
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

    /*
     *  Create repository for mocked version.
	@Test
	public void marshallCharacters() throws NoSuchAlgorithmException {
		CharactersSearchParams params = new CharactersSearchParams();
		params.setLimit(100);
		CharacterDataContainer container = new CharacterDataContainer();
		container.setResults(getCharactersService().getCharacters(params));

		try {
			JAXBContext jContext = JAXBContext.newInstance(CharacterDataContainer.class);
			Marshaller marshallObj = jContext.createMarshaller();
			marshallObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshallObj.marshal(container, new FileOutputStream("characters.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

    protected abstract CharactersService getCharactersService();

}
