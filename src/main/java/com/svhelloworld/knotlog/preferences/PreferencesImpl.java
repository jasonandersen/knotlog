package com.svhelloworld.knotlog.preferences;

import java.util.prefs.Preferences;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link Preferences} using the Java Preferences API.
 */
@Service
public class PreferencesImpl implements com.svhelloworld.knotlog.preferences.Preferences {

    private static final Logger log = LoggerFactory.getLogger(PreferencesImpl.class);

    private Preferences preferences;

    /**
     * Constructor - use this constructor!
     */
    public PreferencesImpl() {
        this(PreferencesImpl.class);
    }

    /**
     * Protected constructor for testing purposes to allow tests to define a different class
     * node to write preferences to so as not to interfere with existing preferences.
     * @param nodeClass
     */
    protected PreferencesImpl(final Class<?> nodeClass) {
        log.info("instantiating preferences");
        preferences = Preferences.userNodeForPackage(getClass());
    }

    /**
     * @see com.svhelloworld.knotlog.preferences.Preferences#get(java.lang.String)
     */
    @Override
    public String get(String key) {
        log.debug("retrieving preference {}", key);
        return preferences.get(key, null);
    }

    /**
     * @see com.svhelloworld.knotlog.preferences.Preferences#get(java.lang.String, java.lang.String)
     */
    @Override
    public String get(String key, String defaultValue) {
        String value = get(key);
        return value == null ? defaultValue : value;
    }

    /**
     * @see com.svhelloworld.knotlog.preferences.Preferences#getEnum(java.lang.String, java.lang.Class)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public <T extends Enum> T getEnum(String key, Class<T> type) {
        log.debug("retrieving enum preference {}", key);
        String preference = preferences.get(key, null);
        for (T enumInstance : type.getEnumConstants()) {
            if (enumInstance.toString().equalsIgnoreCase(preference)) {
                return enumInstance;
            }
        }
        throw new IllegalArgumentException(String.format("Enum %s not found", preference));
    }

    /**
     * @see com.svhelloworld.knotlog.preferences.Preferences#put(java.lang.String, java.lang.Object)
     */
    @Override
    public void put(String key, Object value) {
        log.info("setting preference {} as {}", key, value);
        preferences.put(key, value.toString());
    }

    /**
     * @return the preferences instance
     */
    protected Preferences getPreferences() {
        return preferences;
    }

}
