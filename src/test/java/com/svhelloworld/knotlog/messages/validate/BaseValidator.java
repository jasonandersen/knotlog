package com.svhelloworld.knotlog.messages.validate;

import java.util.Map;

import org.junit.Assert;

import com.svhelloworld.knotlog.messages.VesselMessage;

/**
 * Provides some basic functionality for validating common attributes.
 */
public class BaseValidator {

    private static final String SOURCE = "source";

    /**
     * Assert source matches expected value
     * @param message
     * @param expectedAttributes
     */
    protected void assertSource(VesselMessage message, Map<String, String> expectedAttributes) {
        assertAttribute(message, expectedAttributes, SOURCE, message.getSource().toString());
    }

    /**
     * Asserts a single attribute. Will do nothing if the expected attribute is not in the map.
     * @param message
     * @param expectedAttributes
     * @param attributeName
     * @param actualValue
     */
    protected void assertAttribute(
            VesselMessage message,
            Map<String, String> expectedAttributes,
            String attributeName,
            String actualValue) {

        String expectedValue = expectedAttributes.get(attributeName);
        if (expectedValue != null) {
            Assert.assertEquals(expectedValue, actualValue);
        }
    }

}
