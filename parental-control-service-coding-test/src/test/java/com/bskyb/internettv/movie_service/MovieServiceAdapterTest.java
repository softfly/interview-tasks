package com.bskyb.internettv.movie_service;

import com.bskyb.internettv.parental_control_service.ErrorCallClientException;
import com.bskyb.internettv.parental_control_service.ParentalControlServiceCnsts;
import com.bskyb.internettv.parental_control_service.ParentalControlServiceImpl;
import com.bskyb.internettv.thirdparty.MovieService;
import com.bskyb.internettv.thirdparty.TechnicalFailureException;
import com.bskyb.internettv.thirdparty.TitleNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceAdapterTest {

    protected static final String[] ALLOWED_PARENTAL_CONTROL_LEVELS = {"U", "PG", "12", "15", "18"};

    protected static final String EXAMPLE_MOVIEID = ParentalControlServiceCnsts.MOVIE_ID;

    protected static final String EXAMPLE_CUSTOMER_PARENTAL_CONTROL_LEVEL = ALLOWED_PARENTAL_CONTROL_LEVELS[0];

    @InjectMocks
    protected MovieServiceAdapter movieServiceAdapter = new MovieServiceAdapter();

    @InjectMocks
    protected ParentalControlServiceImpl parentalControlService = new ParentalControlServiceImpl();
    @Mock
    protected MovieService movieService;

    {
        parentalControlService.setMovieService(movieServiceAdapter);
    }

    @Test(expected = ErrorCallClientException.class)
    public void testTitleNotFoundCanWatchMovie() throws Exception {
        Mockito.when(movieService.getParentalControlLevel(EXAMPLE_MOVIEID)).thenThrow(new TitleNotFoundException());
        parentalControlService.canWatchMovie(EXAMPLE_CUSTOMER_PARENTAL_CONTROL_LEVEL, EXAMPLE_MOVIEID);
    }

    @Test
    public void testTechnicalFailureExceptionCanWatchMovie() throws Exception {
        Mockito.when(movieService.getParentalControlLevel(EXAMPLE_MOVIEID)).thenThrow(new TechnicalFailureException());
        assertFalse(parentalControlService.canWatchMovie(EXAMPLE_CUSTOMER_PARENTAL_CONTROL_LEVEL, EXAMPLE_MOVIEID));
    }

}
