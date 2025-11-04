package com.github.martiandreamer;

public class InvalidClassFileFormatException extends RuntimeException {
    public InvalidClassFileFormatException() {
        super();
    }
    public InvalidClassFileFormatException(String message) {
        super(message);
    }
}
