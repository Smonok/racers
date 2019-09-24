package com.foxminded.racers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class InitializerUtil {

    public static PrintStream initializeFileWriter(ClassLoader loader, String fileName) throws IOException {
        File file = initializeFile(loader, fileName);

        return new PrintStream(new FileOutputStream(file.getAbsolutePath()));
    }

    public static List<String> initializeListFromFile(ClassLoader loader, String fileName) throws IOException {
        File file = initializeFile(loader, fileName);

        return new ArrayList<>(Files.readAllLines(Paths.get(file.getAbsolutePath())));
    }

    private static File initializeFile(ClassLoader loader, String fileName) {
        return Objects.requireNonNull(new File(loader.getResource(fileName)
                .getFile()));
    }

    public static void putThreeRacers(Map<String, String> racers) {

        racers.put("SVF", "1:04,415");
        racers.put("FAM", "1:12,657");
        racers.put("NHR", "1:13,065");
    }
}
