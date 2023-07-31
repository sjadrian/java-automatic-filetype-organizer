package com.sjadrian;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSorter {

    private final String directoryPath;
    public FileSorter(String directoryPath) {
       this.directoryPath = directoryPath;
    }

    public void run() throws IOException {
        // Check Download Path
        System.out.println("Download path = " + directoryPath);

        // Move each file based on its file type
        Files.list(Paths.get(directoryPath)).filter(obj-> obj.toFile().isFile()).forEach(obj-> move(obj.toFile().getName()));
    }

    private void move(String fileName) {
        // get file extension
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

        // get the target directory path
        String targetPathString = directoryPath + "\\" + fileExtension + "\\";
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
            Path temp = Files.move(Paths.get(directoryPath + fileName), Paths.get(targetPathString  + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
