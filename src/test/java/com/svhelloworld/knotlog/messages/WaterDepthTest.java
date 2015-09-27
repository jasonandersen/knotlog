package com.svhelloworld.knotlog.messages;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.svhelloworld.knotlog.measure.DistanceUnit;

/**
 * Unit test for <tt>WaterDepth</tt> class.
 * 
 * @author Jason Andersen
 * @since Mar 5, 2010
 *
 */
public class WaterDepthTest extends
        BaseQuantitativeMessageTest<WaterDepth, DistanceUnit> {

    private static final float DEPTH = 349.512f;

    private static final DistanceUnit UNIT = DistanceUnit.FEET;

    private WaterDepth depth;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        depth = new WaterDepth(source, timestamp, DEPTH, UNIT);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.WaterDepth#getDistance()}.
     */
    @Test
    public void testGetDistance() {
        assertEquals(DEPTH, depth.getDistance(), 0.001);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.WaterDepth#getDistanceUnit()}.
     */
    @Test
    public void testGetDistanceUnit() {
        assertEquals(UNIT, depth.getDistanceUnit());
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getExpectedDisplayString()
     */
    @Override
    protected String getExpectedDisplayString() {
        return "water depth 349.5 ft";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getExpectedName()
     */
    @Override
    protected String getExpectedName() {
        return "water depth";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getInstance(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, com.svhelloworld.knotlog.measure.MeasurementUnit)
     */
    @Override
    protected WaterDepth getInstance(VesselMessageSource source, Date timestamp, DistanceUnit unit) {
        return new WaterDepth(source, timestamp, DEPTH, unit);
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getMeasurementUnit()
     */
    @Override
    protected DistanceUnit getMeasurementUnit() {
        return UNIT;
    }

}
