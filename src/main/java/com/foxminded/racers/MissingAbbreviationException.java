package com.foxminded.racers;

public class MissingAbbreviationException extends Exception{
    private static final long serialVersionUID = 1L;
    private static String message = "Abrreviation missed";
    
    public MissingAbbreviationException() {
        super(message);
    }
}
