package com.sjadrian;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {

    @Test
    void validConfigFileNameAndDirectoryKeyShouldReturnValidDirectory() {

        String configFileName  = "config";
        String directoryKey = "DOWNLOAD_PATH";

        Config config = new Config(configFileName, directoryKey);

        assertNotNull(config.getDirectory());
    }
    @Test
    void invalidConfigFileNameShouldReturnRunTimeException() {

        String configFileName  = "confixg";
        String directoryKey = "DOWNLOAD_PATH";

        assertThrows(RuntimeException.class,
                () -> {
                    Config config = new Config(configFileName, directoryKey);
        });
    }

    @Test
    void configClassHasValidDirectoryKey() {

        String configFileName  = "config";
        String directoryKey = "DOWNLOAD_PATH";
        Config config = new Config(configFileName, directoryKey);

        assertNotNull(config.getDirectory());
    }

    @Test
    void invalidDirectoryKeyShouldReturnIllegalStateException() {

        String configFileName  = "config";
        String directoryKey = "DOWNLOAD_PATHX";

        assertThrows(IllegalStateException.class,
                () -> {
                    Config config = new Config(configFileName, directoryKey);
                });
    }

    @Test
    void invalidDirectoryObtainedFromDirectoryKeyShouldReturnIllegalStateException() {

        String configFileName  = "config";
        String directoryKey = "DOWNLOAD_PATH_WITH_INVALID_DIRECTORY";

        assertThrows(IllegalStateException.class,
                () -> {
                    Config config = new Config(configFileName, directoryKey);
                });
    }
}