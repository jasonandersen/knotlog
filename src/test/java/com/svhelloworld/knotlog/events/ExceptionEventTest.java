package com.svhelloworld.knotlog.events;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.svhelloworld.knotlog.test.BaseIntegrationTest;

/**
 * Testing the {@link ExceptionEvent} class.
 */
public class ExceptionEventTest extends BaseIntegrationTest {

    @Autowired
    private EventBus eventBus;

    private ExceptionEvent event;

    @Before
    public void setup() {
        eventBus.register(this);
        eventBus.post(new Epicac());
    }

    @Test
    public void testEventIsThrown() {
        assertNotNull(event);
    }

    /**
     * Suscribe to epicac events so we can trigger the exception.
     * @param barf
     */
    @Subscribe
    public void barf(Epicac barf) {
        barf.barf();
    }

    /**
     * Subscribe to exception events to inspect the event that was raised.
     * @param exceptionEvent
     */
    @Subscribe
    public void handleExceptionEvent(ExceptionEvent exceptionEvent) {
        this.event = exceptionEvent;
    }

    /**
     * Testing class that is guaranteed to barf.
     */
    private class Epicac {
        void barf() {
            throw new RuntimeException("I'm gonna baaarrrrfffffffasdflkjeggghccchh....");
        }
    }
}
