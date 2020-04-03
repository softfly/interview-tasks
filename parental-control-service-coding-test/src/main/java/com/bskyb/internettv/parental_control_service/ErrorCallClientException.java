package com.bskyb.internettv.parental_control_service;

public class ErrorCallClientException extends Exception {

    public ErrorCallClientException() {
        super();
    }

    public ErrorCallClientException(String message) {
        super(message);
    }


    public ErrorCallClientException(String message, Throwable cause) {
        super(message, cause);
    }


    public ErrorCallClientException(Throwable cause) {
        super(cause);
    }
}
