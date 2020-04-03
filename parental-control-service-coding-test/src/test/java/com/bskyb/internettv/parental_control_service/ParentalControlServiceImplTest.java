package com.bskyb.internettv.parental_control_service;

import com.bskyb.internettv.movie_service.MovieService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ParentalControlServiceImplTest {

    protected static final String[] ALLOWED_PARENTAL_CONTROL_LEVELS = {"U", "PG", "12", "15", "18"};

    protected static final String EXAMPLE_MOVIEID = ParentalControlServiceCnsts.MOVIE_ID;

    protected static final String EXAMPLE_CUSTOMER_PARENTAL_CONTROL_LEVEL = ALLOWED_PARENTAL_CONTROL_LEVELS[0];

    @InjectMocks
    protected ParentalControlServiceImpl parentalControlService = new ParentalControlServiceImpl();

    @Mock
    protected MovieService movieService;

    @Test(expected = IllegalArgumentException.class)
    public void testNullCustomerParentalControlLevelCanWatchMovie() throws Exception {
        try {
            parentalControlService.canWatchMovie(null, EXAMPLE_MOVIEID);
        } catch (IllegalArgumentException e) {
            assertEquals("customerParentalControlLevel must not be null", e.getMessage());
            throw e;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullMovieIdCanWatchMovie() throws Exception {
        try {
            parentalControlService.canWatchMovie(EXAMPLE_CUSTOMER_PARENTAL_CONTROL_LEVEL, null);
        } catch (IllegalArgumentException e) {
            assertEquals("movieId must not be null", e.getMessage());
            throw e;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyCustomerParentalControlLevelCanWatchMovie() throws Exception {
        try {
            parentalControlService.canWatchMovie("", EXAMPLE_MOVIEID);
        } catch (IllegalArgumentException e) {
            assertEquals("customerParentalControlLevel must not be empty", e.getMessage());
            throw e;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyMovieIdCanWatchMovie() throws Exception {
        try {
            parentalControlService.canWatchMovie(EXAMPLE_CUSTOMER_PARENTAL_CONTROL_LEVEL, "");
        } catch (IllegalArgumentException e) {
            assertEquals("movieId must not be empty", e.getMessage());
            throw e;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotAllowedCustomerParentalControlLevelCanWatchMovie() throws Exception {
        try {
            parentalControlService.canWatchMovie("A", EXAMPLE_MOVIEID);
        } catch (IllegalArgumentException e) {
            assertEquals("customerParentalControlLevel must match \"U|PG|12|15|18|18\"", e.getMessage());
            throw e;
        }
    }

    @Test
    public void testReturnTrueCanWatchMovie() throws Exception {
        for (int i = 0; i < ALLOWED_PARENTAL_CONTROL_LEVELS.length; i++) {
            String cpcl1 = ALLOWED_PARENTAL_CONTROL_LEVELS[i];
            for (int ii = 0; ii <= i; ii++) {
                String cpcl2 = ALLOWED_PARENTAL_CONTROL_LEVELS[ii];
                String msg = new StringBuilder().append("ParentalControlService.customerParentalControlLevel=")
                        .append(cpcl1).append(", MovieService.customerParentalControlLevel=").append(cpcl2).toString();
                //System.out.println(msg);
                Mockito.when(movieService.getParentalControlLevel(EXAMPLE_MOVIEID)).thenReturn(cpcl2);
                assertTrue(msg, parentalControlService.canWatchMovie(cpcl1, EXAMPLE_MOVIEID));
            }
        }
    }

    @Test
    public void testReturnFalseCanWatchMovie() throws Exception {
        for (int i = 0; i < ALLOWED_PARENTAL_CONTROL_LEVELS.length; i++) {
            String cpcl1 = ALLOWED_PARENTAL_CONTROL_LEVELS[i];
            for (int ii = i + 1; ii < ALLOWED_PARENTAL_CONTROL_LEVELS.length; ii++) {
                String cpcl2 = ALLOWED_PARENTAL_CONTROL_LEVELS[ii];
                String msg = new StringBuilder().append("ParentalControlService.customerParentalControlLevel=")
                        .append(cpcl1).append(", MovieService.customerParentalControlLevel=").append(cpcl2).toString();
                //System.out.println(msg);
                Mockito.when(movieService.getParentalControlLevel(EXAMPLE_MOVIEID)).thenReturn(cpcl2);
                assertFalse(msg, parentalControlService.canWatchMovie(cpcl1, EXAMPLE_MOVIEID));
            }
        }
    }

}
