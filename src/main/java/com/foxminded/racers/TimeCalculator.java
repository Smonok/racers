package com.foxminded.racers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeCalculator {
    private final SimpleDateFormat format = new SimpleDateFormat(Constants.TIME_FORMAT);

    Map<String, String> createRacersTime(String startFile, String endFile) throws IOException {
        Map<String, String> racersTime = new HashMap<>();
        List<String> startLines = InitializerUtil.initializeListFromFile(startFile);
        List<String> endLines = InitializerUtil.initializeListFromFile(endFile);

        if (startLines.size() != endLines.size()) {
            throw new MissingLineException("Not corresponding lines in files " + startFile + " and " + endFile
                    + ". The amount of lines should be equal");
        }

        endLines.forEach(endLine -> {
            String racer = endLine.substring(0, Constants.RACER_ABBREVIATION_LENGTH);
            Date endTime = parseTime(endLine);

            startLines.stream()
                    .filter(line -> line.contains(racer))
                    .forEach(startLine -> {
                        Date startTime = parseTime(startLine);
                        long bestTime = endTime.getTime() - startTime.getTime();

                        racersTime.put(racer, timeToString(bestTime));
                    });
        });

        return racersTime;
    }

    private Date parseTime(String abbreviation) {
        Date time = null;

        try {
            time = format.parse(abbreviation.substring(Constants.BEGIN_TIME_INDEX));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return time;
    }

    private String timeToString(long milliseconds) {
        long minutes = (milliseconds / 1000) / 60;
        double seconds = (milliseconds / 1000.0) % 60;

        return String.format("%d:%06.3f", minutes, seconds);
    }
}
