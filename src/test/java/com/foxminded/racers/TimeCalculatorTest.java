package com.foxminded.racers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TimeCalculatorTest {
    private static final TimeCalculator racersTime = new TimeCalculator();
    private Map<String, String> expectedResult;
    private PrintStream startFileWriter;
    private PrintStream endFileWriter;    

    @BeforeEach
    void initialize() throws IOException {
        startFileWriter = InitializerUtil.initializeFileWriter(Constants.START_FILE_NAME_TEST);
        endFileWriter = InitializerUtil.initializeFileWriter(Constants.END_FILE_NAME_TEST);
        expectedResult = new HashMap<>();
    }

    @AfterEach
    void close() {
        startFileWriter.close();
        endFileWriter.close();
    }

    @Test
    void createRacersTimeShouldThrowNullPointerExceptionWhenFirstFileNotFound() {

        assertThrows(NullPointerException.class,
                () -> racersTime.createRacersTime(Constants.INCORRECT_NAME, Constants.END_FILE_NAME_TEST));
    }

    @Test
    void createRacersTimeShouldThrowNullPointerExceptionWhenSecondFileNotFound() {

        assertThrows(NullPointerException.class,
                () -> racersTime.createRacersTime(Constants.START_FILE_NAME_TEST, Constants.INCORRECT_NAME));
    }

    @Test
    void createRacersTimeShouldThrowMissingLineExceptionWhenDifferentFileLengths() {
        endFileWriter.println("FAM2018-05-24_12:14:17.169");

        assertThrows(MissingLineException.class,
                () -> racersTime.createRacersTime(Constants.START_FILE_NAME_TEST, Constants.END_FILE_NAME_TEST));
    }

    @Test
    void createRacersTimeShouldReturnEmptyMapWhenBothFilesEmpty() throws IOException {
        Map<String, String> actualResult = racersTime.createRacersTime(Constants.START_FILE_NAME_TEST,
                Constants.END_FILE_NAME_TEST);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createRacersTimeShouldReturnRacerAbbreviationAndBestTimeWhenOneRacerInFiles() throws IOException {

        expectedResult.put("FAM", "1:12,657");
        startFileWriter.println("FAM2018-05-24_12:13:04.512");
        endFileWriter.println("FAM2018-05-24_12:14:17.169");

        Map<String, String> actualResult = racersTime.createRacersTime(Constants.START_FILE_NAME_TEST,
                Constants.END_FILE_NAME_TEST);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createRacersTimeShouldReturnRacersAbbreviationsAndBestTimeWhenSeveralRacersInFiles() throws IOException {

        InitializerUtil.putThreeRacers(expectedResult);

        startFileWriter.println("SVF2018-05-24_12:02:58.917");
        startFileWriter.println("NHR2018-05-24_12:02:49.914");
        startFileWriter.println("FAM2018-05-24_12:13:04.512");

        endFileWriter.println("SVF2018-05-24_12:04:03.332");
        endFileWriter.println("FAM2018-05-24_12:14:17.169");
        endFileWriter.println("NHR2018-05-24_12:04:02.979");

        Map<String, String> actualResult = racersTime.createRacersTime(Constants.START_FILE_NAME_TEST,
                Constants.END_FILE_NAME_TEST);

        assertEquals(expectedResult, actualResult);
    }
}
