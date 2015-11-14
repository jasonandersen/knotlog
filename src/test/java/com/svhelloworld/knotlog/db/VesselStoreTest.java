package com.svhelloworld.knotlog.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;
import com.svhelloworld.knotlog.domain.Vessel;
import com.svhelloworld.knotlog.test.BaseIntegrationTest;

/**
 * Test the {@link VesselStore} implementation.
 */
public class VesselStoreTest extends BaseIntegrationTest {

    private static Logger log = LoggerFactory.getLogger(VesselStoreTest.class);

    private Environment environment;

    private EntityStore store;

    private PrimaryIndex<Integer, Vessel> primaryIndex;

    private Vessel vessel;

    @Before
    public void createEnvironment() throws DatabaseException {
        log.info("creating environment");
        EnvironmentConfig envConf = new EnvironmentConfig();
        envConf.setAllowCreate(true);
        environment = new Environment(new File("/Users/jason/dev/workspace/knotlog/temp/db"), envConf);

        log.info("creating entity store");
        StoreConfig config = new StoreConfig();
        config.setAllowCreate(true);
        store = new EntityStore(environment, "Walmart", config);

        log.info("setting up primary index");
        primaryIndex = store.getPrimaryIndex(Integer.class, Vessel.class);

        log.info("setting up vessel");
        vessel = new Vessel();
        vessel.setId(1);
        vessel.setName("s/v hello world");

        log.info("storing vessel");
        primaryIndex.put(vessel);

    }

    @After
    public void cleanup() throws DatabaseException {
        if (store != null) {
            log.info("closing entity store");
            store.close();
        }
        if (environment != null) {
            log.info("closing environment");
            environment.close();
        }
    }

    @Test
    public void testRead() throws DatabaseException {
        Vessel storedVessel = primaryIndex.get(1);
        assertNotNull(storedVessel);
        assertEquals(1, storedVessel.getId(), 0);
        assertEquals("s/v hello world", storedVessel.getName());
        log.info("vessel id={} name={}", storedVessel.getId(), storedVessel.getName());
    }

    @Test
    public void testEdit() throws DatabaseException {
        vessel.setName("s/v Monkey Nutz");
        primaryIndex.put(vessel);
        Vessel storedVessel = primaryIndex.get(1);
        assertNotNull(storedVessel);
        assertEquals("s/v Monkey Nutz", vessel.getName());
        log.info("vessel id={} name={}", storedVessel.getId(), storedVessel.getName());
    }

}
