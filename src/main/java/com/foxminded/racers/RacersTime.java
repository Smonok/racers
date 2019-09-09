package com.foxminded.racers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RacersTime {
    private boolean isMillisecondsNagative;
    private boolean isSecondsNagative;

    Map<String, String> createRacersTime(String startFile, String endFile) throws IOException {
        Map<String, String> racersTime = new HashMap<>();
        List<String> startLines = new ArrayList<>(Files.readAllLines(Paths.get(startFile)));
        List<String> endLines = new ArrayList<>(Files.readAllLines(Paths.get(endFile)));

        endLines.forEach(endLine -> {
            String racer = endLine.substring(0, 3);
            String[] endTime = splitTime(endLine);

            startLines.forEach(startLine -> {
                if (startLine.contains(racer)) {
                    String[] startTime = splitTime(startLine);
                    double milliseconds = computeMilliseconds(startTime[2], endTime[2]);
                    int seconds = computeSeconds(startTime[1], endTime[1]);
                    int minutes = computeMinutes(startTime[0], endTime[0]);
                    String bestTime = toTimeString(minutes, seconds, milliseconds);

                    racersTime.put(racer, bestTime);
                }
            });
        });

        return racersTime;
    }

    private String[] splitTime(String data) {
        String time = data.substring(14, data.length());

        return time.split(":");
    }

    private double computeMilliseconds(String startMilliseconds, String endMilliseconds) {
        double milliseconds = Double.valueOf(endMilliseconds) - Double.valueOf(startMilliseconds);

        isMillisecondsNagative = false;
        isSecondsNagative = false;

        if (milliseconds < 0) {
            milliseconds += 60;
            isMillisecondsNagative = true;
        }

        return milliseconds;
    }

    private int computeSeconds(String startSeconds, String endSeconds) {
        int seconds = Integer.parseInt(endSeconds) - Integer.parseInt(startSeconds);

        if (seconds < 0) {
            seconds += 60;
            isSecondsNagative = true;
        }

        if (isMillisecondsNagative) {
            seconds--;
        }

        return seconds;
    }

    private int computeMinutes(String startMinutes, String endMinutes) {
        int minutes = Integer.parseInt(endMinutes) - Integer.parseInt(startMinutes);

        if (isSecondsNagative) {
            minutes--;
        }

        return minutes;
    }

    private String toTimeString(int minutes, int seconds, double milliseconds) {
        StringBuilder time = new StringBuilder();

        if (minutes != 0) {
            time.append(minutes + ":");
        }

        time.append(seconds + ":" + String.format("%06.3f", milliseconds));

        return time.toString();
    }
}
