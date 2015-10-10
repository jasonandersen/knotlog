package com.svhelloworld.knotlog.messages.validate;

import java.util.Map;

import com.svhelloworld.knotlog.messages.WaterDepth;

/**
 * 
 * @author Jason Andersen (andersen.jason@gmail.com)
 */
public class WaterDepthValidator extends MessageAttributeValidator<WaterDepth> {

    private static final String WATER_DEPTH = "water depth";

    /**
     * @see com.svhelloworld.knotlog.messages.validate.MessageAttributeValidator#buildActualAttributes(com.svhelloworld.knotlog.messages.VesselMessage, java.util.Map)
     */
    @Override
    protected void buildActualAttributes(WaterDepth message,
            Map<String, MessageAttributeValidator<WaterDepth>.Attribute> attributes) {
        String actualValue = String.format("%.1f %s", message.getDistance(), message.getDistanceUnit().toString());
        setActualAttributeValue(WATER_DEPTH, attributes, actualValue);
    }

}
