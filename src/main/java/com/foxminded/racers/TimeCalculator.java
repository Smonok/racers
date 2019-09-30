package com.foxminded.racers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeCalculator {

    Map<String, String> createRacersTime(String startFile, String endFile) throws IOException {
        Map<String, String> racersTime = new HashMap<>();

        File start = InitializerUtil.initializeFile(startFile);
        File end = InitializerUtil.initializeFile(endFile);
        List<String> startLines = new ArrayList<>(Files.readAllLines(Paths.get(start.getAbsolutePath())));
        List<String> endLines = new ArrayList<>(Files.readAllLines(Paths.get(end.getAbsolutePath())));

        if (startLines.size() != endLines.size()) {
            throw new MissingLineException("Not corresponding lines in files " + startFile + " and " + endFile
                    + ". The amount of lines should be equal");
        }

        endLines.forEach(endLine -> {
            String racer = endLine.substring(0, Constants.RACER_ABBREVIATION_LENGTH);
            LocalTime endTime = parseTime(endLine);

            startLines.stream()
                    .filter(line -> line.contains(racer))
                    .forEach(startLine -> {
                        LocalTime startTime = parseTime(startLine);
                        long bestTime = ChronoUnit.MILLIS.between(startTime, endTime);

                        racersTime.put(racer, timeToString(bestTime));
                    });
        });

        return racersTime;
    }

    private LocalTime parseTime(String abbreviation) {

        return LocalTime.parse(abbreviation.substring(Constants.BEGIN_TIME_INDEX));
    }

    private String timeToString(long milliseconds) {
        long minutes = (milliseconds / 1000) / 60;
        double seconds = (milliseconds / 1000.0) % 60;

        return String.format("%d:%06.3f", minutes, seconds);
    }
}
