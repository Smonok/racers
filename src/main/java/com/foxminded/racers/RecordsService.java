package com.foxminded.racers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class RecordsService {
    private List<String> racersRecords = new ArrayList<>();
    private int count = 0;

    public String separateRacersRecords(Map<String, String> sortedRacers, int topNumber, String abbreviationsFile)
            throws IOException {
        StringJoiner numeratedRacers = new StringJoiner("\n");

        fillRacersRecords(sortedRacers, abbreviationsFile);
        racersRecords.forEach(record -> {
            numeratedRacers.add(record);
            if (racersRecords.indexOf(record) + 1 == topNumber) {
                numeratedRacers.add(createSeparator(record.length()));
            }
        });

        return numeratedRacers.toString();
    }

    private String createSeparator(int length) {
        return String.join("", Collections.nCopies(length, "-"));
    }

    private void fillRacersRecords(Map<String, String> sortedRacers, String abbreviationsFile) throws IOException {
        List<String> abbreviations = new ArrayList<>(Files.readAllLines(Paths.get(abbreviationsFile)));

        if (abbreviations.size() != sortedRacers.size()) {
            throw new MissingLineException("Not enough lines in abbreviations.txt file");
        }

        sortedRacers.keySet()
                .forEach(name -> abbreviations.stream()
                        .filter(abbreviation -> abbreviation.contains(name))
                        .forEach(
                                abbreviation -> racersRecords.add(createRecord(abbreviation, sortedRacers.get(name)))));
    }

    private String createRecord(String abbreviation, String time) {
        String number = String.format("%2d. ", ++count);
        String parsedFullName = abbreviation.substring(4, getSecondUnderscoreIndex(abbreviation));
        String parsedCarBrand = abbreviation.substring(getSecondUnderscoreIndex(abbreviation) + 1);
        String name = String.format("%-" + 20 + "s | ", parsedFullName);
        String car = String.format("%-" + 25 + "s | ", parsedCarBrand);

        return number + name + car + time;
    }

    private int getSecondUnderscoreIndex(String abbreviation) {
        return abbreviation.indexOf("_", abbreviation.indexOf("_") + 1);
    }
}
