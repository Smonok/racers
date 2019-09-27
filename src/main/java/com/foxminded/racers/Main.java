package com.foxminded.racers;

import java.io.IOException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        RecordsService records = new RecordsService();

        System.out.println(records.separateRacersRecords(new Sorter().sortRacers(Constants.START_FILE_NAME,
                Constants.END_FILE_NAME), 15, Constants.ABBREVIATIONS_FILE_NAME));
    }
}
