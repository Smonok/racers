package com.foxminded.racers;

@SuppressWarnings("serial")
public class MissingLineException extends RuntimeException{
    
    public MissingLineException(String message) {
        super(message);
    }
}
