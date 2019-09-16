package com.foxminded.racers;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        NumberingSeparator racers = new NumberingSeparator();

        try {
            System.out.println(racers.separateNumeratedRacers(new Sorter().createSortedRacers(Constants.START_PATH,
                    Constants.END_PATH, Constants.ABBREVIATIONS_PATH), 15));
        } catch (MissingLineException e) {
            e.printStackTrace();
        }
    }
}
