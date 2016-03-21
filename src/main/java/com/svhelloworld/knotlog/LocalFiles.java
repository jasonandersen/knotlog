package com.svhelloworld.knotlog;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.svhelloworld.knotlog.preferences.Preferences;

/**
 * Provides abstraction around interactions with the local file system and where
 * to store and retrieve knotlog data.
 */
@Component
public class LocalFiles {

    private static Logger log = LoggerFactory.getLogger(LocalFiles.class);

    private static final String ROOT_DIRECTORY_NAME = "knotlog";

    private static final String DATA_DIRECTORY_NAME = "data";

    private Preferences preferences;

    private File root;

    private File data;

    /**
     * Constructor
     * @param preferences
     */
    @Autowired
    public LocalFiles(Preferences preferences) {
        Validate.notNull(preferences);
        this.preferences = preferences;
    }

    /**
     * DO NOT CALL THIS METHOD. Will be called by lifecycle container.
     * @throws IOException 
     */
    @PostConstruct
    public void initialize() throws IOException {
        resolveRootDirectory();
        resolveDataDirectory();
    }

    /**
     * @return the root Knotlog directory
     */
    public File getRootDirectory() {
        return root;
    }

    /**
     * @return the directory that stores the database
     */
    public File getDataDirectory() {
        return data;
    }

    /**
     * Resolve the root directory by looking in the preferences first. If not in the preferences, then
     * point to the default directory in the user's home directory.
     * @throws IOException 
     */
    private void resolveRootDirectory() throws IOException {
        String defaultPath = buildDefaultRootPath();
        String path = preferences.get(Preferences.KEY_ROOT_DIRECTORY, defaultPath);
        root = buildDirectory(path);
        preferences.put(Preferences.KEY_ROOT_DIRECTORY, root.getAbsolutePath());
        log.info("Knotlog root directory resolved to {}", root.getAbsolutePath());
    }

    /**
     * Resolve the data directory by looking in the preferences first. If not in the preferences, then
     * use a child directory of the root directory.
     * @throws IOException 
     */
    private void resolveDataDirectory() throws IOException {
        String defaultPath = buildDefaultDataPath();
        String path = preferences.get(DATA_DIRECTORY_NAME, defaultPath);
        data = buildDirectory(path);
        preferences.put(Preferences.KEY_DATA_DIRECTORY, data.getAbsolutePath());
        log.info("Knotlog data directory resolved to {}", data.getAbsolutePath());
    }

    /**
     * @return the default path off the Knotlog root directory
     */
    private String buildDefaultDataPath() {
        StringBuilder builder = new StringBuilder(root.getAbsolutePath());
        if (!builder.toString().endsWith(File.separator)) {
            builder.append(File.separator);
        }
        builder.append(DATA_DIRECTORY_NAME);
        log.debug("data default directory {}", builder);
        return builder.toString();
    }

    /**
     * @return the default path in the user's home directory
     */
    private String buildDefaultRootPath() {
        StringBuilder builder = new StringBuilder(FileUtils.getUserDirectoryPath());
        if (!builder.toString().endsWith(File.separator)) {
            builder.append(File.separator);
        }
        builder.append(ROOT_DIRECTORY_NAME);
        log.debug("root default directory {}", builder);
        return builder.toString();
    }

    /**
     * @param path
     * @return a valid {@link File} that exists on the file system and is readable and writeable
     * @throws IOException 
     */
    private File buildDirectory(String path) throws IOException {
        File directory = new File(path);
        if (!directory.exists()) {
            log.info("creating new directory {}", path);
            FileUtils.forceMkdir(directory);
        }
        Validate.isTrue(directory.exists());
        Validate.isTrue(directory.canRead());
        Validate.isTrue(directory.canWrite());
        return directory;
    }

}
