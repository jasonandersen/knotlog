package com.svhelloworld.knotlog.messages.validate;

import java.util.Map;

import com.svhelloworld.knotlog.messages.PositionPrecision;

/**
 * Asserts {@link PositionPrecision} messages are valid.
 */
public class PositionPrecisionValidator extends BaseValidator implements MessageValidator<PositionPrecision> {

    private static final String POSITION_PRECISION = "position precision";

    /**
     * @see com.svhelloworld.knotlog.messages.validate.MessageValidator#assertValid(com.svhelloworld.knotlog.messages.VesselMessage, java.util.Map)
     */
    @Override
    public void assertValid(PositionPrecision message, Map<String, String> expectedAttributes) {
        assertPositionPrecision(message, expectedAttributes);
        assertSource(message, expectedAttributes);
    }

    /**
     * @param message
     * @param expectedAttributes
     */
    private void assertPositionPrecision(PositionPrecision message, Map<String, String> expectedAttributes) {
        /*
         * Should come in the format:
         *      1.7 meters
         */
        String actualValue = String.format("%.1f %s", message.getDistance(), message.getDistanceUnit().toString());
        assertAttribute(message, expectedAttributes, POSITION_PRECISION, actualValue);
    }

}
