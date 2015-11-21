package com.svhelloworld.knotlog.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.eventbus.EventBus;
import com.svhelloworld.knotlog.test.BaseIntegrationTest;

/**
 * Ensure dead events that are {@link Request}s throw an exception.
 */
public class DeadRequestEventsTest extends BaseIntegrationTest {

    @Autowired
    private EventBus eventBus;

    @Test
    public void testDeadRequestEventThrowsException() {
        DummyRequest request = new DummyRequest();

        try {
            eventBus.post(request);
            fail("No exception was thrown."); //if we get to here, the test didn't work
        } catch (RequestIgnoredException e) {
            assertEquals(request, e.getRequest());
        }
    }

    /**
     * A dummy request that should end up as a dead event.
     */
    private class DummyRequest implements Request<String> {

        /**
         * @see com.svhelloworld.knotlog.events.Request#hasResponse()
         */
        @Override
        public boolean hasResponse() {
            return false;
        }

        /**
         * @see com.svhelloworld.knotlog.events.Request#getResponse()
         */
        @Override
        public String getResponse() {
            return null;
        }

    }
}
