package yapily.marvel.explorer.backend.impl;

import org.springframework.beans.factory.annotation.Autowired;

import yapily.marvel.explorer.backend.CharactersService;

public class CharactersServiceMarvelRestClientImplTest extends AbstractCharactersServiceTest {

	@Autowired
    private CharactersServiceMarvelRestClientImpl charactersService;

	@Override
	protected CharactersService getCharactersService() {
		return charactersService;
	}

}
