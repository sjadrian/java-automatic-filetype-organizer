package com.sjadrian;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {
        String configFileName  = "config";
        String directoryKey = "DOWNLOAD_PATH";
        Config config = new Config(configFileName, directoryKey);
        FileSorter fileSorter = new FileSorter(config.getDirectoryConfig());
        fileSorter.run();
    }
}
