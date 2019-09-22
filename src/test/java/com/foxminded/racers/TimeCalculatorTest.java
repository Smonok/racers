package com.foxminded.racers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimeCalculatorTest {
    private static final TimeCalculator racersTime = new TimeCalculator();
    private Map<String, String> expectedResult;
    private PrintStream startFileWriter;
    private PrintStream endFileWriter;
    private static final ClassLoader loader = TimeCalculator.class.getClassLoader();

    @BeforeEach
    void initialize() throws IOException {
        File startTest = new File(loader.getResource(Constants.START_TEST_FILE_NAME)
                .getFile());
        File endTest = new File(loader.getResource(Constants.END_TEST_FILE_NAME)
                .getFile());
        startFileWriter = new PrintStream(new FileOutputStream(startTest.getAbsolutePath()));
        endFileWriter = new PrintStream(new FileOutputStream(endTest.getAbsolutePath()));
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
                () -> racersTime.createRacersTime(Constants.INCORRECT_NAME, Constants.END_TEST_FILE_NAME));
    }

    @Test
    void createRacersTimeShouldThrowNullPointerExceptionWhenSecondFileNotFound() {

        assertThrows(NullPointerException.class,
                () -> racersTime.createRacersTime(Constants.START_TEST_FILE_NAME, Constants.INCORRECT_NAME));
    }

    @Test
    void createRacersTimeShouldThrowMissingLineExceptionWhenDifferentFileLengths() {
        endFileWriter.println("FAM2018-05-24_12:14:17.169");

        assertThrows(MissingLineException.class,
                () -> racersTime.createRacersTime(Constants.START_TEST_FILE_NAME, Constants.END_TEST_FILE_NAME));
    }

    @Test
    void createRacersTimeShouldReturnEmptyMapWhenBothFilesEmpty() throws IOException {
        Map<String, String> actualResult = racersTime.createRacersTime(Constants.START_TEST_FILE_NAME,
                Constants.END_TEST_FILE_NAME);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createRacersTimeShouldReturnRacerAbbreviationAndBestTimeWhenOneRacerInFiles() throws IOException {

        expectedResult.put("FAM", "1:12,657");
        startFileWriter.println("FAM2018-05-24_12:13:04.512");
        endFileWriter.println("FAM2018-05-24_12:14:17.169");

        Map<String, String> actualResult = racersTime.createRacersTime(Constants.START_TEST_FILE_NAME,
                Constants.END_TEST_FILE_NAME);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createRacersTimeShouldReturnRacersAbbreviationsAndBestTimeWhenSeveralRacersInFiles() throws IOException {

        expectedResult.put("SVF", "1:04,415");
        expectedResult.put("NHR", "1:13,065");
        expectedResult.put("FAM", "1:12,657");

        startFileWriter.println("SVF2018-05-24_12:02:58.917");
        startFileWriter.println("NHR2018-05-24_12:02:49.914");
        startFileWriter.println("FAM2018-05-24_12:13:04.512");

        endFileWriter.println("SVF2018-05-24_12:04:03.332");
        endFileWriter.println("FAM2018-05-24_12:14:17.169");
        endFileWriter.println("NHR2018-05-24_12:04:02.979");

        Map<String, String> actualResult = racersTime.createRacersTime(Constants.START_TEST_FILE_NAME,
                Constants.END_TEST_FILE_NAME);

        assertEquals(expectedResult, actualResult);
    }
}
