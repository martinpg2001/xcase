/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.common.utils;

import java.io.File;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martinpg
 */
public class FileUtils {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method renames a file from its source name to a target name.
     * Locations of both source and target are relative to the specified
     * directory.
     */
    public static void renameFile(File directory, String sourceName, String targetName) {
        try {
            if (directory == null || !directory.isDirectory()) {
                LOGGER.warn(directory + " should be not null and should be a directory");
            } else {
                File sourceFile = new File(directory.getAbsolutePath() + System.getProperty("file.separator") + sourceName);
                LOGGER.info("sourceFile is " + sourceFile);
                File targetFile = new File(directory.getAbsolutePath() + System.getProperty("file.separator") + targetName);
                LOGGER.info("targetFile is " + targetFile);
                sourceFile.renameTo(targetFile);
                LOGGER.info("renamed file");
            }
        } catch (Exception e) {
            LOGGER.warn("exception renaming file: " + e.getMessage());
        }
    }

    private FileUtils() {
    }
}
