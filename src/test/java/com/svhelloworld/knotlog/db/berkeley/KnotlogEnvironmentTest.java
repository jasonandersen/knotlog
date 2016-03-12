package com.svhelloworld.knotlog.db.berkeley;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

import com.sleepycat.je.Environment;
import com.svhelloworld.knotlog.test.BaseIntegrationTest;

/**
 * Testing the {@link KnotlogDatabase} class.
 */
@Ignore //Berkeley DB not working right now
public class KnotlogEnvironmentTest extends BaseIntegrationTest {

    //@Autowired
    private KnotlogDatabase environment;

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
