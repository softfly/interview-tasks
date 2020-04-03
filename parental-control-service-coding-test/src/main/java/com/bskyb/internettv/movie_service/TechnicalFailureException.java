package com.bskyb.internettv.movie_service;

public class TechnicalFailureException extends Exception {

    public TechnicalFailureException() {
        super();
    }

    public TechnicalFailureException(String message) {
        super(message);
    }


    public TechnicalFailureException(String message, Throwable cause) {
        super(message, cause);
    }


    public TechnicalFailureException(Throwable cause) {
        super(cause);
    }

}
