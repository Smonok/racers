package com.foxminded.racers;

import java.io.IOException;

public class Main {

    private final static String startPath = "src/main/resources/start.log";
    private final static String endPath = "src/main/resources/end.log";
    private final static String abbreviationsPath = "src/main/resources/abbreviations.txt";

    public static void main(String[] args) throws IOException {
        PrintRacers print = new PrintRacers();

        try {
            print.printSortedRacers(new SortedRacers().createSortedRacers(startPath, endPath, abbreviationsPath), 15);
        } catch (MissingAbbreviationException e) {
            e.printStackTrace();
        }
    }
}
