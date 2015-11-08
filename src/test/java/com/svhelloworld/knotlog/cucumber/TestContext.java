package com.svhelloworld.knotlog.cucumber;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * All testing state should reside in this class. This class enables us to hold context 
 * across multiple step definition classes.
 */
@Component
public class TestContext {

    private static final Logger log = LoggerFactory.getLogger(TestContext.class);

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
    public Object getObject(String key) {
        log.debug("retrieving from key: {}", key);
        return context.get(key);
    }

    /**
     * @param key
     * @return the object stored with the key, if the key is not found will return null
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        log.debug("retrieving from key: {}", key);
        return (T) context.get(key);
    }

    /**
     * Stores a value in the context using the associated key
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        log.debug("storing key {} as {}", key, value);
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
