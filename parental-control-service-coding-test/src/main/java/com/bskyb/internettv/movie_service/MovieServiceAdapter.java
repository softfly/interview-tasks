package com.bskyb.internettv.movie_service;

import com.bskyb.internettv.parental_control_service.ErrorCallClientException;

public class MovieServiceAdapter implements MovieService {

    protected com.bskyb.internettv.thirdparty.MovieService movieService;

    @Override
    public String getParentalControlLevel(String titleId) throws TechnicalFailureException, ErrorCallClientException {
        try {
            return movieService.getParentalControlLevel(titleId);
        } catch (com.bskyb.internettv.thirdparty.TitleNotFoundException e) {
            throw new ErrorCallClientException(e);
        } catch (com.bskyb.internettv.thirdparty.TechnicalFailureException e) {
            throw new com.bskyb.internettv.movie_service.TechnicalFailureException(e);
        }
    }

    public com.bskyb.internettv.thirdparty.MovieService getMovieService() {
        return movieService;
    }

    public void setMovieService(com.bskyb.internettv.thirdparty.MovieService movieService) {
        this.movieService = movieService;
    }

}
