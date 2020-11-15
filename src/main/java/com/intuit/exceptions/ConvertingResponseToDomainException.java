package com.intuit.exceptions;

public class ConvertingResponseToDomainException extends Exception{
    public ConvertingResponseToDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
