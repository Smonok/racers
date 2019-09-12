package com.foxminded.racers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class PrintRacersTest {
    private final PrintRacers print = new PrintRacers();

    @Test
    void printSortedRacersShouldReturnEmptyStringWhenEmptyList() {
        List<String> sortedRacers = new ArrayList<>();
        int topNumber = 3;
        String expectedResult = "";
        String actualResult = print.printSortedRacers(sortedRacers, topNumber);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void printSortedRacersShouldReturnSameStringWithNumerationWhenOnlyOneInList() {
        List<String> sortedRacers = new ArrayList<>();
        int topNumber = 0;
        sortedRacers.add("Fernando Alonso      | MCLAREN RENAULT           | 1:12,657");

        String expectedResult = " 1. Fernando Alonso      | MCLAREN RENAULT           | 1:12,657";
        String actualResult = print.printSortedRacers(sortedRacers, topNumber);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void printSortedRacersShouldReturnTwoNumeratedRacersWithoutSeparatorWhenTopNumberEqualsZero() {
        List<String> sortedRacers = new ArrayList<>();
        int topNumber = 0;

        sortedRacers.add("Sebastian Vettel     | FERRARI                   | 1:04,415");
        sortedRacers.add("Fernando Alonso      | MCLAREN RENAULT           | 1:12,657");

        StringBuilder expectedResult = new StringBuilder(
                " 1. Sebastian Vettel     | FERRARI                   | 1:04,415\n")
                        .append(" 2. Fernando Alonso      | MCLAREN RENAULT           | 1:12,657");
        String actualResult = print.printSortedRacers(sortedRacers, topNumber);

        assertEquals(expectedResult.toString(), actualResult);
    }

    @Test
    void printSortedRacersShouldReturnTwoNumeratedRacersWithoutSeparatorWhenTopNumberGreaterThanTwo() {
        List<String> sortedRacers = new ArrayList<>();
        int topNumber = 3;

        sortedRacers.add("Sebastian Vettel     | FERRARI                   | 1:04,415");
        sortedRacers.add("Fernando Alonso      | MCLAREN RENAULT           | 1:12,657");

        StringBuilder expectedResult = new StringBuilder(
                " 1. Sebastian Vettel     | FERRARI                   | 1:04,415\n")
                        .append(" 2. Fernando Alonso      | MCLAREN RENAULT           | 1:12,657");
        String actualResult = print.printSortedRacers(sortedRacers, topNumber);

        assertEquals(expectedResult.toString(), actualResult);
    }

    @Test
    void printSortedRacersShouldReturnTwoNumeratedRacersWithSeparatorBetweenThemWhenTopNumberEqualsOne() {
        List<String> sortedRacers = new ArrayList<>();
        int topNumber = 1;

        sortedRacers.add("Sebastian Vettel     | FERRARI                   | 1:04,415");
        sortedRacers.add("Fernando Alonso      | MCLAREN RENAULT           | 1:12,657");

        StringBuilder expectedResult = new StringBuilder(
                " 1. Sebastian Vettel     | FERRARI                   | 1:04,415\n")
                        .append("---------------------------------------------------------------\n")
                        .append(" 2. Fernando Alonso      | MCLAREN RENAULT           | 1:12,657");
        String actualResult = print.printSortedRacers(sortedRacers, topNumber);

        assertEquals(expectedResult.toString(), actualResult);
    }

    @Test
    void printSortedRacersShouldReturnTwoNumeratedRacersWithSeparatorAfterThemWhenTopNumberEqualsTwo() {
        List<String> sortedRacers = new ArrayList<>();
        int topNumber = 2;

        sortedRacers.add("Sebastian Vettel     | FERRARI                   | 1:04,415");
        sortedRacers.add("Fernando Alonso      | MCLAREN RENAULT           | 1:12,657");

        StringBuilder expectedResult = new StringBuilder(
                " 1. Sebastian Vettel     | FERRARI                   | 1:04,415\n")
                        .append(" 2. Fernando Alonso      | MCLAREN RENAULT           | 1:12,657\n")
                        .append("---------------------------------------------------------------\n");
        String actualResult = print.printSortedRacers(sortedRacers, topNumber);

        assertEquals(expectedResult.toString(), actualResult);
    }

    @Test
    void printSortedRacersShouldReturnThreeNumeratedRacersWithSeparatorAfterSecondWhenTopNumberEqualsTwo() {
        List<String> sortedRacers = new ArrayList<>();
        int topNumber = 2;

        sortedRacers.add("Sebastian Vettel     | FERRARI                   | 1:04,415");
        sortedRacers.add("Fernando Alonso      | MCLAREN RENAULT           | 1:12,657");
        sortedRacers.add("Nico Hulkenberg      | RENAULT                   | 1:13,065");

        StringBuilder expectedResult = new StringBuilder(
                " 1. Sebastian Vettel     | FERRARI                   | 1:04,415\n")
                        .append(" 2. Fernando Alonso      | MCLAREN RENAULT           | 1:12,657\n")
                        .append("---------------------------------------------------------------\n")
                        .append(" 3. Nico Hulkenberg      | RENAULT                   | 1:13,065");
        String actualResult = print.printSortedRacers(sortedRacers, topNumber);

        assertEquals(expectedResult.toString(), actualResult);
    }

}
