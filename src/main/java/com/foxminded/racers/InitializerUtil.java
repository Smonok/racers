package com.foxminded.racers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InitializerUtil {
    private final static ClassLoader loader = InitializerUtil.class.getClassLoader();

    public static List<String> initializeListFromFile(String fileName) throws IOException {
        File file = initializeFile(fileName);

        return new ArrayList<>(Files.readAllLines(Paths.get(file.getAbsolutePath())));
    }

    public static File initializeFile(String fileName) {
        URL url = Objects.requireNonNull(loader.getResource(fileName));

        return new File(url.getFile());
    }
}
