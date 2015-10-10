package com.svhelloworld.knotlog.messages.validate;

import java.util.Map;

import com.svhelloworld.knotlog.messages.MagneticVariation;

/**
 * Validates {@link MagnaticVariation} messages.
 */
public class MagneticVariationValidator extends MessageAttributeValidator<MagneticVariation> {

    private static final String MAGNETIC_VARIATION = "magnetic variation";

    /**
     * @see com.svhelloworld.knotlog.messages.validate.MessageAttributeValidator#buildActualAttributes(com.svhelloworld.knotlog.messages.VesselMessage, java.util.Map)
     */
    @Override
    protected void buildActualAttributes(MagneticVariation message,
            Map<String, MessageAttributeValidator<MagneticVariation>.Attribute> attributes) {
        setActualAttributeValue(MAGNETIC_VARIATION, attributes, message.getDisplayMessage());
    }

}
