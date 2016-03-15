package com.svhelloworld.knotlog.domain.messages;

import java.time.Instant;

import com.svhelloworld.knotlog.measure.Distance;
import com.svhelloworld.knotlog.measure.DistanceUnit;

/**
 * A message defining the depth of water. Relative position of the 
 * depth of water is dependent on the vessel instrument. Some will 
 * communicate this message as below the transducer, some will 
 * communicate this message as below the waterline.
 * 
 * @author Jason Andersen
 * @since Feb 16, 2010
 *
 */
public class WaterDepth extends BaseQuantitativeMessage<DistanceUnit>implements Distance {

    /**
     * Constructor.
     * @param source message source
     * @param timestamp timestamp of message event
     * @param depth water depth
     * @param unit unit of measure
     * @throws NullPointerException when source is null
     * @throws NullPointerException when timestamp is null
     * @throws NullPointerException when measurement is null
     */
    public WaterDepth(
            final VesselMessageSource source,
            final Instant timestamp,
            final float depth,
            final DistanceUnit unit) {

        super(source, timestamp, depth, unit);
    }

    /**
     * @return water depth
     */
    public float getWaterDepth() {
        return quantity;
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

    /**
     * @see com.svhelloworld.knotlog.domain.messages.BaseInstrumentMessage#getDisplayKey()
     */
    @Override
    protected String getDisplayKey() {
        return "display.water.depth";
    }

    /**
     * @see com.svhelloworld.knotlog.domain.messages.BaseInstrumentMessage#getNameKey()
     */
    @Override
    protected String getNameKey() {
        return "name.water.depth";
    }

}
