package com.foxminded.racers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;

public class TestInitializerUtil {

    public static void putThreeRacers(Map<String, String> racers) {

        racers.put("SVF", "1:04,415");
        racers.put("FAM", "1:12,657");
        racers.put("NHR", "1:13,065");
    }

    public static PrintStream initializeFileWriter(String fileName) throws IOException {
        File file = InitializerUtil.initializeFile(fileName);

        return new PrintStream(new FileOutputStream(file.getAbsolutePath()));
    }
}
