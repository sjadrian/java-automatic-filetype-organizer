package com.sjadrian;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws IOException {

//        // test 1
//        Path configPath = Path.of("configx");
//        System.out.println(configPath);
//        System.out.println(Files.exists(configPath));
//        // test 2
//        Properties properties = new Properties();
//        try (BufferedReader reader = Files.newBufferedReader(Paths.get("config"))) {
//            properties.load(reader);
//        }  catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        String downloadPathString = properties.get("DOWNLOAD_PATH").toString();
//        Path downloadPath = Path.of(downloadPathString);
//        System.out.println(Files.exists(downloadPath));
//
//        // test 3
//        String downloadPathString2 = properties.get("DOWNLOAD_PATHX").toString();
//        System.out.println(downloadPathString2);

        Config config = new Config();
        FileSorter fileSorter = new FileSorter(config.getDirectoryConfig());
        fileSorter.run();
    }
}
