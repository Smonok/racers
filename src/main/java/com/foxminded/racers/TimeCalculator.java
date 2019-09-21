package com.foxminded.racers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeCalculator {
    private boolean isMillisecondsNegative;
    private boolean isSecondsNegative;

    Map<String, String> createRacersTime(String startFile, String endFile) throws IOException {
        Map<String, String> racersTime = new HashMap<>();
        List<String> startLines = new ArrayList<>(Files.readAllLines(Paths.get(startFile)));
        List<String> endLines = new ArrayList<>(Files.readAllLines(Paths.get(endFile)));
        int racerAbbreviationLength = 3;

        if (startLines.size() != endLines.size()) {
            String fileName = startLines.size() < endLines.size() ? "start.log" : "end.log";
            throw new MissingLineException("Not enough lines in " + fileName + " file");
        }
        
        endLines.forEach(endLine -> {            
            String racer = endLine.substring(0, racerAbbreviationLength);
            String[] endTime = splitTime(endLine);

            startLines.stream()
                    .filter(line -> line.contains(racer))
                    .forEach(startLine -> {
                        String[] startTime = splitTime(startLine);
                        double milliseconds = computeMilliseconds(startTime[2], endTime[2]);
                        int seconds = computeSeconds(startTime[1], endTime[1]);
                        int minutes = computeMinutes(startTime[0], endTime[0]);
                        String bestTime = timeToString(minutes, seconds, milliseconds);

                        racersTime.put(racer, bestTime);
                    });
        });

        return racersTime;
    }

    private String[] splitTime(String abbreviation) {
        int firstTimeDigitIndex = 14;
        String time = abbreviation.substring(firstTimeDigitIndex);

        return time.split(":");
    }

    private double computeMilliseconds(String startMilliseconds, String endMilliseconds) {
        double milliseconds = Double.valueOf(endMilliseconds) - Double.valueOf(startMilliseconds);

        isMillisecondsNegative = false;
        if (milliseconds < 0) {
            milliseconds += 60;
            isMillisecondsNegative = true;
        }

        return milliseconds;
    }

    private int computeSeconds(String startSeconds, String endSeconds) {
        int seconds = Integer.parseInt(endSeconds) - Integer.parseInt(startSeconds);

        isSecondsNegative = false;
        if (seconds < 0) {
            seconds += 60;
            isSecondsNegative = true;
        }

        if (isMillisecondsNegative) {
            seconds--;
        }

        return seconds;
    }

    private int computeMinutes(String startMinutes, String endMinutes) {
        int minutes = Integer.parseInt(endMinutes) - Integer.parseInt(startMinutes);

        if (isSecondsNegative) {
            minutes--;
        }

        return minutes;
    }

    private String timeToString(int minutes, int seconds, double milliseconds) {
        StringBuilder time = new StringBuilder();

        if (minutes != 0) {
            time.append(minutes)
                    .append(":");
        }

        time.append(seconds)
                .append(":")
                .append(String.format("%06.3f", milliseconds));

        return time.toString();
    }
}
