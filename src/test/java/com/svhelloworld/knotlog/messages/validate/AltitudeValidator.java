package com.svhelloworld.knotlog.messages.validate;

import java.util.Map;

import com.svhelloworld.knotlog.messages.Altitude;

/**
 * Asserts {@link Altitude} objects are valid.
 */
public class AltitudeValidator extends MessageAttributeValidator<Altitude> {

    private final static String ALTITUDE = "altitude";

    /**
     * @see com.svhelloworld.knotlog.messages.validate.MessageAttributeValidator#buildActualAttributes(com.svhelloworld.knotlog.messages.VesselMessage, java.util.Map)
     */
    @Override
    protected void buildActualAttributes(Altitude message,
            Map<String, MessageAttributeValidator<Altitude>.Attribute> attributes) {

        String actualValue = String.format("%.1f %s", message.getDistance(), message.getDistanceUnit().toString());
        setActualAttributeValue(ALTITUDE, attributes, actualValue);
    }

}
