package com.svhelloworld.knotlog.ui.currentstate;

import org.apache.commons.lang.Validate;
import org.springframework.core.convert.converter.Converter;

import com.svhelloworld.knotlog.messages.DistanceMessage;
import com.svhelloworld.knotlog.messages.PositionMessage;
import com.svhelloworld.knotlog.messages.SpeedMessage;
import com.svhelloworld.knotlog.messages.VesselMessage;
import com.svhelloworld.knotlog.messages.WaterDepth;
import com.svhelloworld.knotlog.messages.WindSpeed;

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
