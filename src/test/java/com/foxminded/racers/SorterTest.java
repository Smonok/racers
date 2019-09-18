package com.foxminded.racers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SorterTest {
    private final String START_PATH = "src/test/resources/startTest.log";
    private final String END_PATH = "src/test/resources/endTest.log";
    private PrintStream startFileWriter;
    private PrintStream endFileWriter;
    private Map<String, String> expectedResult;

    @BeforeEach
    void initialize() throws IOException {
        startFileWriter = new PrintStream(new FileOutputStream(START_PATH));
        endFileWriter = new PrintStream(new FileOutputStream(END_PATH));
        expectedResult = new LinkedHashMap<>();
    }

    @AfterEach
    void close() {
        startFileWriter.close();
        endFileWriter.close();
    }

    @Test
    void sortRacersShouldThrowIOExceptionWhenFilesNotFound() {
        String incorrectPath = "start";

        assertThrows(IOException.class, () -> new Sorter().sortRacers(incorrectPath, incorrectPath));
    }

    @Test
    void sortRacersShouldReturnEmptyMapWhenAllFilesEmpty() throws IOException {
        Map<String, String> actualResult = new Sorter().sortRacers(START_PATH, END_PATH);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void sortRacersShouldReturnRacerAbbreviationAndBestTimeWhenOneRacerInFiles() throws IOException {
        expectedResult.put("FAM", "1:12,657");
        startFileWriter.println("FAM2018-05-24_12:13:04.512");
        endFileWriter.println("FAM2018-05-24_12:14:17.169");

        Map<String, String> actualResult = new Sorter().sortRacers(START_PATH, END_PATH);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void sortRacersShouldReturnSortedByTimeRacersRecordsWhenSeveralRacersInFiles() throws IOException {
        expectedResult.put("SVF", "1:04,415");
        expectedResult.put("FAM", "1:12,657");
        expectedResult.put("NHR", "1:13,065");

        startFileWriter.println("SVF2018-05-24_12:02:58.917");
        startFileWriter.println("NHR2018-05-24_12:02:49.914");
        startFileWriter.println("FAM2018-05-24_12:13:04.512");

        endFileWriter.println("SVF2018-05-24_12:04:03.332");
        endFileWriter.println("FAM2018-05-24_12:14:17.169");
        endFileWriter.println("NHR2018-05-24_12:04:02.979");

        Map<String, String> actualResult = new Sorter().sortRacers(START_PATH, END_PATH);

        assertEquals(expectedResult, actualResult);
    }
}
