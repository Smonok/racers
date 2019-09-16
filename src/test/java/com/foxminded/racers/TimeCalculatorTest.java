package com.foxminded.racers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimeCalculatorTest {

    private final TimeCalculator racersTime = new TimeCalculator();
    private final String START_PATH = "src/test/resources/startTest.log";
    private final String END_PATH = "src/test/resources/endTest.log";
    private PrintStream startWriter;
    private PrintStream endWriter;

    @BeforeEach
    void initialize() throws IOException {
        startWriter = new PrintStream(new FileOutputStream(START_PATH));
        endWriter = new PrintStream(new FileOutputStream(END_PATH));
    }

    @AfterEach
    void close() {
        startWriter.close();
        endWriter.close();
    }

    @Test
    void createRacersTimeShouldThrowIOExceptionWhenFirstFileNotFound() {
        String incorrectPath = "start";

        assertThrows(IOException.class, () -> racersTime.createRacersTime(incorrectPath, END_PATH));
    }

    @Test
    void createRacersTimeShouldThrowIOExceptionWhenSecondFileNotFound() {
        String incorrectPath = "end";

        assertThrows(IOException.class, () -> racersTime.createRacersTime(START_PATH, incorrectPath));
    }

    @Test
    void createRacersTimeShouldThrowMissingLineExceptionWhenDifferentFileLengths()
            throws MissingLineException, IOException {

        endWriter.println("FAM2018-05-24_12:14:17.169");

        assertThrows(MissingLineException.class, () -> racersTime.createRacersTime(START_PATH, END_PATH));
    }

    @Test
    void createRacersTimeShouldReturnEmptyMapWhenBothFilesEmpty() throws IOException, MissingLineException {
        Map<String, String> expectedResult = new HashMap<>();
        Map<String, String> actualResult = racersTime.createRacersTime(START_PATH, END_PATH);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createRacersTimeShouldReturnRacerAbbreviationAndBestTimeWhenOneRacerInFiles()
            throws IOException, MissingLineException {
        Map<String, String> expectedResult = new HashMap<>();

        expectedResult.put("FAM", "1:12,657");
        startWriter.println("FAM2018-05-24_12:13:04.512");
        endWriter.println("FAM2018-05-24_12:14:17.169");

        Map<String, String> actualResult = racersTime.createRacersTime(START_PATH, END_PATH);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createRacersTimeShouldReturnRacersAbbreviationsAndBestTimeWhenSeveralRacersInFiles()
            throws IOException, MissingLineException {
        Map<String, String> expectedResult = new HashMap<>();

        expectedResult.put("SVF", "1:04,415");
        expectedResult.put("NHR", "1:13,065");
        expectedResult.put("FAM", "1:12,657");

        startWriter.println("SVF2018-05-24_12:02:58.917");
        startWriter.println("NHR2018-05-24_12:02:49.914");
        startWriter.println("FAM2018-05-24_12:13:04.512");

        endWriter.println("SVF2018-05-24_12:04:03.332");
        endWriter.println("FAM2018-05-24_12:14:17.169");
        endWriter.println("NHR2018-05-24_12:04:02.979");

        Map<String, String> actualResult = racersTime.createRacersTime(START_PATH, END_PATH);

        assertEquals(expectedResult, actualResult);
    }
}
