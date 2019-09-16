package com.foxminded.racers;

public class MissingLineException extends RuntimeException{
    private static final long serialVersionUID = 1L;
        
    public MissingLineException(String message) {
        super(message);
    }
}
