package com.bskyb.internettv.parental_control_service.client;

import com.bskyb.internettv.movie_service.MovieServiceAdapter;
import com.bskyb.internettv.parental_control_service.ErrorCallClientException;
import com.bskyb.internettv.parental_control_service.IllegalArgumentException;
import com.bskyb.internettv.parental_control_service.ParentalControlServiceImpl;
import com.bskyb.internettv.thirdparty.MovieService;

public class ParentalControlServiceClient {

    protected MovieService movieService = new MovieServiceMock();

    protected MovieServiceAdapter movieServiceAdapter = new MovieServiceAdapter();
    protected ParentalControlServiceImpl parentalControlService = new ParentalControlServiceImpl();

    {
        movieServiceAdapter.setMovieService(movieService);
    }

    {
        parentalControlService.setMovieService(movieServiceAdapter);
    }

    public static void main(String[] args) throws ErrorCallClientException, IllegalArgumentException {
        String customerParentalControlLevel = null;
        String movieId = null;

        if (args.length > 0) {
            customerParentalControlLevel = args[0];
        }

        if (args.length > 1) {
            customerParentalControlLevel = args[1];
        }

        (new ParentalControlServiceClient()).getParentalControlService().canWatchMovie(customerParentalControlLevel, movieId);
    }

    public ParentalControlServiceImpl getParentalControlService() {
        return parentalControlService;
    }

    public void setParentalControlService(ParentalControlServiceImpl parentalControlService) {
        this.parentalControlService = parentalControlService;
    }

}
