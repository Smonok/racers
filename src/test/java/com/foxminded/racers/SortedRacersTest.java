package com.foxminded.racers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class SortedRacersTest {

    private final SortedRacers sortedRacers = new SortedRacers();
    private final String startPath = "src/test/resources/startTest.log";
    private final String endPath = "src/test/resources/endTest.log";
    private final String abbreviationsPath = "src/test/resources/abbreviationsTest.txt";

    @Test
    void createSortedRacersShouldThrowIOExceptionWhenFilesNotFound() {
        String incorrectPath = "start";

        assertThrows(IOException.class,
                () -> sortedRacers.createSortedRacers(incorrectPath, incorrectPath, incorrectPath));
    }

    @Test
    void createSortedRacersShouldThrowMissingAbbreviationExceptionWhenDifferentFileLengths()
            throws MissingAbbreviationException, IOException {
        PrintStream startWriter = new PrintStream(new FileOutputStream(startPath));
        PrintStream endWriter = new PrintStream(new FileOutputStream(endPath));
        PrintStream abbreviationsWriter = new PrintStream(new FileOutputStream(abbreviationsPath));

        startWriter.println("FAM2018-05-24_12:13:04.512");
        endWriter.println("FAM2018-05-24_12:14:17.169");
        abbreviationsWriter.println("FAM_Fernando Alonso_MCLAREN RENAULT");
        abbreviationsWriter.println("NHR_Nico Hulkenberg_RENAULT");
        
        startWriter.close();
        endWriter.close();
        abbreviationsWriter.close();

        assertThrows(MissingAbbreviationException.class, () -> sortedRacers.createSortedRacers(startPath, endPath, abbreviationsPath));
    }

    @Test
    void createSortedRacersShouldReturnEmptyListWhenAllFilesEmpty() throws IOException, MissingAbbreviationException {
        PrintStream startWriter = new PrintStream(new FileOutputStream(startPath));
        PrintStream endWriter = new PrintStream(new FileOutputStream(endPath));
        PrintStream abbreviationsWriter = new PrintStream(new FileOutputStream(abbreviationsPath));
        List<String> expectedResult = new ArrayList<>();
        List<String> actualResult = sortedRacers.createSortedRacers(startPath, endPath, abbreviationsPath);

        startWriter.close();
        endWriter.close();
        abbreviationsWriter.close();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createSortedRacersShouldReturnRacerCarAndBestTimeWhenOneRacerInFile()
            throws IOException, MissingAbbreviationException {
        PrintStream startWriter = new PrintStream(new FileOutputStream(startPath));
        PrintStream endWriter = new PrintStream(new FileOutputStream(endPath));
        PrintStream abbreviationsWriter = new PrintStream(new FileOutputStream(abbreviationsPath));
        List<String> expectedResult = new ArrayList<>();

        expectedResult.add("Fernando Alonso      | MCLAREN RENAULT           | 1:12,657");
        startWriter.println("FAM2018-05-24_12:13:04.512");
        endWriter.println("FAM2018-05-24_12:14:17.169");
        abbreviationsWriter.println("FAM_Fernando Alonso_MCLAREN RENAULT");

        List<String> actualResult = sortedRacers.createSortedRacers(startPath, endPath, abbreviationsPath);

        startWriter.close();
        endWriter.close();
        abbreviationsWriter.close();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void createSortedRacersShouldReturnListOfSortedRacersByTimeWhenSeveralRacersInFiles()
            throws IOException, MissingAbbreviationException {
        PrintStream startWriter = new PrintStream(new FileOutputStream(startPath));
        PrintStream endWriter = new PrintStream(new FileOutputStream(endPath));
        PrintStream abbreviationsWriter = new PrintStream(new FileOutputStream(abbreviationsPath));
        List<String> expectedResult = new ArrayList<>();

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

        List<String> actualResult = sortedRacers.createSortedRacers(startPath, endPath, abbreviationsPath);

        startWriter.close();
        endWriter.close();
        abbreviationsWriter.close();
        assertEquals(expectedResult, actualResult);
    }
}
