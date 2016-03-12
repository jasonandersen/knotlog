package com.svhelloworld.knotlog.messages;

import java.time.Instant;

import com.svhelloworld.knotlog.measure.SpeedUnit;

/**
 * A message communicating speed of the vessel. Concrete
 * classes represent the vessel speed relative to the
 * earth or relative to the medium the vessel is traveling
 * through (water or air).
 * 
 * @author Jason Andersen
 * @since Feb 26, 2010
 *
 */
public abstract class VesselSpeed
        extends BaseQuantitativeMessage<SpeedUnit> implements SpeedMessage {

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
    public VesselSpeed(
            VesselMessageSource source,
            Instant timestamp,
            float speed,
            SpeedUnit unit) {

        super(source, timestamp, speed, unit);
        if (speed < 0) {
            throw new IllegalArgumentException("speed cannot be less than zero");
        }
    }

    /**
     * @see com.svhelloworld.knotlog.measure.Speed#getSpeed()
     */
    @Override
    public float getSpeed() {
        return quantity;
    }

    /**
     * @see com.svhelloworld.knotlog.measure.Speed#getSpeedUnit()
     */
    @Override
    public SpeedUnit getSpeedUnit() {
        return unit;
    }

}
