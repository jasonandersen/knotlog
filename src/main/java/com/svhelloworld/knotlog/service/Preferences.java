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
     * Root directory for all knotlog data. 
     */
    public String KEY_ROOT_DIRECTORY = "directory.root";
    /**
     * Directory where the knotlog database is stored.
     */
    public String KEY_DATA_DIRECTORY = "directory.data";

    /**
     * @param key
     * @return application preference stored at that key, will return null if not found
     */
    String get(String key);

    /**
     * @param key
     * @param defaultValue
     * @return application preference stored at that key, if no preference is found at that key
     *      then this method will return defaultValue
     */
    String get(String key, String defaultValue);

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
