package com.svhelloworld.knotlog.db.berkeley;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.EnvironmentStats;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.StoreConfig;
import com.svhelloworld.knotlog.LocalFiles;
import com.svhelloworld.knotlog.preferences.Preferences;
import com.svhelloworld.knotlog.service.impl.PreferencesHashMapImpl;

/**
 * Testing database locking problem.
 */
public class DatabaseLocksTest {

    /*
     * This test is used to diagnose some lock contention trying to truncate the database
     * after each test.
     */

    private static Logger log = LoggerFactory.getLogger(DatabaseLocksTest.class);

    private Preferences preferences = new PreferencesHashMapImpl();

    private LocalFiles localFiles = new LocalFiles(preferences);

    private Environment environment;

    private EntityStore store;

    @Before
    public void setup() throws IOException {
        localFiles.initialize();
        EnvironmentConfig envConf = new EnvironmentConfig();
        envConf.setAllowCreate(true);
        envConf.setTransactional(true);
        environment = new Environment(localFiles.getDataDirectory(), envConf);

        logLockCount("after environment setup, before store setup");

        StoreConfig config = new StoreConfig();
        config.setAllowCreate(true);
        store = new EntityStore(environment, "testStore", config);
    }

    @Test
    public void test() {
        logLockCount("in test()");

        store.close();

        logLockCount("after store was closed");
    }

    private void logLockCount(String message) {
        log.info(message);
        EnvironmentStats stats = environment.getStats(null);
        log.info("number of read locks {}", stats.getNReadLocks());
        log.info("number of write locks {}", stats.getNWriteLocks());
    }
}
