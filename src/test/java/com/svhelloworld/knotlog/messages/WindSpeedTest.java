package com.svhelloworld.knotlog.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.Instant;

import org.junit.Before;
import org.junit.Test;

import com.svhelloworld.knotlog.measure.MeasurementBasis;
import com.svhelloworld.knotlog.measure.SpeedUnit;

/**
 * Unit test for <tt>WindSpeed</tt> class.
 * 
 * @author Jason Andersen
 * @since Mar 6, 2010
 *
 */
public class WindSpeedTest extends BaseQuantitativeMessageTest<WindSpeed, SpeedUnit> {

    private static final float SPEED = 18.419f;

    private static final SpeedUnit UNIT = SpeedUnit.KNOTS;

    private MeasurementBasis BASIS = MeasurementBasis.RELATIVE;

    private WindSpeed speed;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        speed = new WindSpeed(source, timestamp, SPEED, UNIT, BASIS);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.WindSpeed#WindSpeed(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.SpeedUnit, com.svhelloworld.knotlog.measure.MeasurementBasis)}.
     */
    @Test
    public void testConstructorNullBasis() {
        try {
            @SuppressWarnings("unused")
            WindSpeed target = new WindSpeed(source, timestamp, SPEED, UNIT, null);
            fail("expected exception");
        } catch (NullPointerException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.WindSpeed#getWindSpeed()}.
     */
    @Test
    public void testGetWindSpeed() {
        assertEquals(SPEED, speed.getWindSpeed(), 0.001);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.WindSpeed#getBasis()}.
     */
    @Test
    public void testGetBasis() {
        assertEquals(BASIS, speed.getBasis());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.WindSpeed#getSpeed()}.
     */
    @Test
    public void testGetSpeed() {
        assertEquals(SPEED, speed.getSpeed(), 0.001);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.WindSpeed#getSpeedUnit()}.
     */
    @Test
    public void testGetSpeedUnit() {
        assertEquals(UNIT, speed.getMeasurementUnit());
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getExpectedDisplayString()
     */
    @Override
    protected String getExpectedDisplayString() {
        return "relative wind speed 18.4 knots";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getExpectedName()
     */
    @Override
    protected String getExpectedName() {
        return "wind speed";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getInstance(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, com.svhelloworld.knotlog.measure.MeasurementUnit)
     */
    @Override
    protected WindSpeed getInstance(VesselMessageSource source, Instant timestamp, SpeedUnit unit) {
        return new WindSpeed(source, timestamp, SPEED, unit, BASIS);
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getMeasurementUnit()
     */
    @Override
    protected SpeedUnit getMeasurementUnit() {
        return UNIT;
    }

}
