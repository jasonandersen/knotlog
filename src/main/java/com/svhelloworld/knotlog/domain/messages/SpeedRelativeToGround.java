package com.svhelloworld.knotlog.domain.messages;

import java.time.Instant;

import com.svhelloworld.knotlog.measure.SpeedUnit;

/**
 * Message communicating a vessel's speed relative to the earth.
 * 
 * @author Jason Andersen
 * @since Feb 27, 2010
 *
 */
public class SpeedRelativeToGround extends VesselSpeed {

    /**
     * Constructor.
     * @param source message source
     * @param timestamp timestamp of message event
     * @param speed vessel speed
     * @param unit unit of measure
     * @throws NullPointerException when source is null
     * @throws NullPointerException when timestamp is null
     * @throws NullPointerException when measurement is null
     * @throws IllegalArgumentException when speed is less than zero
     */
    public SpeedRelativeToGround(
            final VesselMessageSource source,
            final Instant timestamp,
            final float speed,
            final SpeedUnit unit) {

        super(source, timestamp, speed, unit);
    }

    /**
     * @see com.svhelloworld.knotlog.domain.messages.BaseInstrumentMessage#getDisplayKey()
     */
    @Override
    protected String getDisplayKey() {
        return "display.speed.over.ground";
    }

    /**
     * @see com.svhelloworld.knotlog.domain.messages.BaseInstrumentMessage#getNameKey()
     */
    @Override
    protected String getNameKey() {
        return "name.speed.over.ground";
    }

}
