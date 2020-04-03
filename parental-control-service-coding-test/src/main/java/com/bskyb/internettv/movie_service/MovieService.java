package com.bskyb.internettv.movie_service;

import com.bskyb.internettv.parental_control_service.ErrorCallClientException;

public interface MovieService {
    String getParentalControlLevel(String titleId) throws TechnicalFailureException, ErrorCallClientException;
}
