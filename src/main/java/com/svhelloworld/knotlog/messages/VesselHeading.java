package com.svhelloworld.knotlog.messages;

import java.time.Instant;

import com.svhelloworld.knotlog.measure.AngleUnit;

/**
 * A message defining the direction the vessel is pointing.
 * 
 * @author Jason Andersen
 * @since Mar 6, 2010
 *
 */
public class VesselHeading extends BaseQuantitativeMessage<AngleUnit> {

    /**
     * Constructor.
     * @param source message source
     * @param timestamp timestamp of message event
     * @param heading vessel heading
     * @param unit unit of measure
     * @throws NullPointerException when source is null
     * @throws NullPointerException when timestamp is null
     * @throws NullPointerException when measurement is null
     * @throws IllegalArgumentException when heading &lt; 0 or heading &gt; 360
     * @throws IllegalArgumentException when unit is not DEGREES_TRUE 
     *          or DEGREES_MAGNETIC
     */
    public VesselHeading(
            final VesselMessageSource source,
            final Instant timestamp,
            final float heading,
            final AngleUnit unit) {

        super(source, timestamp, heading, unit);
        //make sure the heading is valid
        if (heading < 0 || heading > 360) {
            throw new IllegalArgumentException("illegal heading argument: " + heading);
        }
        //make sure we are measuring either magnetic or true
        if (!(unit.equals(AngleUnit.DEGREES_MAGNETIC) || unit.equals(AngleUnit.DEGREES_TRUE))) {
            throw new IllegalArgumentException("illegal measurement unit: " + unit);
        }
    }

    /**
     * @return vessel heading
     */
    public float getVesselHeading() {
        return quantity;
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getDisplayKey()
     */
    @Override
    protected String getDisplayKey() {
        return "display.vessel.heading";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getNameKey()
     */
    @Override
    protected String getNameKey() {
        return "name.vessel.heading";
    }

}
