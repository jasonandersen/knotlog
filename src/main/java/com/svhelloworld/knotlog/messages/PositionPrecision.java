package com.svhelloworld.knotlog.messages;

import java.time.Instant;

import com.svhelloworld.knotlog.measure.DistanceUnit;

/**
 * A message defining the accuracy of a position in the 
 * horizontal plane.
 * 
 * @author Jason Andersen
 * @since Feb 26, 2010
 *
 */
public class PositionPrecision extends BaseQuantitativeMessage<DistanceUnit>
        implements DistanceMessage {

    /**
     * Constructor.
     * @param source message source
     * @param timestamp timestamp of message event
     * @param precision horizontal position precision
     * @param unit unit of measure
     * @throws NullPointerException when source is null
     * @throws NullPointerException when timestamp is null
     * @throws NullPointerException when measurement is null
     * @throws IllegalArgumentException when precision is less than zero
     */
    public PositionPrecision(
            final VesselMessageSource source,
            final Instant timestamp,
            final float precision,
            final DistanceUnit unit) {

        super(source, timestamp, precision, unit);
        if (precision < 0) {
            throw new IllegalArgumentException("precision cannot be less than zero");
        }
    }

    /**
     * @return horizontal precision
     */
    public float getPositionPrecision() {
        return quantity;
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getDisplayKey()
     */
    @Override
    protected String getDisplayKey() {
        return "display.position.precision";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getNameKey()
     */
    @Override
    protected String getNameKey() {
        return "name.position.precision";
    }

    /**
     * @see com.svhelloworld.knotlog.measure.Distance#getDistance()
     */
    @Override
    public float getDistance() {
        return quantity;
    }

    /**
     * @see com.svhelloworld.knotlog.measure.Distance#getDistanceUnit()
     */
    @Override
    public DistanceUnit getDistanceUnit() {
        return unit;
    }

}
