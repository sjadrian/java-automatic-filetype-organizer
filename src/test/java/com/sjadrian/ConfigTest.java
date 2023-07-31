package com.sjadrian;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {

    @Test
    void configFileShouldExist() {
        Path configPath = Path.of("config");
        System.out.println(configPath);
        System.out.println(Files.exists(configPath));
        assertTrue(Files.exists(configPath));
    }

    @Test
    void configHasDOWNLOAD_PATHAsKey() {
        Properties properties = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("config"))) {
            properties.load(reader);
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
        String downloadPathString = properties.get("DOWNLOAD_PATH").toString();
        assertNotNull(downloadPathString);
    }

    @Test
    void validDirectoryProvidedInConfigFile() {
        Properties properties = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("config"))) {
            properties.load(reader);
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
        String downloadPathString = properties.get("DOWNLOAD_PATH").toString();
        Path downloadPath = Path.of(downloadPathString);
        assertTrue(Files.exists(downloadPath));
    }
}