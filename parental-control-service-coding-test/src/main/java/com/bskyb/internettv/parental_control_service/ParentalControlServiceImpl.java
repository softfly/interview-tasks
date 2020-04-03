package com.bskyb.internettv.parental_control_service;

import com.bskyb.internettv.movie_service.MovieService;
import com.bskyb.internettv.movie_service.TechnicalFailureException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ParentalControlServiceImpl implements ParentalControlService {

    private static final Logger LOGGER = Logger.getLogger(ParentalControlServiceImpl.class.getName());

    /**
     * Best practices, for external service was used Adapter pattern for loose coupling (Open Closed Principle).
     * This is an example. In real project use this pattern dependencies than more factors.
     */
    private MovieService movieService;

    @Override
    public boolean canWatchMovie(String customerParentalControlLevel, String movieId)
            throws ErrorCallClientException, IllegalArgumentException {
        try {

            // 1. Validation
            /** Instead of Bean Validation JSR-349, Stategy Pattern could be used
             https://dzone.com/articles/avoiding-many-if-blocks*/
            CanWatchMovieValidation.validateCustomerParentalControlLevel(customerParentalControlLevel);
            ParentalControlLevel c1 = ParentalControlLevel.getEnum(customerParentalControlLevel);
            CanWatchMovieValidation.validateMovieId(movieId);


            // 2. Call MovieService
            String c2S = getMovieService().getParentalControlLevel(movieId);
            CanWatchMovieValidation.validateCustomerParentalControlLevel(c2S);
            ParentalControlLevel c2 = ParentalControlLevel.getEnum(c2S);


            // 3. Calculate canWatchMovie
            if (c1.compareTo(c2) >= 0) {
                return true;
            } else {
                return false;
            }
        } catch (java.lang.IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (ErrorCallClientException e) {
            throw e;
        } catch (TechnicalFailureException e) {
            // System error
            // Indicate that the customer cannot watch this movie
            LOGGER.log(Level.WARNING, ParentalControlServiceCnsts.GOT_EXCEPTION, e);
            return false;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, ParentalControlServiceCnsts.GOT_EXCEPTION, e);
            return false;
        }
    }

    public MovieService getMovieService() {
        return movieService;
    }

    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

}
