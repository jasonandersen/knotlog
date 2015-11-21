package com.svhelloworld.knotlog;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.svhelloworld.knotlog.service.InitializableService;
import com.svhelloworld.knotlog.service.Preferences;

/**
 * The directories to store Knotlog specific data in.
 */
@Component
public class LocalFileSystem implements InitializableService {

    private static Logger log = LoggerFactory.getLogger(LocalFileSystem.class);

    private static final String DIRECTORY_NAME = "db";

    @Autowired
    private Preferences preferences;

    private File root;

    private File data;

    /**
     * @see com.svhelloworld.knotlog.service.InitializableService#initialize()
     */
    @Override
    public void initialize() {
        //resolveRootDirectory();
        //resolveDataDirectory();
    }

    /**
     * @see com.svhelloworld.knotlog.service.InitializableService#isInitialized()
     */
    @Override
    public boolean isInitialized() {
        return root != null && data != null;
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
     * Initializes the database directory to be a child directory off the knotlog directory
     */
    private void initDatabaseDirectory() {
        File knotlogDir = preferences.getKnotlogDirectory();
        StringBuilder path = new StringBuilder(knotlogDir.getAbsolutePath());
        if (!path.toString().endsWith(File.separator)) {
            path.append(File.separator);
        }
        path.append(DIRECTORY_NAME).append(File.separator);
        File dbDir = new File(path.toString());
        if (!dbDir.exists()) {
            dbDir.mkdir();
        }
        assert dbDir.exists();
        assert dbDir.canRead();
        log.info("using database directory {}", dbDir.getAbsolutePath());
    }

}
