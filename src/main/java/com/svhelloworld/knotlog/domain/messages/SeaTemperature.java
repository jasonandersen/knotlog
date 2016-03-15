package com.svhelloworld.knotlog.domain.messages;

import java.time.Instant;

import com.svhelloworld.knotlog.measure.TemperatureUnit;

/**
 * A message indicating temperature of the surrounding sea water.
 * 
 * @author Jason Andersen
 * @since Mar 6, 2010
 *
 */
public class SeaTemperature extends Temperature {

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
    public SeaTemperature(
            final VesselMessageSource source,
            final Instant timestamp,
            final float temperature,
            final TemperatureUnit unit) {

        super(source, timestamp, temperature, unit);
    }

    /**
     * @see com.svhelloworld.knotlog.domain.messages.BaseInstrumentMessage#getDisplayKey()
     */
    @Override
    protected String getDisplayKey() {
        return "display.sea.temperature";
    }

    /**
     * @see com.svhelloworld.knotlog.domain.messages.BaseInstrumentMessage#getNameKey()
     */
    @Override
    protected String getNameKey() {
        return "name.sea.temperature";
    }

}
