package com.foxminded.racers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SorterTest {
    private final Sorter sortedRacers = new Sorter();
    private final String START_PATH = "src/test/resources/startTest.log";
    private final String END_PATH = "src/test/resources/endTest.log";
    private final String ABBREVIATIONS_PATH = "src/test/resources/abbreviationsTest.txt";
    private PrintStream startWriter;
    private PrintStream endWriter;
    private PrintStream abbreviationsWriter;
    private List<String> expectedResult;

    @BeforeEach
    void initialize() throws IOException {
        startWriter = new PrintStream(new FileOutputStream(START_PATH));
        endWriter = new PrintStream(new FileOutputStream(END_PATH));
        abbreviationsWriter = new PrintStream(new FileOutputStream(ABBREVIATIONS_PATH));
        expectedResult = new ArrayList<>();
    }

    @AfterEach
    void close() {
        startWriter.close();
        endWriter.close();
        abbreviationsWriter.close();
    }

    @Test
    void createSortedRacersShouldThrowIOExceptionWhenFilesNotFound() {
        String incorrectPath = "start";

        assertThrows(IOException.class,
                () -> sortedRacers.createSortedRacers(incorrectPath, incorrectPath, incorrectPath));
    }

    @Test
    void createSortedRacersShouldThrowMissingLineExceptionWhenDifferentFileLengths()
            throws MissingLineException, IOException {

        startWriter.println("FAM2018-05-24_12:13:04.512");
        endWriter.println("FAM2018-05-24_12:14:17.169");
        abbreviationsWriter.println("FAM_Fernando Alonso_MCLAREN RENAULT");
        abbreviationsWriter.println("NHR_Nico Hulkenberg_RENAULT");

        assertThrows(MissingLineException.class,
                () -> sortedRacers.createSortedRacers(START_PATH, END_PATH, ABBREVIATIONS_PATH));
    }

    @Test
    void createSortedRacersShouldReturnEmptyListWhenAllFilesEmpty() throws IOException, MissingLineException {
        List<String> actualResult = sortedRacers.createSortedRacers(START_PATH, END_PATH, ABBREVIATIONS_PATH);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createSortedRacersShouldReturnRacerCarAndBestTimeWhenOneRacerInFile()
            throws IOException, MissingLineException {

        expectedResult.add("Fernando Alonso      | MCLAREN RENAULT           | 1:12,657");
        startWriter.println("FAM2018-05-24_12:13:04.512");
        endWriter.println("FAM2018-05-24_12:14:17.169");
        abbreviationsWriter.println("FAM_Fernando Alonso_MCLAREN RENAULT");

        List<String> actualResult = sortedRacers.createSortedRacers(START_PATH, END_PATH, ABBREVIATIONS_PATH);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createSortedRacersShouldReturnListOfSortedRacersByTimeWhenSeveralRacersInFiles()
            throws IOException, MissingLineException {

        expectedResult.add("Sebastian Vettel     | FERRARI                   | 1:04,415");
        expectedResult.add("Fernando Alonso      | MCLAREN RENAULT           | 1:12,657");
        expectedResult.add("Nico Hulkenberg      | RENAULT                   | 1:13,065");

        startWriter.println("SVF2018-05-24_12:02:58.917");
        startWriter.println("NHR2018-05-24_12:02:49.914");
        startWriter.println("FAM2018-05-24_12:13:04.512");

        endWriter.println("SVF2018-05-24_12:04:03.332");
        endWriter.println("FAM2018-05-24_12:14:17.169");
        endWriter.println("NHR2018-05-24_12:04:02.979");

        abbreviationsWriter.println("FAM_Fernando Alonso_MCLAREN RENAULT");
        abbreviationsWriter.println("SVF_Sebastian Vettel_FERRARI");
        abbreviationsWriter.println("NHR_Nico Hulkenberg_RENAULT");

        List<String> actualResult = sortedRacers.createSortedRacers(START_PATH, END_PATH, ABBREVIATIONS_PATH);

        assertEquals(expectedResult, actualResult);
    }
}
