package com.svhelloworld.knotlog.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.Instant;

import org.junit.Before;
import org.junit.Test;

import com.svhelloworld.knotlog.measure.SpeedUnit;

/**
 * Unit test for <tt>SpeedRelativeToWater</tt> class.
 * 
 * @author Jason Andersen
 * @since Mar 5, 2010
 *
 */
public class SpeedRelativeToWaterTest extends
        BaseQuantitativeMessageTest<SpeedRelativeToWater, SpeedUnit> {

    private static final float SPEED = 18.692f;

    private static final SpeedUnit UNIT = SpeedUnit.KNOTS;

    private SpeedRelativeToWater stw;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        stw = new SpeedRelativeToWater(source, timestamp, SPEED, UNIT);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.SpeedRelativeToWater#SpeedRelativeToWater(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.SpeedUnit)}.
     */
    @Test
    public void testConstructorNegativeSpeed() {
        try {
            @SuppressWarnings("unused")
            SpeedRelativeToWater target = new SpeedRelativeToWater(source, timestamp, -10, UNIT);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.VesselSpeed#getSpeed()}.
     */
    @Test
    public void testGetSpeed() {
        assertEquals(SPEED, stw.getSpeed(), 0.001);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.VesselSpeed#getSpeedUnit()}.
     */
    @Test
    public void testGetSpeedUnit() {
        assertEquals(UNIT, stw.getSpeedUnit());
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getExpectedDisplayString()
     */
    @Override
    protected String getExpectedDisplayString() {
        return "water speed 18.7 kts";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getExpectedName()
     */
    @Override
    protected String getExpectedName() {
        return "water speed";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getInstance(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, com.svhelloworld.knotlog.measure.MeasurementUnit)
     */
    @Override
    protected SpeedRelativeToWater getInstance(VesselMessageSource source, Instant timestamp, SpeedUnit unit) {
        return new SpeedRelativeToWater(source, timestamp, SPEED, unit);
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getMeasurementUnit()
     */
    @Override
    protected SpeedUnit getMeasurementUnit() {
        return UNIT;
    }

}
