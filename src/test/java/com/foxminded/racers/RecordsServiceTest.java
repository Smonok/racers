package com.foxminded.racers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecordsServiceTest {
    private Map<String, String> sortedRacers;
    private PrintStream abbreviationsFileWriter;
    private final ClassLoader loader = TimeCalculator.class.getClassLoader();

    @BeforeEach
    void initialize() throws IOException {
        File abbreviationsTest = new File(loader.getResource(Constants.ABBREVIATIONS_TEST_FILE_NAME)
                .getFile());
        abbreviationsFileWriter = new PrintStream(new FileOutputStream(abbreviationsTest.getAbsolutePath()));
        sortedRacers = new LinkedHashMap<>();
    }

    @AfterEach
    void close() {
        abbreviationsFileWriter.close();
    }

    @Test
    void separateRacersRecordsShouldThrowNullPointerExceptionWhenFileNotFound() {

        assertThrows(NullPointerException.class,
                () -> new RecordsService().separateRacersRecords(sortedRacers, 0, Constants.INCORRECT_NAME));
    }

    @Test
    void createRacersTimeShouldThrowMissingLineExceptionWhenNotEnoughLinesInFile() {
        abbreviationsFileWriter.println("NHR_Nico Hulkenberg_RENAULT");

        assertThrows(MissingLineException.class,
                () -> new RecordsService()
                        .separateRacersRecords(sortedRacers, 0, Constants.ABBREVIATIONS_TEST_FILE_NAME));
    }

    @Test
    void separateRacersRecordsShouldReturnEmptyStringWhenEmptyList() throws IOException {
        int topNumber = 3;
        String expectedResult = "";
        String actualResult = new RecordsService()
                .separateRacersRecords(sortedRacers, topNumber, Constants.ABBREVIATIONS_TEST_FILE_NAME);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void separateRacersRecordsShouldReturnSameStringWithNumerationWhenOnlyOneInList() throws IOException {
        int topNumber = 0;

        sortedRacers.put("FAM", "1:12,657");
        abbreviationsFileWriter.println("FAM_Fernando Alonso_MCLAREN RENAULT");

        String expectedResult = " 1. Fernando Alonso      | MCLAREN RENAULT           | 1:12,657";
        String actualResult = new RecordsService()
                .separateRacersRecords(sortedRacers, topNumber, Constants.ABBREVIATIONS_TEST_FILE_NAME);

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
        String actualResult = new RecordsService()
                .separateRacersRecords(sortedRacers, topNumber, Constants.ABBREVIATIONS_TEST_FILE_NAME);

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
        String actualResult = new RecordsService()
                .separateRacersRecords(sortedRacers, topNumber, Constants.ABBREVIATIONS_TEST_FILE_NAME);

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
        String actualResult = new RecordsService()
                .separateRacersRecords(sortedRacers, topNumber, Constants.ABBREVIATIONS_TEST_FILE_NAME);

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
        String actualResult = new RecordsService()
                .separateRacersRecords(sortedRacers, topNumber, Constants.ABBREVIATIONS_TEST_FILE_NAME);

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
        String actualResult = new RecordsService()
                .separateRacersRecords(sortedRacers, topNumber, Constants.ABBREVIATIONS_TEST_FILE_NAME);

        assertEquals(expectedResult, actualResult);
    }
}
