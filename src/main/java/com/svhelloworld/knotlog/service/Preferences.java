package com.svhelloworld.knotlog.service;

/**
 * Provides application preferences.
 * 
 */
public interface Preferences {

    /**
     * Preferred water depth unit.
     */
    public String KEY_DEPTH_UNIT = "water.depth.unit";

    /**
     * @param key
     * @return application preference stored at that key, will return null if not found
     */
    <T> T get(String key);

    /**
     * Persists an application preference value at the specified key.
     * @param key
     * @param value
     */
    void put(String key, Object value);
}
