package com.svhelloworld.knotlog.messages.validate;

import java.util.Map;

import com.svhelloworld.knotlog.messages.Altitude;

/**
 * Asserts {@link Altitude} objects are valid.
 */
public class AltitudeValidator extends BaseValidator implements MessageValidator<Altitude> {

    private final static String ALTITUDE = "altitude";

    /**
     * @see com.svhelloworld.knotlog.messages.validate.MessageValidator#assertValid(com.svhelloworld.knotlog.messages.VesselMessage, java.util.Map)
     */
    @Override
    public void assertValid(Altitude message, Map<String, String> expectedAttributes) {
        assertAltitude(message, expectedAttributes);
        assertSource(message, expectedAttributes);
    }

    /**
     * @param message
     * @param expectedAttributes
     */
    private void assertAltitude(Altitude message, Map<String, String> expectedAttributes) {
        String actualValue = String.format("%.1f %s", message.getDistance(), message.getDistanceUnit().toString());
        assertAttribute(message, expectedAttributes, ALTITUDE, actualValue);
    }

}
