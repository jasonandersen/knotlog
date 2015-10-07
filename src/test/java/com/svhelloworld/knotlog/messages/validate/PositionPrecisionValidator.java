package com.svhelloworld.knotlog.messages.validate;

import java.util.Map;

import com.svhelloworld.knotlog.messages.PositionPrecision;

/**
 * Asserts {@link PositionPrecision} messages are valid.
 */
public class PositionPrecisionValidator extends MessageAttributeValidator<PositionPrecision> {

    private static final String POSITION_PRECISION = "position precision";

    /**
     * @see com.svhelloworld.knotlog.messages.validate.MessageAttributeValidator#buildActualAttributes(com.svhelloworld.knotlog.messages.VesselMessage, java.util.Map)
     */
    @Override
    protected void buildActualAttributes(PositionPrecision message,
            Map<String, MessageAttributeValidator<PositionPrecision>.Attribute> attributes) {

        String actualValue = String.format("%.1f %s", message.getDistance(), message.getDistanceUnit().toString());
        setActualAttributeValue(POSITION_PRECISION, attributes, actualValue);
    }

}
