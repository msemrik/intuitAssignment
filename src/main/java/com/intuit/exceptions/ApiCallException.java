package com.intuit.exceptions;

public class ApiCallException extends Exception{
    public ApiCallException(String message, Throwable cause) {
        super(message, cause);
    }
}
