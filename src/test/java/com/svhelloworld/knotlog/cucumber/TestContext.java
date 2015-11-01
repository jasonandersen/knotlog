package com.svhelloworld.knotlog.cucumber;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Holds context to enable acceptance testing across multiple step definition classes.
 */
@Component
public class TestContext {

    private static final Logger log = Logger.getLogger(TestContext.class);

    private Map<String, Object> context;

    /**
     * Constructor
     */
    public TestContext() {
        reset();
    }

    /**
     * @param key
     * @return the object stored with the key, if the key is not found will return null
     */
    public Object get(String key) {
        log.debug(String.format("retrieving from key: %s", key));
        return context.get(key);
    }

    /**
     * Stores a value in the context using the associated key
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        log.debug(String.format("storing key %s as %s", key, value));
        context.put(key, value);
    }

    /**
     * Emptys the context and resets it for a new test scenario.
     */
    public void reset() {
        log.debug("resetting test context");
        context = new HashMap<String, Object>();
    }

}
