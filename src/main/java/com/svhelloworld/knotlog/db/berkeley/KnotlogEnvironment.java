package com.svhelloworld.knotlog.db.berkeley;

import java.io.File;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.Transaction;
import com.svhelloworld.knotlog.LocalFileSystem;
import com.svhelloworld.knotlog.service.InitializableService;

/**
 * Responsible for setting up a BerkeleyDB {@link Environment}.
 */
@Component
public class KnotlogEnvironment implements InitializableService {

    private static Logger log = LoggerFactory.getLogger(KnotlogEnvironment.class);

    @Autowired
    private LocalFileSystem directories;

    private Environment environment;

    /**
     * @return the Berkeley DB JE environment
     */
    public Environment getEnvironment() {
        return environment;
    }

    /**
     * Starts a new transaction 
     * @return the transaction handle
     */
    public Transaction startTransaction() {
        return environment.beginTransaction(null, null);
    }

    /**
     * @see com.svhelloworld.knotlog.service.InitializableService#isInitialized()
     */
    @Override
    public boolean isInitialized() {
        return environment != null;
    }

    /**
     * Do not call directly. Will be called during the lifecycle of this managed bean.
     */
    @Override
    public void initialize() {
        log.info("initializing");
        initEnvironment();
    }

    /**
     * Do not call directly. Will be called during the lifecycle of this managed bean.
     */
    @PreDestroy
    protected void closeEnvironment() {
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
        return directories.getDataDirectory();
    }

    /**
     * Initializes Berkeley DB JE environment.
     */
    private void initEnvironment() {
        log.info("initializing Berkeley DB environment");
        EnvironmentConfig envConf = new EnvironmentConfig();
        envConf.setAllowCreate(true);
        envConf.setTransactional(true);
        environment = new Environment(getDatabaseDirectory(), envConf);
    }

}
