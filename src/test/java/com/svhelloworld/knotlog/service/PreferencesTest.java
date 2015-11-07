package com.svhelloworld.knotlog.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.svhelloworld.knotlog.measure.DistanceUnit;
import com.svhelloworld.knotlog.test.BaseIntegrationTest;

/**
 * Test the {@link Preferences} implementation.
 */
public class PreferencesTest extends BaseIntegrationTest {

    @Autowired
    private Preferences preferences;

    @Test
    public void testDependencyInjection() {
        assertNotNull(preferences);
    }

    @Test
    public void testString() {
        String key = "he.likes.monkeys";
        String value = "I LIKE MONKEYS!";
        preferences.put(key, value);

        String retrievedValue = preferences.get(key);
        assertEquals(value, retrievedValue);
    }

    @Test
    public void testEnum() {
        String key = "water.depth";
        DistanceUnit value = DistanceUnit.FATHOMS;
        preferences.put(key, value);

        DistanceUnit retrievedValue = preferences.getEnum(key, DistanceUnit.class);
        assertEquals(value, retrievedValue);
    }
}
