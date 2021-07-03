package com.library.Library.exception;


public class LibraryException extends RuntimeException {
    public LibraryException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public LibraryException(String exMessage) {
        super(exMessage);
    }
}
