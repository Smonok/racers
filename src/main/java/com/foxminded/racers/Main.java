package com.foxminded.racers;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        printSortedRacers();
    }

    private static void printSortedRacers() {
        try {
            List<String> sortedRacers = new SortedRacers().createSortedRacers();
            int i = 0;
            for (String racer : sortedRacers) {
                System.out.println(String.format("%2d. ", ++i) + racer);
                if (i == 15) {
                    System.out.println(String.join("", Collections.nCopies(racer.length() + 4, "-")));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
