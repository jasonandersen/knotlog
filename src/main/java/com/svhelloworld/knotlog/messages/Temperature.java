package com.svhelloworld.knotlog.messages;

import java.time.Instant;

import com.svhelloworld.knotlog.measure.TemperatureUnit;

/**
 * A message indicating temperature.
 * 
 * @author Jason Andersen
 * @since Mar 6, 2010
 *
 */
public abstract class Temperature extends BaseQuantitativeMessage<TemperatureUnit> {

    /**
     * Constructor.
     * @param source message source
     * @param timestamp timestamp of message event
     * @param temperature temperature measurement
     * @param unit unit of measure
     * @throws NullPointerException when source is null
     * @throws NullPointerException when timestamp is null
     * @throws NullPointerException when unit is null
     */
    public Temperature(
            final VesselMessageSource source,
            final Instant timestamp,
            final float temperature,
            final TemperatureUnit unit) {

        super(source, timestamp, temperature, unit);
    }

    /**
     * @return water temperature
     */
    public float getTemperature() {
        return quantity;
    }

}
