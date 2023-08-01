package com.sjadrian;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {

    private final String directory;
    private final String configFileName;
    private final String directoryKey;

    public Config(String configFileName, String directoryKey) {
        this.configFileName = configFileName;
        this.directoryKey = directoryKey;
        this.directory = readConfigData();
    }

    public String getDirectory() {
        return directory;
    }

    private String readConfigData() {
        Properties properties = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(configFileName))) {
            properties.load(reader);
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (properties.get(directoryKey) == null) {
            throw new IllegalStateException("Invalid Directory Key");
        }

        if (!Files.exists(Path.of(properties.get(directoryKey).toString()))) {
            throw new IllegalStateException("Directory doesn't exist");
        }

        return properties.get(directoryKey).toString();
    }
}
