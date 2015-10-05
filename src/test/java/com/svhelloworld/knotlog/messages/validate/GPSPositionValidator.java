package com.svhelloworld.knotlog.messages.validate;

import java.util.Map;

import com.svhelloworld.knotlog.messages.GPSPosition;
import com.svhelloworld.knotlog.messages.PositionFormat;

/**
 * Will validate {@link GPSPosition} messages against expected values.
 */
public class GPSPositionValidator extends BaseValidator implements MessageValidator<GPSPosition> {

    private static final String POSITION = "position";

    /**
     * @see com.svhelloworld.knotlog.messages.validate.MessageValidator#assertValid(com.svhelloworld.knotlog.messages.VesselMessage, java.util.Map)
     */
    @Override
    public void assertValid(GPSPosition message, Map<String, String> expectedAttributes) {
        assertPosition(message, expectedAttributes);
        assertSource(message, expectedAttributes);
    }

    /**
     * @param message
     * @param expectedAttributes
     */
    private void assertPosition(GPSPosition message, Map<String, String> expectedAttributes) {
        String actualPosition = PositionFormat.DEGREES_MINUTES.format(message);
        assertAttribute(message, expectedAttributes, POSITION, actualPosition);
    }

}
