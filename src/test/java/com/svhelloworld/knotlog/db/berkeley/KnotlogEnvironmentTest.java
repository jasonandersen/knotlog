package com.svhelloworld.knotlog.db.berkeley;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sleepycat.je.Environment;
import com.svhelloworld.knotlog.test.BaseIntegrationTest;

/**
 * Testing the {@link KnotlogEnvironment} class.
 */
public class KnotlogEnvironmentTest extends BaseIntegrationTest {

    private static Logger log = LoggerFactory.getLogger(KnotlogEnvironmentTest.class);

    @Autowired
    private KnotlogEnvironment environment;

    @Test
    public void testEnvironmentInjected() {
        assertNotNull(environment);
    }

    @Test
    public void testDatabaseDirectory() {
        File dir = environment.getDatabaseDirectory();
        assertNotNull(dir);
        assertTrue(dir.exists());
        assertTrue(dir.isDirectory());
        assertTrue(dir.canRead());
        assertTrue(dir.canWrite());

        log.info("database directory: {}", dir.getAbsolutePath());
    }

    @Test
    public void testGetEnvironment() {
        Environment dbEnv = environment.getEnvironment();
        assertNotNull(dbEnv);
        assertTrue(dbEnv.isValid());
    }

}
