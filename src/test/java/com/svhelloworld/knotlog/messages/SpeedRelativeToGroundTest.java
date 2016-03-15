package com.svhelloworld.knotlog.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.Instant;

import org.junit.Before;
import org.junit.Test;

import com.svhelloworld.knotlog.domain.messages.SpeedRelativeToGround;
import com.svhelloworld.knotlog.domain.messages.VesselMessageSource;
import com.svhelloworld.knotlog.measure.SpeedUnit;

/**
 * Unit test for <tt>SpeedRelativeToGround</tt> class.
 * 
 * @author Jason Andersen
 * @since Mar 5, 2010
 *
 */
public class SpeedRelativeToGroundTest extends
        BaseQuantitativeMessageTest<SpeedRelativeToGround, SpeedUnit> {

    private static final float SPEED = 18.692f;

    private static final SpeedUnit UNIT = SpeedUnit.KNOTS;

    private SpeedRelativeToGround sog;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        sog = new SpeedRelativeToGround(source, timestamp, SPEED, UNIT);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.SpeedRelativeToGround#SpeedRelativeToGround(com.svhelloworld.knotlog.domain.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.SpeedUnit)}.
     */
    @Test
    public void testConstructorNegativeSpeed() {
        try {
            @SuppressWarnings("unused")
            SpeedRelativeToGround target = new SpeedRelativeToGround(source, timestamp, -10, UNIT);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.VesselSpeed#getSpeed()}.
     */
    @Test
    public void testGetSpeed() {
        assertEquals(SPEED, sog.getSpeed(), 0.001);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.VesselSpeed#getSpeedUnit()}.
     */
    @Test
    public void testGetSpeedUnit() {
        assertEquals(UNIT, sog.getSpeedUnit());
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getExpectedDisplayString()
     */
    @Override
    protected String getExpectedDisplayString() {
        return "SOG 18.7 kts";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getExpectedName()
     */
    @Override
    protected String getExpectedName() {
        return "speed over ground";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getInstance(com.svhelloworld.knotlog.domain.messages.VesselMessageSource, java.util.Date, com.svhelloworld.knotlog.measure.MeasurementUnit)
     */
    @Override
    protected SpeedRelativeToGround getInstance(VesselMessageSource source, Instant timestamp, SpeedUnit unit) {
        return new SpeedRelativeToGround(source, timestamp, SPEED, unit);
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getMeasurementUnit()
     */
    @Override
    protected SpeedUnit getMeasurementUnit() {
        return UNIT;
    }

}
