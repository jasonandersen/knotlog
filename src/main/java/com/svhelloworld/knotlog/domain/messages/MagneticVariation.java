package com.svhelloworld.knotlog.domain.messages;

import java.time.Instant;
import java.util.List;

import com.svhelloworld.knotlog.measure.AngleUnit;
import com.svhelloworld.knotlog.measure.LongitudinalHemisphere;
import com.svhelloworld.knotlog.util.MiscUtil;

/**
 * A message indicating the direction and amount in degrees of 
 * variation between magnetic north and true north at this location.
 * 
 * @author Jason Andersen
 * @since Mar 6, 2010
 *
 */
public class MagneticVariation extends BaseQuantitativeMessage<AngleUnit> {

    /**
     * hemisphere to indicate direction of magnetic variation
     */
    private final LongitudinalHemisphere hemisphere;

    /**
     * Constructor.
     * @param source message source
     * @param timestamp timestamp of message event
     * @param variation magnetic variation
     * @param hemisphere direction of magnetic variation
     * @throws NullPointerException when source is null
     * @throws NullPointerException when timestamp is null
     * @throws NullPointerException when hemisphere is null
     * @throws IllegalArgumentException when variation &lt; 0 or &gt; 90
     */
    public MagneticVariation(
            final VesselMessageSource source,
            final Instant timestamp,
            final float variation,
            final LongitudinalHemisphere hemisphere) {

        super(source, timestamp, variation, AngleUnit.DEGREES);

        //ensure variation is correct
        if (variation < 0 || variation > 90) {
            throw new IllegalArgumentException(
                    "variation cannot be less than zero or greater than 90: " + variation);
        }
        //ensure hemisphere is east or west
        if (hemisphere == null) {
            throw new NullPointerException("hemisphere cannot be null");
        }
        this.hemisphere = hemisphere;
    }

    /**
     * @return magnetic variation in degrees
     */
    public float getMagneticVariation() {
        return quantity;
    }

    /**
     * @return the direction of the magnetic variation
     */
    public LongitudinalHemisphere getMagneticVariationDirection() {
        return hemisphere;
    }

    /**
     * @see com.svhelloworld.knotlog.domain.messages.BaseInstrumentMessage#getDisplayKey()
     */
    @Override
    protected String getDisplayKey() {
        return "display.magnetic.variation";
    }

    /**
     * @see com.svhelloworld.knotlog.domain.messages.BaseInstrumentMessage#getNameKey()
     */
    @Override
    protected String getNameKey() {
        return "name.magnetic.variation";
    }

    /**
     * @see com.svhelloworld.knotlog.i18n.Localizable#getLocalizeParams()
     */
    @Override
    public List<Object> getLocalizeParams() {
        return MiscUtil.varargsToList(quantity, unit.getSuffix(), hemisphere.getSuffix());
    }

}
