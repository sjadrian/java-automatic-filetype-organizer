package com.sjadrian;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Main {

    private static final String downloadPath =  getDownloadDirectoryData();

    private static String getDownloadDirectoryData() {

        Properties properties = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("config"))) {
            properties.load(reader);
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties.get("DOWNLOAD_PATH").toString();
    }

    public static void main(String[] args) throws IOException {
        // Check Download Path
        System.out.println("Download path = " + downloadPath);

        // Move each file based on its file type
        Files.list(Paths.get(downloadPath)).filter(obj-> obj.toFile().isFile()).forEach(obj-> move(obj.toFile().getName()));
    }

    private static void move(String fileName) {
        // get file extension
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

        // get the target directory path
        String targetPathString = downloadPath + "\\" + fileExtension + "\\";
        Path targetPath = Paths.get(targetPathString);

        // check if directory exist
        if (!Files.exists(targetPath)) {
            try {
                Files.createDirectory((targetPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            // move the file to its designated folder based on each type
            Path temp = Files.move(Paths.get(downloadPath + fileName), Paths.get(targetPathString  + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
