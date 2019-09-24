package com.foxminded.racers;

import static org.junit.jupiter.api.Assertions.*;

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
    private final ClassLoader loader = RecordsServiceTest.class.getClassLoader();

    @BeforeEach
    void initialize() throws IOException {
        abbreviationsFileWriter = InitializerUtil.initializeFileWriter(loader, Constants.ABBREVIATIONS_FILE_NAME_TEST);
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
                        .separateRacersRecords(sortedRacers, 0, Constants.ABBREVIATIONS_FILE_NAME_TEST));
    }

    @Test
    void separateRacersRecordsShouldReturnEmptyStringWhenEmptyList() throws IOException {
        int topNumber = 3;
        String expectedResult = "";
        String actualResult = new RecordsService()
                .separateRacersRecords(sortedRacers, topNumber, Constants.ABBREVIATIONS_FILE_NAME_TEST);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void separateRacersRecordsShouldReturnSameStringWithNumerationWhenOnlyOneInList() throws IOException {
        int topNumber = 0;

        sortedRacers.put("FAM", "1:12,657");
        abbreviationsFileWriter.println("FAM_Fernando Alonso_MCLAREN RENAULT");

        String expectedResult = " 1. Fernando Alonso      | MCLAREN RENAULT           | 1:12,657";
        String actualResult = new RecordsService()
                .separateRacersRecords(sortedRacers, topNumber, Constants.ABBREVIATIONS_FILE_NAME_TEST);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void separateRacersRecordsShouldReturnTwoNumeratedRacersWithoutSeparatorWhenTopNumberEqualsZeroOrGreaterThanTwo()
            throws IOException {
        int topNumber = 0;

        sortedRacers.put("SVF", "1:04,415");
        sortedRacers.put("FAM", "1:12,657");
        abbreviationsFileWriter.println("FAM_Fernando Alonso_MCLAREN RENAULT");
        abbreviationsFileWriter.println("SVF_Sebastian Vettel_FERRARI");

        String expectedResult = " 1. Sebastian Vettel     | FERRARI                   | 1:04,415\n"
                + " 2. Fernando Alonso      | MCLAREN RENAULT           | 1:12,657";
        String actualResultWhenZero = new RecordsService()
                .separateRacersRecords(sortedRacers, topNumber, Constants.ABBREVIATIONS_FILE_NAME_TEST);

        topNumber = 3;
        String actualResultWhenThree = new RecordsService()
                .separateRacersRecords(sortedRacers, topNumber, Constants.ABBREVIATIONS_FILE_NAME_TEST);

        assertEquals(expectedResult, actualResultWhenZero, actualResultWhenThree);
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
                .separateRacersRecords(sortedRacers, topNumber, Constants.ABBREVIATIONS_FILE_NAME_TEST);

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
                .separateRacersRecords(sortedRacers, topNumber, Constants.ABBREVIATIONS_FILE_NAME_TEST);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void separateRacersRecordsShouldReturnThreeNumeratedRacersWithSeparatorAfterSecondWhenTopNumberEqualsTwo()
            throws IOException {
        int topNumber = 2;

        InitializerUtil.putThreeRacers(sortedRacers);

        abbreviationsFileWriter.println("FAM_Fernando Alonso_MCLAREN RENAULT");
        abbreviationsFileWriter.println("SVF_Sebastian Vettel_FERRARI");
        abbreviationsFileWriter.println("NHR_Nico Hulkenberg_RENAULT");

        String expectedResult = " 1. Sebastian Vettel     | FERRARI                   | 1:04,415\n"
                + " 2. Fernando Alonso      | MCLAREN RENAULT           | 1:12,657\n"
                + "---------------------------------------------------------------\n"
                + " 3. Nico Hulkenberg      | RENAULT                   | 1:13,065";
        String actualResult = new RecordsService()
                .separateRacersRecords(sortedRacers, topNumber, Constants.ABBREVIATIONS_FILE_NAME_TEST);

        assertEquals(expectedResult, actualResult);
    }
}
