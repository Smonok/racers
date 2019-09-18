package com.foxminded.racers;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Records records = new Records();

        System.out.println(
                records.separateRacersRecords(new Sorter().sortRacers(Constants.START_PATH, Constants.END_PATH),
                        15,
                        Constants.ABBREVIATIONS_PATH));
    }
}
