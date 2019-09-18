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

class RecordsTest {
    private final String ABBREVIATIONS_PATH = "src/test/resources/abbreviationsTest.txt";

    private Map<String, String> sortedRacers;
    private PrintStream abbreviationsFileWriter;

    @BeforeEach
    void initialize() throws IOException {
        abbreviationsFileWriter = new PrintStream(new FileOutputStream(ABBREVIATIONS_PATH));
        sortedRacers = new LinkedHashMap<>();
    }
    
    @AfterEach
    void close() {
        abbreviationsFileWriter.close();       
    }

    @Test
    void separateRacersRecordsShouldThrowIOExceptionWhenFileNotFound() {
        String incorrectPath = "start";

        assertThrows(IOException.class, () -> new Records().separateRacersRecords(sortedRacers, 0, incorrectPath));
    }

    @Test
    void createRacersTimeShouldThrowMissingLineExceptionWhenNotEnoughLinesInFile() {
        abbreviationsFileWriter.println("NHR_Nico Hulkenberg_RENAULT");

        assertThrows(MissingLineException.class,
                () -> new Records().separateRacersRecords(sortedRacers, 0, ABBREVIATIONS_PATH));
    }

    @Test
    void separateRacersRecordsShouldReturnEmptyStringWhenEmptyList() throws IOException {
        int topNumber = 3;
        String expectedResult = "";
        String actualResult = new Records().separateRacersRecords(sortedRacers, topNumber, ABBREVIATIONS_PATH);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void separateRacersRecordsShouldReturnSameStringWithNumerationWhenOnlyOneInList() throws IOException {
        int topNumber = 0;

        sortedRacers.put("FAM", "1:12,657");
        abbreviationsFileWriter.println("FAM_Fernando Alonso_MCLAREN RENAULT");

        String expectedResult = " 1. Fernando Alonso      | MCLAREN RENAULT           | 1:12,657";
        String actualResult = new Records().separateRacersRecords(sortedRacers, topNumber, ABBREVIATIONS_PATH);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void separateRacersRecordsShouldReturnTwoNumeratedRacersWithoutSeparatorWhenTopNumberEqualsZero()
            throws IOException {
        int topNumber = 0;

        sortedRacers.put("SVF", "1:04,415");
        sortedRacers.put("FAM", "1:12,657");
        abbreviationsFileWriter.println("FAM_Fernando Alonso_MCLAREN RENAULT");
        abbreviationsFileWriter.println("SVF_Sebastian Vettel_FERRARI");

        String expectedResult = " 1. Sebastian Vettel     | FERRARI                   | 1:04,415\n"
                + " 2. Fernando Alonso      | MCLAREN RENAULT           | 1:12,657";
        String actualResult = new Records().separateRacersRecords(sortedRacers, topNumber, ABBREVIATIONS_PATH);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void separateRacersRecordsShouldReturnTwoNumeratedRacersWithoutSeparatorWhenTopNumberGreaterThanTwo()
            throws IOException {
        int topNumber = 3;

        sortedRacers.put("SVF", "1:04,415");
        sortedRacers.put("FAM", "1:12,657");
        abbreviationsFileWriter.println("FAM_Fernando Alonso_MCLAREN RENAULT");
        abbreviationsFileWriter.println("SVF_Sebastian Vettel_FERRARI");

        String expectedResult = " 1. Sebastian Vettel     | FERRARI                   | 1:04,415\n"
                + " 2. Fernando Alonso      | MCLAREN RENAULT           | 1:12,657";
        String actualResult = new Records().separateRacersRecords(sortedRacers, topNumber, ABBREVIATIONS_PATH);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void separateRacersRecordsShouldReturnTwoNumeratedRacersWithSeparatorBetweenThemWhenTopNumberEqualsOne()
            throws IOException {
        int topNumber = 1;

        sortedRacers.put("SVF", "1:04,415");
        sortedRacers.put("FAM", "1:12,657");
        abbreviationsFileWriter.println("FAM_Fernando Alonso_MCLAREN RENAULT");
        abbreviationsFileWriter.println("SVF_Sebastian Vettel_FERRARI");

        String expectedResult = " 1. Sebastian Vettel     | FERRARI                   | 1:04,415\n"
                + "---------------------------------------------------------------\n"
                + " 2. Fernando Alonso      | MCLAREN RENAULT           | 1:12,657";
        String actualResult = new Records().separateRacersRecords(sortedRacers, topNumber, ABBREVIATIONS_PATH);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void separateRacersRecordsShouldReturnTwoNumeratedRacersWithSeparatorAfterThemWhenTopNumberEqualsTwo()
            throws IOException {
        int topNumber = 2;

        sortedRacers.put("SVF", "1:04,415");
        sortedRacers.put("FAM", "1:12,657");
        abbreviationsFileWriter.println("FAM_Fernando Alonso_MCLAREN RENAULT");
        abbreviationsFileWriter.println("SVF_Sebastian Vettel_FERRARI");

        String expectedResult = " 1. Sebastian Vettel     | FERRARI                   | 1:04,415\n"
                + " 2. Fernando Alonso      | MCLAREN RENAULT           | 1:12,657\n"
                + "---------------------------------------------------------------";
        String actualResult = new Records().separateRacersRecords(sortedRacers, topNumber, ABBREVIATIONS_PATH);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void separateRacersRecordsShouldReturnThreeNumeratedRacersWithSeparatorAfterSecondWhenTopNumberEqualsTwo()
            throws IOException {
        int topNumber = 2;

        sortedRacers.put("SVF", "1:04,415");
        sortedRacers.put("FAM", "1:12,657");
        sortedRacers.put("NHR", "1:13,065");
        abbreviationsFileWriter.println("FAM_Fernando Alonso_MCLAREN RENAULT");
        abbreviationsFileWriter.println("SVF_Sebastian Vettel_FERRARI");
        abbreviationsFileWriter.println("NHR_Nico Hulkenberg_RENAULT");

        String expectedResult = " 1. Sebastian Vettel     | FERRARI                   | 1:04,415\n"
                + " 2. Fernando Alonso      | MCLAREN RENAULT           | 1:12,657\n"
                + "---------------------------------------------------------------\n"
                + " 3. Nico Hulkenberg      | RENAULT                   | 1:13,065";
        String actualResult = new Records().separateRacersRecords(sortedRacers, topNumber, ABBREVIATIONS_PATH);

        assertEquals(expectedResult, actualResult);
    }
}
