package com.sjadrian;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {

    private final String directoryConfig;

    public Config() {
        this.directoryConfig = readConfigData();
    }

    public String getDirectoryConfig() {
        return directoryConfig;
    }

    private static String readConfigData() {
        Properties properties = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("config"))) {
            properties.load(reader);
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties.get("DOWNLOAD_PATH").toString();
    }
}
