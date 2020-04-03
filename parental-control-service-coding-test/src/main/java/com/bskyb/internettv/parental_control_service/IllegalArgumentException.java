package com.bskyb.internettv.parental_control_service;

public class IllegalArgumentException extends Exception {

    public IllegalArgumentException() {
        super();
    }

    public IllegalArgumentException(String message) {
        super(message);
    }


    public IllegalArgumentException(String message, Throwable cause) {
        super(message, cause);
    }


    public IllegalArgumentException(Throwable cause) {
        super(cause);
    }
}
