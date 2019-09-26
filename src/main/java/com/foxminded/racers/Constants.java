package com.foxminded.racers;

public final class Constants {

    private Constants() {
        throw new AssertionError();
    }

    public static final String START_FILE_NAME = "start.log";
    public static final String END_FILE_NAME = "end.log";
    public static final String ABBREVIATIONS_FILE_NAME = "abbreviations.txt";

    public static final String START_FILE_NAME_TEST = "startTest.log";
    public static final String END_FILE_NAME_TEST = "endTest.log";
    public static final String ABBREVIATIONS_FILE_NAME_TEST = "abbreviationsTest.txt";
    public static final String INCORRECT_NAME = "incorrect.txt";

    public static final int MAX_NAME_LENGTH = 20;
    public static final int MAX_CAR_BRAND_LENGTH = 25;
    public static final int RACER_ABBREVIATION_LENGTH = 3;
    public static final int BEGIN_TIME_INDEX = 14;    
}
