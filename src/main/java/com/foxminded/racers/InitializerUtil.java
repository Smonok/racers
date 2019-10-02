package com.foxminded.racers;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class InitializerUtil {
    private final static ClassLoader loader = InitializerUtil.class.getClassLoader();

    public static File initializeFile(String fileName) {
        URL url = Objects.requireNonNull(loader.getResource(fileName));

        return new File(url.getFile());
    }
}
