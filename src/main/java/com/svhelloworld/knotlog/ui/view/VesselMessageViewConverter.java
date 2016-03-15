package com.svhelloworld.knotlog.ui.view;

import org.apache.commons.lang.Validate;
import org.springframework.core.convert.converter.Converter;

import com.svhelloworld.knotlog.domain.messages.DistanceMessage;
import com.svhelloworld.knotlog.domain.messages.PositionMessage;
import com.svhelloworld.knotlog.domain.messages.RudderAngle;
import com.svhelloworld.knotlog.domain.messages.SpeedMessage;
import com.svhelloworld.knotlog.domain.messages.VesselMessage;
import com.svhelloworld.knotlog.domain.messages.WaterDepth;
import com.svhelloworld.knotlog.domain.messages.WindSpeed;

/**
 * Creates {@link VesselMessageView}s from {@link VesselMessage} objects.
 */
public class VesselMessageViewConverter implements Converter<VesselMessage, VesselMessageView<?>> {

    /**
     * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    @Override
    public VesselMessageView<?> convert(VesselMessage source) {
        Validate.notNull(source);
        if (source instanceof WindSpeed) {
            return new WindSpeedView((WindSpeed) source);
        }
        if (source instanceof WaterDepth) {
            return new WaterDepthView((WaterDepth) source);
        }
        if (source instanceof PositionMessage) {
            return new PositionView((PositionMessage) source);
        }
        if (source instanceof RudderAngle) {
            return new RudderAngleView((RudderAngle) source);
        }
        if (source instanceof SpeedMessage) {
            return new SpeedView((SpeedMessage) source);
        }
        if (source instanceof DistanceMessage) {
            return new DistanceView((DistanceMessage) source);
        }
        String message = String.format("source could not be converted - type=%s;value=%s", source.getClass(), source.toString());
        throw new IllegalArgumentException(message);
    }

}
