package com.svhelloworld.knotlog.db.berkeley;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.svhelloworld.knotlog.service.Preferences;

/**
 * Responsible for setting up a BerkeleyDB {@link Environment}.
 */
@Component
public class KnotlogEnvironment {

    private static Logger log = LoggerFactory.getLogger(KnotlogEnvironment.class);

    private static final String DIRECTORY_NAME = "db";

    @Autowired
    private Preferences preferences;

    private Environment environment;

    private File databaseDirectory;

    /**
     * @return the Berkeley DB JE environment
     */
    public Environment getEnvironment() {
        return environment;
    }

    /**
     * Do not call directly. Will be called during the lifecycle of this managed bean.
     */
    @PostConstruct
    private void initialize() {
        log.info("initializing");
        initDatabaseDirectory();
        initEnvironment();
    }

    /**
     * Do not call directly. Will be called during the lifecycle of this managed bean.
     */
    @PreDestroy
    private void closeEnvironment() {
        log.info("closing database environment");
        if (environment == null) {
            return;
        }
        environment.close();
    }

    /**
     * @return the directory used to store the database files
     */
    protected File getDatabaseDirectory() {
        return databaseDirectory;
    }

    /**
     * Initializes Berkeley DB JE environment.
     */
    private void initEnvironment() {
        log.info("initializing Berkeley DB environment");
        EnvironmentConfig envConf = new EnvironmentConfig();
        envConf.setAllowCreate(true);
        environment = new Environment(getDatabaseDirectory(), envConf);
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
        databaseDirectory = dbDir;
    }

}
