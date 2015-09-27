package com.svhelloworld.knotlog.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.svhelloworld.knotlog.measure.AngleUnit;
import com.svhelloworld.knotlog.measure.LongitudinalHemisphere;

/**
 * Unit test for <tt>MagneticVariation</tt> class.
 * 
 * @author Jason Andersen
 * @since Mar 6, 2010
 *
 */
public class MagneticVariationTest extends
        BaseQuantitativeMessageTest<MagneticVariation, AngleUnit> {

    private static final float VARIATION = 10.123f;

    private static final LongitudinalHemisphere HEMISPHERE = LongitudinalHemisphere.EAST;

    private MagneticVariation variation;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        variation = new MagneticVariation(source, timestamp, VARIATION, HEMISPHERE);
    }

    /*
     * constructor tests:
     * var < 0
     * var > 90
     * hem = NORTH
     * hem = SOUTH
     * hem = null
     */

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.MagneticVariation#MagneticVariation(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.LatitudinalHemisphere)}.
     */
    @Test
    public void testConstructorVariationLessThan0() {
        try {
            variation = new MagneticVariation(source, timestamp, -1, HEMISPHERE);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.MagneticVariation#MagneticVariation(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.LatitudinalHemisphere)}.
     */
    @Test
    public void testConstructorVariationGreaterThan90() {
        try {
            variation = new MagneticVariation(source, timestamp, 91, HEMISPHERE);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.MagneticVariation#MagneticVariation(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.LatitudinalHemisphere)}.
     */
    @Test
    public void testConstructorVariationHemNull() {
        try {
            variation = new MagneticVariation(source, timestamp, VARIATION, null);
            fail("expected exception");
        } catch (NullPointerException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.MagneticVariation#getMagneticVariation()}.
     */
    @Test
    public void testGetMagneticVariation() {
        assertEquals(VARIATION, variation.getMagneticVariation(), 0.001);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.MagneticVariation#getMagneticVariationDirection()}.
     */
    @Test
    public void testGetMagneticVariationDirection() {
        assertEquals(HEMISPHERE, variation.getMagneticVariationDirection());
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getExpectedDisplayString()
     */
    @Override
    protected String getExpectedDisplayString() {
        return "magnetic variation 10.1°E";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getExpectedName()
     */
    @Override
    protected String getExpectedName() {
        return "magnetic variation";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getInstance(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, com.svhelloworld.knotlog.measure.MeasurementUnit)
     */
    @Override
    protected MagneticVariation getInstance(VesselMessageSource source, Date timestamp, AngleUnit unit) {
        /*
         * a little hack here, the BaseQuantitativeMessageTest class
         * expects a null pointer exception when the unit object is null.
         * 95% of the time, that's true, not this time.
         */
        if (unit == null) {
            throw new NullPointerException("hacking around funkiness in the BaseQuantitativeMessageTest class");
        }
        return new MagneticVariation(source, timestamp, VARIATION, HEMISPHERE);
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getMeasurementUnit()
     */
    @Override
    protected AngleUnit getMeasurementUnit() {
        return AngleUnit.DEGREES;
    }

}
