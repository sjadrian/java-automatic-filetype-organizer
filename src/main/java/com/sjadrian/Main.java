package com.sjadrian;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws IOException {
        Config config = new Config();
        FileSorter fileSorter = new FileSorter(config.getDirectoryConfig());
        fileSorter.run();
    }
}
