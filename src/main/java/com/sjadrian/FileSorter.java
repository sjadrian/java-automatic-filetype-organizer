package com.sjadrian;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileSorter {

    private final String directoryPath;
    public FileSorter(String directoryPath) {
       this.directoryPath = directoryPath;
    }

    public void run() throws IOException {
        // Check Download Path
        System.out.println("Download path = " + directoryPath);

        // Move each file based on its file type
        Files.list(Path.of(directoryPath)).filter(obj-> obj.toFile().isFile()).forEach(obj-> {
            try {
                move(obj.toFile().getName());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void move(String fileName) throws IOException {
        // get file extension
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));

        // get the target directory path
        String targetPathString = directoryPath + "\\" + fileExtension.substring(1) + "\\";
        Path targetPath = Path.of(targetPathString);

        // check if directory exist
        if (!Files.exists(targetPath)) {
            try {
                Files.createDirectory((targetPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // rename the file if there's the same filename in target directory
        String targetFileName = Files.exists(Path.of(targetPathString + fileName))
                ? renameFile(fileName, fileExtension, targetPathString) : fileName;

        try {
            // move the file to its designated folder based on each type
            Path temp = Files.move(Path.of(directoryPath + fileName), Path.of(targetPathString + targetFileName));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String renameFile(String fileName, String fileExtension, String targetPath) throws IOException {

        String fileNameNoExtension = fileName.substring(0, fileName.lastIndexOf("."));

        String pattern = String.format("%s(?: \\((\\d+)\\))?%s", Pattern.quote(fileNameNoExtension), Pattern.quote(fileExtension));
        Pattern regex = Pattern.compile(pattern);

        int highestDuplicateIndex = 0;

        // get all matching fileNames
        List<String> fileNames = Files.list(Path.of(targetPath))
                .map(obj -> obj.toFile().getName())
                .filter(fileNameS -> fileNameS.matches(pattern))
                .toList();

        // get the index of each matching fileNames
        for (String file: fileNames) {
            Matcher matcher = regex.matcher(file);
            while (matcher.find()) {
                if (matcher.group(1) != null) {
                    // update the highest index
                    if (Integer.parseInt(matcher.group(1)) > highestDuplicateIndex)  {
                        highestDuplicateIndex = Integer.parseInt(matcher.group(1));
                    }
                }
            }
        }
        highestDuplicateIndex++;

        // rename file
        fileName = fileNameNoExtension + " ("  + highestDuplicateIndex + ")" + fileExtension;
        return fileName;
    }
}
