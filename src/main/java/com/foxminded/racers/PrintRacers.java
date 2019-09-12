package com.foxminded.racers;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

public class PrintRacers {

    public String printSortedRacers(List<String> sortedRacers, int topNumber) {
        StringBuilder numeratedRacers = new StringBuilder(addNumberingToTopRacers(sortedRacers, topNumber));

        if (topNumber > 0 && topNumber <= sortedRacers.size()) {
            numeratedRacers.append(createSeparator(sortedRacers.get(0).length()));
        }

        numeratedRacers.append(addNumberingToLastRacers(sortedRacers, topNumber));
        System.out.println(numeratedRacers);

        return numeratedRacers.toString();
    }

    private String addNumberingToTopRacers(List<String> sortedRacers, int topNumber) {
        StringJoiner topRacers = new StringJoiner("\n");

        sortedRacers.stream().filter(racer -> sortedRacers.indexOf(racer) < topNumber).forEach(racer -> {
            topRacers.add(String.format("%2d. %s", sortedRacers.indexOf(racer) + 1, racer));
        });

        return topRacers.toString();
    }

    private String createSeparator(int length) {
        return "\n" + String.join("", Collections.nCopies(length + 4, "-")) + "\n";
    }

    private String addNumberingToLastRacers(List<String> sortedRacers, int topNumber) {
        StringJoiner lastRacers = new StringJoiner("\n");

        sortedRacers.stream().filter(racer -> sortedRacers.indexOf(racer) >= topNumber).forEach(racer -> {
            lastRacers.add(String.format("%2d. %s", sortedRacers.indexOf(racer) + 1, racer));
        });

        return lastRacers.toString();
    }
}
