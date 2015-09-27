package com.svhelloworld.knotlog.messages;

import java.util.Date;

import com.svhelloworld.knotlog.measure.Distance;
import com.svhelloworld.knotlog.measure.DistanceUnit;

/**
 * A message defining altitude above sea level.
 * 
 * @author Jason Andersen
 * @since Feb 24, 2010
 *
 */
public class Altitude extends BaseQuantitativeMessage<DistanceUnit> implements Distance {
    
    /**
     * Constructor.
     * @param source message source
     * @param timestamp timestamp of message event
     * @param altitude altitude communicated by message
     * @param unit unit of measure
     * @throws NullPointerException when source is null
     * @throws NullPointerException when timestamp is null
     * @throws NullPointerException when measurement is null
     */
    public Altitude(
            final VesselMessageSource source, 
            final Date timestamp, 
            final float altitude,
            final DistanceUnit unit) {
        
        super(source, timestamp, altitude, unit);
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
     * @return altitude above sea level.
     */
    public float getAltitude() {
        return quantity;
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getDisplayKey()
     */
    @Override
    protected String getDisplayKey() {
        return "display.altitude";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getNameKey()
     */
    @Override
    protected String getNameKey() {
        return "name.altitude";
    }



}
