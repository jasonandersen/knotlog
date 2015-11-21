package com.svhelloworld.knotlog.test;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * Integration tests requiring dependency injection should extend from this class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public abstract class BaseIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(BaseIntegrationTest.class);

    @Autowired
    private EventBus eventBus;

    /**
     * Register testing class on event bus before every test.
     */
    @Before
    public void registerOnEventBus() {
        eventBus.register(this);
    }

    /**
     * Unregister testing class on event bus after every test to prevent memory leaks during
     * unit testing.
     */
    @After
    public void unregisterFromEventBus() {
        eventBus.unregister(this);
    }

    /**
     * Post an event on to the event bus.
     * @param event
     */
    protected void post(Object event) {
        eventBus.post(event);
    }

    @Test
    public void testDependencyInjection() {
        assertNotNull(eventBus);
    }

    /*
     * Event bus event handler methods
     */

    /**
     * Log any dead events.
     * @param deadEvent
     */
    @Subscribe
    public void handleDeadEvents(DeadEvent deadEvent) {
        log.warn("dead event: {}", deadEvent.getEvent());
    }

}
