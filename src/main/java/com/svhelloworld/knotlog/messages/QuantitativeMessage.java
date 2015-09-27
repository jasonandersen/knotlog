package com.svhelloworld.knotlog.messages;

import com.svhelloworld.knotlog.measure.MeasurementUnit;

/**
 * An instrument message communicating a numeric quantity.
 * 
 * @author Jason Andersen
 * @since Feb 16, 2010
 *
 */
public interface QuantitativeMessage extends VesselMessage {
    /**
     * @return Quantity indicated in the message
     */
    public Number getQuantity();
    /**
     * @return The unit of measure of the quantity
     */
    public MeasurementUnit getMeasurementUnit();
}
