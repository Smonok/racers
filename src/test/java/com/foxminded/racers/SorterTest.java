package com.foxminded.racers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SorterTest {
    private PrintStream startFileWriter;
    private PrintStream endFileWriter;
    private Map<String, String> expectedResult;

    @BeforeEach
    void initialize() throws IOException {
        startFileWriter = TestInitializerUtil.initializeFileWriter(TestConstants.START_FILE_NAME_TEST);
        endFileWriter = TestInitializerUtil.initializeFileWriter(TestConstants.END_FILE_NAME_TEST);
        expectedResult = new LinkedHashMap<>();
    }

    @AfterEach
    void close() {
        startFileWriter.close();
        endFileWriter.close();
    }

    @Test
    void sortRacersShouldThrowNullPointerExceptionWhenFilesNotFound() {

        assertThrows(NullPointerException.class,
                () -> new Sorter().sortRacers(TestConstants.INCORRECT_NAME, TestConstants.INCORRECT_NAME));
    }

    @Test
    void sortRacersShouldReturnEmptyMapWhenAllFilesEmpty() throws IOException {
        Map<String, String> actualResult = new Sorter().sortRacers(TestConstants.START_FILE_NAME_TEST,
                TestConstants.END_FILE_NAME_TEST);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void sortRacersShouldReturnRacerAbbreviationAndBestTimeWhenOneRacerInFiles() throws IOException {

        expectedResult.put("FAM", "1:12,657");
        startFileWriter.println("FAM2018-05-24_12:13:04.512");
        endFileWriter.println("FAM2018-05-24_12:14:17.169");

        Map<String, String> actualResult = new Sorter().sortRacers(TestConstants.START_FILE_NAME_TEST,
                TestConstants.END_FILE_NAME_TEST);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void sortRacersShouldReturnSortedByTimeRacersRecordsWhenSeveralRacersInFiles() throws IOException {

        TestInitializerUtil.putThreeRacers(expectedResult);

        startFileWriter.println("SVF2018-05-24_12:02:58.917");
        startFileWriter.println("FAM2018-05-24_12:13:04.512");
        startFileWriter.println("NHR2018-05-24_12:02:49.914");

        endFileWriter.println("SVF2018-05-24_12:04:03.332");
        endFileWriter.println("NHR2018-05-24_12:04:02.979");
        endFileWriter.println("FAM2018-05-24_12:14:17.169");

        Map<String, String> actualResult = new Sorter().sortRacers(TestConstants.START_FILE_NAME_TEST,
                TestConstants.END_FILE_NAME_TEST);

        assertEquals(expectedResult, actualResult);
    }
}
