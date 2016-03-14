package com.svhelloworld.knotlog.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.svhelloworld.knotlog.exceptions.ExceptionEvent;
import com.svhelloworld.knotlog.exceptions.ExceptionHandler;
import com.svhelloworld.knotlog.exceptions.ExceptionHandler.Listener;
import com.svhelloworld.knotlog.test.BaseIntegrationTest;

/**
 * Testing the {@link ExceptionHandler} class.
 */
public class ExceptionHandlerTest extends BaseIntegrationTest implements Listener {

    private static final String EXCEPTION_MESSAGE = "I'm gonna baaarrrrfffffffasdflkjeggghccchh....";

    private static final String THIS_TO_STRING = "ExceptionHandler";

    private static final String EPICAC_TO_STRING = "Epicac";

    @Autowired
    private EventBus eventBus;

    @Autowired
    private ExceptionHandler exceptionHandler;

    private ExceptionEvent event;

    @Before
    public void setup() {
        exceptionHandler.addListener(this);
        eventBus.register(this);
        eventBus.post(new Epicac());
    }

    @Test
    public void testEventIsThrown() {
        assertNotNull(event);
    }

    @Test
    public void testEventExceptionFromEventBusException() {
        assertNotNull(event.getException());
        assertEquals(EXCEPTION_MESSAGE, event.getException().getMessage());
    }

    @Test
    public void testAdditionalInfoFromEventBusException() {
        Map<String, Object> info = event.getContext();
        assertNotNull(info);
        assertFalse(info.isEmpty());
        assertEquals(this, info.get("subscriber"));
        assertTrue(info.get("subscriber method") instanceof Method);
        assertTrue(info.get("event") instanceof Epicac);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAdditionalInfoUnmodifiable() {
        Map<String, Object> info = event.getContext();
        info.clear();
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
     * @see com.svhelloworld.knotlog.exceptions.ExceptionHandler.Listener#handleException(com.svhelloworld.knotlog.exceptions.ExceptionEvent)
     */
    @Override
    public void handleException(ExceptionEvent exceptionEvent) {
        event = exceptionEvent;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return THIS_TO_STRING;
    }

    /**
     * Testing class that is guaranteed to barf.
     */
    private class Epicac {
        void barf() {
            throw new RuntimeException(EXCEPTION_MESSAGE);
        }

        /**
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return EPICAC_TO_STRING;
        }
    }

}
