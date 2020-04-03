package yapily.marvel.explorer.backend.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import yapily.marvel.explorer.backend.CharactersService;

public class CharactersServiceMarvelRestClientCacheableImplTest extends AbstractCharactersServiceTest {

	@Autowired
	@Qualifier("charactersServiceMarvelRestClientCacheableImpl")
    private CharactersService charactersService;

	@Override
	protected CharactersService getCharactersService() {
		return charactersService;
	}

}
