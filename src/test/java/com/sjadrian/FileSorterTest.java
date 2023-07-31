package com.sjadrian;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class FileSorterTest {

    @Test
    void oldFileIsRemoved() throws IOException {

        Config config = new Config();

        String configPathString = config.getDirectoryConfig();
        String fileName = "testFile.txt";
        Path configPath = Paths.get(configPathString + fileName);
        Path targetPath = Path.of(configPathString + "//txt//" + fileName);

        // create test file if doesn't exist
        if (!Files.exists(configPath)) {
            try {
                Files.createFile((configPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        FileSorter fileSorter = new FileSorter(config.getDirectoryConfig());
        fileSorter.run();

        assertFalse(Files.exists(configPath));
        Files.deleteIfExists(targetPath);
    }

    @Test
    void oldFileIsFoundInNewLocation() throws IOException {

        Config config = new Config();

        String configPathString = config.getDirectoryConfig();
        String fileName = "testFile.txt";
        Path configPath = Path.of(configPathString + fileName);
        Path targetPath = Path.of(configPathString + "//txt//" + fileName);

        // create test file if the file doesn't exist
        if (!Files.exists(configPath)) {
            try {
                Files.createFile((configPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        FileSorter fileSorter = new FileSorter(config.getDirectoryConfig());
        fileSorter.run();

        assertTrue(Files.exists(targetPath));
        Files.deleteIfExists(targetPath);
    }

    @Test
    void sameFileNameInTargetDirectoryIsProperlyNamed() throws IOException {

        Config config = new Config();

        String configPathString = config.getDirectoryConfig();
        String fileName = "testFile.txt";
        Path configPath = Path.of(configPathString + fileName);
        Path targetPath = Path.of(configPathString + "//txt//" + fileName);
        Path newNamePath = Path.of(configPathString + "//txt//" + "testFile (1).txt");

        // create test file if the file doesn't exist
        if (!Files.exists(configPath)) {
            try {
                Files.createFile((configPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // create file with the same name in target directory
        if (!Files.exists(targetPath)) {
            try {
                Files.createFile((targetPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        FileSorter fileSorter = new FileSorter(config.getDirectoryConfig());
        fileSorter.run();

        assertTrue(Files.exists(newNamePath));
        Files.deleteIfExists(newNamePath);
    }
}