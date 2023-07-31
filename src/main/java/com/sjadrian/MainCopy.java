package com.sjadrian;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainCopy {

    private static final String downloadPath = System.getProperty("user.home") + "\\Downloads\\";

    public static void main(String[] args) throws IOException {

        // go to the target folder
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        System.out.println("Home Directory = " + System.getProperty("user.home"));

        // download path
        System.out.println("Download path = " + downloadPath);

        File folder = new File(downloadPath);
        File[] listOfFiles = folder.listFiles();

        // read all files
        for (File file: listOfFiles) {
            if (file.isFile()) {
                // get file extension
                String fileName = file.getName();
                String fileExtension = fileName.substring(fileName.lastIndexOf("."));
                System.out.println("File " + file.getName());
                System.out.println("File extension: " + fileExtension);

                // for each file move the file to the designated location
                move(fileName);
            } else if (file.isDirectory()) {
                System.out.println("Directory " + file.getName());
            }
        }
    }

//    private static void processMp4(String fileName) throws IOException {
//        String targetFilePath = downloadPath + "\\mp4\\";
//        move(targetFilePath, fileName);
//    }
//
//    private static void processPDF(String fileName) throws IOException {
//        String targetFilePath = downloadPath + "\\pdf\\";
//        move(targetFilePath, fileName);
//    }

    private static void move(String fileName) {
//        try {
//            Path temp = Files.move(Paths.get(initialFilePath + fileName), Paths.get(targetFilePath + fileName));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        // get file extension
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

        // check if folder exist
        String targetPath = downloadPath + "\\" + fileExtension + "\\";
//        Path tempDirectory = Paths.get(downloadPath + "\\" + fileExtension + "\\");
        File tempDir = new File(targetPath);

        // create directory if not exist
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }

        try {
            // move the file to its designated folder based on each type
            Path temp = Files.move(Paths.get(downloadPath + fileName), Paths.get(targetPath + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
