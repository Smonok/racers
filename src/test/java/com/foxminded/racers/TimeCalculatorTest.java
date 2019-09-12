package com.foxminded.racers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class TimeCalculatorTest {

    private final TimeCalculator racersTime = new TimeCalculator();
    private final String startPath = "src/test/resources/startTest.log";
    private final String endPath = "src/test/resources/endTest.log";

    @Test
    void createRacersTimeShouldThrowIOExceptionWhenFirstFileNotFound() {
        String incorrectPath = "start";

        assertThrows(IOException.class, () -> racersTime.createRacersTime(incorrectPath, endPath));
    }

    @Test
    void createRacersTimeShouldThrowIOExceptionWhenSecondFileNotFound() {
        String incorrectPath = "end";

        assertThrows(IOException.class, () -> racersTime.createRacersTime(startPath, incorrectPath));
    }

    @Test
    void createRacersTimeShouldThrowMissingAbbreviationExceptionWhenDifferentFileLengths()
            throws MissingAbbreviationException, IOException {
        PrintStream startWriter = new PrintStream(new FileOutputStream(startPath));
        PrintStream endWriter = new PrintStream(new FileOutputStream(endPath));

        endWriter.println("FAM2018-05-24_12:14:17.169");
        startWriter.close();
        endWriter.close();

        assertThrows(MissingAbbreviationException.class, () -> racersTime.createRacersTime(startPath, endPath));
    }

    @Test
    void createRacersTimeShouldReturnEmptyMapWhenBothFilesEmpty() throws IOException, MissingAbbreviationException {
        PrintStream startWriter = new PrintStream(new FileOutputStream(startPath));
        PrintStream endWriter = new PrintStream(new FileOutputStream(endPath));
        Map<String, String> expectedResult = new HashMap<>();
        Map<String, String> actualResult = racersTime.createRacersTime(startPath, endPath);

        startWriter.close();
        endWriter.close();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createRacersTimeShouldReturnRacerAbbreviationAndBestTimeWhenOneRacerInFiles()
            throws IOException, MissingAbbreviationException {
        Map<String, String> expectedResult = new HashMap<>();
        PrintStream startWriter = new PrintStream(new FileOutputStream(startPath));
        PrintStream endWriter = new PrintStream(new FileOutputStream(endPath));

        expectedResult.put("FAM", "1:12,657");
        startWriter.println("FAM2018-05-24_12:13:04.512");
        endWriter.println("FAM2018-05-24_12:14:17.169");

        Map<String, String> actualResult = racersTime.createRacersTime(startPath, endPath);

        startWriter.close();
        endWriter.close();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createRacersTimeShouldReturnRacersAbbreviationsAndBestTimeWhenSeveralRacersInFiles()
            throws IOException, MissingAbbreviationException {
        Map<String, String> expectedResult = new HashMap<>();
        PrintStream startWriter = new PrintStream(new FileOutputStream(startPath));
        PrintStream endWriter = new PrintStream(new FileOutputStream(endPath));

        expectedResult.put("SVF", "1:04,415");
        expectedResult.put("NHR", "1:13,065");
        expectedResult.put("FAM", "1:12,657");

        startWriter.println("SVF2018-05-24_12:02:58.917");
        startWriter.println("NHR2018-05-24_12:02:49.914");
        startWriter.println("FAM2018-05-24_12:13:04.512");

        endWriter.println("SVF2018-05-24_12:04:03.332");
        endWriter.println("FAM2018-05-24_12:14:17.169");
        endWriter.println("NHR2018-05-24_12:04:02.979");

        Map<String, String> actualResult = racersTime.createRacersTime(startPath, endPath);

        startWriter.close();
        endWriter.close();
        assertEquals(expectedResult, actualResult);
    }
}
