package com.foxminded.racers;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Sorter {
    public Map<String, String> sortRacers(String startFile, String endFile) throws IOException {
        Map<String, String> racersTime = new TimeCalculator().createRacersTime(startFile, endFile);

        return racersTime.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));
    }
}
