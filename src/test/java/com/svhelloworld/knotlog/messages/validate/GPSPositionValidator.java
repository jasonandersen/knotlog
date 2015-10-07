package com.svhelloworld.knotlog.messages.validate;

import java.util.Map;

import com.svhelloworld.knotlog.messages.GPSPosition;
import com.svhelloworld.knotlog.messages.PositionFormat;

/**
 * Will validate {@link GPSPosition} messages against expected values.
 */
public class GPSPositionValidator extends MessageAttributeValidator<GPSPosition> {

    private static final String POSITION = "position";

    /**
     * @see com.svhelloworld.knotlog.messages.validate.MessageAttributeValidator#buildActualAttributes(com.svhelloworld.knotlog.messages.VesselMessage, java.util.Map)
     */
    @Override
    protected void buildActualAttributes(GPSPosition message,
            Map<String, MessageAttributeValidator<GPSPosition>.Attribute> attributes) {

        String actualPosition = PositionFormat.DEGREES_MINUTES.format(message);
        setActualAttributeValue(POSITION, attributes, actualPosition);
    }

}
