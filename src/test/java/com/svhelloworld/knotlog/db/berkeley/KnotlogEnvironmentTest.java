package com.svhelloworld.knotlog.db.berkeley;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sleepycat.je.Environment;
import com.svhelloworld.knotlog.test.BaseIntegrationTest;

/**
 * Testing the {@link KnotlogEnvironment} class.
 */
public class KnotlogEnvironmentTest extends BaseIntegrationTest {

    @Autowired
    private KnotlogEnvironment environment;

    @Test
    public void testEnvironmentInjected() {
        assertNotNull(environment);
    }

    @Test
    public void testGetEnvironment() {
        Environment dbEnv = environment.getEnvironment();
        assertNotNull(dbEnv);
        assertTrue(dbEnv.isValid());
    }

}
