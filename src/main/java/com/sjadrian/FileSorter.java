package com.sjadrian;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
                ? renameFile(fileName) : fileName;

        try {
            // move the file to its designated folder based on each type
            Path temp = Files.move(Path.of(directoryPath + fileName), Path.of(targetPathString + targetFileName));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String renameFile(String fileName) throws IOException {

        // get the file information
        String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf("."));
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        String targetPath = directoryPath + "\\" + fileExtension.substring(1) + "\\";

        String pattern = String.format("%s(?: \\((\\d+)\\))?%s", Pattern.quote(fileNameWithoutExtension), Pattern.quote(fileExtension));
        Pattern regex = Pattern.compile(pattern);

        // get all matching fileNames
        List<String> fileNames = Files.list(Path.of(targetPath))
                .map(Path::getFileName)
                .map(Path::toString)
                .filter(fileNameS -> fileNameS.matches(pattern))
                .toList();

        int highestDuplicateIndex = 0;

        // get the index of each matching fileNames
        for (String file: fileNames) {
            Matcher matcher = regex.matcher(file);
            while (matcher.find()) {
                if (matcher.group(1) != null && Integer.parseInt(matcher.group(1)) > highestDuplicateIndex) {
                    // update the highest index
                    highestDuplicateIndex = Integer.parseInt(matcher.group(1));
                }
            }
        }
        highestDuplicateIndex++;

        // rename the duplicate file
        fileName = fileNameWithoutExtension + " ("  + highestDuplicateIndex + ")" + fileExtension;
        return fileName;
    }
}
