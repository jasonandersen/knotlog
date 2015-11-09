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
    String get(String key);

    /**
     * @param key
     * @param type
     * @return application preference converted to an enum of type T
     * @throws IllegalArgumentException when enum is not found
     */
    @SuppressWarnings("rawtypes")
    <T extends Enum> T getEnum(String key, Class<T> type);

    /**
     * Persists an application preference value at the specified key.
     * @param key
     * @param value
     */
    void put(String key, Object value);
}