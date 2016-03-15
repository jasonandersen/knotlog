package com.svhelloworld.knotlog.domain.messages;

import com.svhelloworld.knotlog.measure.MeasurementUnit;

/**
 * An instrument message communicating a numeric quantity.
 * 
 * @author Jason Andersen
 * @since Feb 16, 2010
 *
 */
public interface QuantitativeMessage extends ValidVesselMessage {
    /**
     * @return Quantity indicated in the message
     */
    public Number getQuantity();

    /**
     * @return The unit of measure of the quantity
     */
    public MeasurementUnit getMeasurementUnit();
}
