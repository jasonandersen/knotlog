package com.svhelloworld.knotlog.messages.validate;

import java.util.Map;

import com.svhelloworld.knotlog.messages.VesselHeading;

/**
 * 
 * @author Jason Andersen (andersen.jason@gmail.com)
 */
public class VesselHeadingValidator extends MessageAttributeValidator<VesselHeading> {

    private static final String VESSEL_HEADING = "vessel heading";

    /**
     * @see com.svhelloworld.knotlog.messages.validate.MessageAttributeValidator#buildActualAttributes(com.svhelloworld.knotlog.messages.VesselMessage, java.util.Map)
     */
    @Override
    protected void buildActualAttributes(VesselHeading message,
            Map<String, MessageAttributeValidator<VesselHeading>.Attribute> attributes) {

        setActualAttributeValue(VESSEL_HEADING, attributes, message.toString());
    }

}
