package yapily.marvel.explorer.backend.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import yapily.marvel.explorer.backend.CharactersSearchParams;
import yapily.marvel.explorer.backend.CharactersService;
import yapily.marvel.explorer.model.CharacterDataContainer;
import yapily.marvel.explorer.model.MarvelCharacter;

@Service
public class CharactersServiceMockedImpl implements CharactersService {

    protected static final int PAGE_SIZE = 25;

    private Resource repositoryFile = new ClassPathResource("characters.xml");

    private final List<MarvelCharacter> repository = initRepository();

    protected List<MarvelCharacter> initRepository() {
        try {
            JAXBContext jContext = JAXBContext.newInstance(CharacterDataContainer.class);
            Unmarshaller unmarshallerObj = jContext.createUnmarshaller();
            CharacterDataContainer container = (CharacterDataContainer) unmarshallerObj.unmarshal(repositoryFile.getInputStream());
            return container.getResults();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<MarvelCharacter> getCharacters(CharactersSearchParams params) {
        Stream<MarvelCharacter> stream = repository.stream();

        if (params != null) {
            if (StringUtils.isNotEmpty(params.getNameStartsWith())) {
                stream = stream.filter(c -> c.getName().startsWith(params.getNameStartsWith()));
            }

            if (params.getOffset() != null) {
                stream = stream.skip(params.getOffset());
            }
        }

        stream = stream.limit(params != null && params.getLimit() != null ? params.getLimit() : PAGE_SIZE);

        return stream.collect(Collectors.toList());
    }

	@Override
	public Optional<MarvelCharacter> getCharacter(Long id) {
		Objects.requireNonNull(id);

		Stream<MarvelCharacter> stream = repository.stream();
		stream = stream.filter(c -> c.getId().equals(id));

		return stream.findAny();
	}

    @Override
    public Integer countCharacters(CharactersSearchParams params) {
        return getCharacters(params).size();
    }


}
