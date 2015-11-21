package com.svhelloworld.knotlog.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.svhelloworld.knotlog.service.Preferences;

/**
 * Implementation to use for testing based on a {@link HashMap}. Will not persist any preferences
 * so is a good choice for use in testing.
 */
public class PreferencesHashMapImpl implements Preferences {

    private static Logger log = LoggerFactory.getLogger(PreferencesHashMapImpl.class);

    private Map<String, String> preferences = new HashMap<String, String>();

    /**
     * @see com.svhelloworld.knotlog.service.Preferences#get(java.lang.String)
     */
    @Override
    public String get(String key) {
        return preferences.get(key);
    }

    /**
     * @see com.svhelloworld.knotlog.service.Preferences#get(java.lang.String, java.lang.String)
     */
    @Override
    public String get(String key, String defaultValue) {
        String value = get(key);
        return value == null ? defaultValue : value;
    }

    /**
     * @see com.svhelloworld.knotlog.service.Preferences#getEnum(java.lang.String, java.lang.Class)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public <T extends Enum> T getEnum(String key, Class<T> type) {
        throw new UnsupportedOperationException("I can't be bothered to implement this. Maybe later.");
    }

    /**
     * @see com.svhelloworld.knotlog.service.Preferences#put(java.lang.String, java.lang.Object)
     */
    @Override
    public void put(String key, Object value) {
        log.info("storing {} as {}", key, value);
        preferences.put(key, value.toString());
    }

    /**
     * Wipes out all preferences stored so far.
     */
    public void clearAllPreferences() {
        preferences.clear();
    }
}
