package com.foxminded.racers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NumberingSeparatorTest {
    private final NumberingSeparator racers = new NumberingSeparator();
    private List<String> sortedRacers;

    @BeforeEach
    void initialize() {
        sortedRacers = new ArrayList<>();
    }

    @Test
    void separateRacersShouldReturnEmptyStringWhenEmptyList() {
        int topNumber = 3;
        String expectedResult = "";
        String actualResult = racers.separateNumeratedRacers(sortedRacers, topNumber);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void separateRacersShouldReturnSameStringWithNumerationWhenOnlyOneInList() {
        int topNumber = 0;

        sortedRacers.add("Fernando Alonso      | MCLAREN RENAULT           | 1:12,657");

        String expectedResult = " 1. Fernando Alonso      | MCLAREN RENAULT           | 1:12,657";
        String actualResult = racers.separateNumeratedRacers(sortedRacers, topNumber);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void separateRacersShouldReturnTwoNumeratedRacersWithoutSeparatorWhenTopNumberEqualsZero() {
        int topNumber = 0;

        sortedRacers.add("Sebastian Vettel     | FERRARI                   | 1:04,415");
        sortedRacers.add("Fernando Alonso      | MCLAREN RENAULT           | 1:12,657");

        String expectedResult = " 1. Sebastian Vettel     | FERRARI                   | 1:04,415\n"
                + " 2. Fernando Alonso      | MCLAREN RENAULT           | 1:12,657";
        String actualResult = racers.separateNumeratedRacers(sortedRacers, topNumber);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void separateRacersShouldReturnTwoNumeratedRacersWithoutSeparatorWhenTopNumberGreaterThanTwo() {
        int topNumber = 3;

        sortedRacers.add("Sebastian Vettel     | FERRARI                   | 1:04,415");
        sortedRacers.add("Fernando Alonso      | MCLAREN RENAULT           | 1:12,657");

        String expectedResult = " 1. Sebastian Vettel     | FERRARI                   | 1:04,415\n"
                + " 2. Fernando Alonso      | MCLAREN RENAULT           | 1:12,657";
        String actualResult = racers.separateNumeratedRacers(sortedRacers, topNumber);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void separateRacersShouldReturnTwoNumeratedRacersWithSeparatorBetweenThemWhenTopNumberEqualsOne() {
        int topNumber = 1;

        sortedRacers.add("Sebastian Vettel     | FERRARI                   | 1:04,415");
        sortedRacers.add("Fernando Alonso      | MCLAREN RENAULT           | 1:12,657");

        String expectedResult = " 1. Sebastian Vettel     | FERRARI                   | 1:04,415\n"
                + "---------------------------------------------------------------\n"
                + " 2. Fernando Alonso      | MCLAREN RENAULT           | 1:12,657";
        String actualResult = racers.separateNumeratedRacers(sortedRacers, topNumber);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void separateRacersShouldReturnTwoNumeratedRacersWithSeparatorAfterThemWhenTopNumberEqualsTwo() {
        int topNumber = 2;

        sortedRacers.add("Sebastian Vettel     | FERRARI                   | 1:04,415");
        sortedRacers.add("Fernando Alonso      | MCLAREN RENAULT           | 1:12,657");

        String expectedResult = " 1. Sebastian Vettel     | FERRARI                   | 1:04,415\n"
                + " 2. Fernando Alonso      | MCLAREN RENAULT           | 1:12,657\n"
                + "---------------------------------------------------------------\n";
        String actualResult = racers.separateNumeratedRacers(sortedRacers, topNumber);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void separateRacersShouldReturnThreeNumeratedRacersWithSeparatorAfterSecondWhenTopNumberEqualsTwo() {
        int topNumber = 2;

        sortedRacers.add("Sebastian Vettel     | FERRARI                   | 1:04,415");
        sortedRacers.add("Fernando Alonso      | MCLAREN RENAULT           | 1:12,657");
        sortedRacers.add("Nico Hulkenberg      | RENAULT                   | 1:13,065");

        String expectedResult = " 1. Sebastian Vettel     | FERRARI                   | 1:04,415\n"
                + " 2. Fernando Alonso      | MCLAREN RENAULT           | 1:12,657\n"
                + "---------------------------------------------------------------\n"
                + " 3. Nico Hulkenberg      | RENAULT                   | 1:13,065";
        String actualResult = racers.separateNumeratedRacers(sortedRacers, topNumber);

        assertEquals(expectedResult, actualResult);
    }

}
