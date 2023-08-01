package com.sjadrian;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class FileSorterTest {

    Config config;
    String fileName;
    @BeforeEach
    void init() {
        String configFileName  = "config";
        String directoryKey = "DOWNLOAD_PATH";
        config = new Config(configFileName, directoryKey);
        fileName = "testFile.txt";
    }

    @Test
    void oldFileIsRemoved() throws IOException {

        String directoryString = config.getDirectory();
        Path originalFile = Paths.get(directoryString + fileName);
        Path movedFile = Path.of(directoryString+ "//txt//" + fileName);

        // create test file if file doesn't exist
        if (!Files.exists(originalFile)) {
            try {
                Files.createFile((originalFile));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        FileSorter fileSorter = new FileSorter(config.getDirectory());
        fileSorter.run();

        assertFalse(Files.exists(originalFile));
        Files.deleteIfExists(movedFile);
    }

    @Test
    void oldFileIsFoundInNewLocation() throws IOException {

        String directoryString = config.getDirectory();

        Path originalFile = Path.of(directoryString + fileName);
        Path movedFile = Path.of(directoryString + "//txt//" + fileName);

        // create test file if the file doesn't exist
        if (!Files.exists(originalFile)) {
            try {
                Files.createFile((originalFile));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        FileSorter fileSorter = new FileSorter(config.getDirectory());
        fileSorter.run();

        assertTrue(Files.exists(movedFile));
        Files.deleteIfExists(movedFile);
    }

    @Test
    void sameFileNameInTargetDirectoryIsNamedWithIndex1() throws IOException {

        String directoryString = config.getDirectory();

        Path originalFile = Path.of(directoryString + fileName);
        Path duplicateFile = Path.of(directoryString + "//txt//" + fileName);
        Path movedFile = Path.of(directoryString + "//txt//" + "testFile (1).txt");

        // create test file if the file doesn't exist
        if (!Files.exists(originalFile)) {
            try {
                Files.createFile((originalFile));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // create duplicate file in target directory
        if (!Files.exists(duplicateFile)) {
            try {
                Files.createFile((duplicateFile));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        FileSorter fileSorter = new FileSorter(config.getDirectory());
        fileSorter.run();

        assertTrue(Files.exists(movedFile));
        Files.deleteIfExists(movedFile);
        Files.deleteIfExists(duplicateFile);
    }

    @Test
    void sameFileNameInTargetDirectoryIsNamedWithIndex2() throws IOException {

        String directoryString = config.getDirectory();

        Path originalFile = Path.of(directoryString + fileName);
        Path duplicateFile1 = Path.of(directoryString + "//txt//" + fileName);
        Path duplicateFile2 = Path.of(directoryString + "//txt//" + "testFile (1).txt");
        Path movedFile = Path.of(directoryString + "//txt//" + "testFile (2).txt");

        // create test file if the file doesn't exist
        if (!Files.exists(originalFile)) {
            try {
                Files.createFile((originalFile));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // create 1st duplicate file in target directory
        if (!Files.exists(duplicateFile1)) {
            try {
                Files.createFile((duplicateFile1));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // create 2nd duplicate file in target directory
        if (!Files.exists(duplicateFile2)) {
            try {
                Files.createFile(duplicateFile2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        FileSorter fileSorter = new FileSorter(config.getDirectory());
        fileSorter.run();

        assertTrue(Files.exists(movedFile));
        Files.deleteIfExists(duplicateFile1);
        Files.deleteIfExists(duplicateFile2);
        Files.deleteIfExists(movedFile);
        Files.deleteIfExists(originalFile);
    }
}