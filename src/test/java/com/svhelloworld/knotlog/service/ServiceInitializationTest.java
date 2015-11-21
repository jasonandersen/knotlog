package com.svhelloworld.knotlog.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import com.google.common.eventbus.EventBus;
import com.svhelloworld.knotlog.events.AppInitializationStarted;
import com.svhelloworld.knotlog.test.BaseIntegrationTest;

/**
 * Tests to see that all initializable services get initialized properly.
 */
public class ServiceInitializationTest extends BaseIntegrationTest {

    private static Logger log = LoggerFactory.getLogger(ServiceInitializationTest.class);

    @Autowired
    private Collection<InitializableService> services;

    @Autowired
    private EventBus eventBus;

    @Test
    public void testDI() {
        assertNotNull(services);
        assertFalse(services.isEmpty());
    }

    @DirtiesContext
    @Test
    public void testInitializeEvent() {
        for (InitializableService service : services) {
            log.debug("asserting initialization is false on {}", service.getClass().getSimpleName());
            assertFalse(service.isInitialized());
        }
        eventBus.post(new AppInitializationStarted());
        for (InitializableService service : services) {
            log.debug("asserting initialization is true on {}", service.getClass().getSimpleName());
            assertTrue(service.isInitialized());
        }
    }

    @DirtiesContext
    @Test
    public void testReinitialization() {

    }
}
