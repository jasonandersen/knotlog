package com.svhelloworld.knotlog.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.svhelloworld.knotlog.measure.VesselArea;

/**
 * Unit test for <tt>RudderAngle</tt> class.
 * 
 * @author Jason Andersen
 * @since Mar 6, 2010
 *
 */
public class RudderAngleTest extends
        BaseQuantitativeMessageTest<RudderAngle, VesselArea> {

    private final static float ANGLE = 12.2f;

    private final static VesselArea SIDE = VesselArea.STARBOARD;

    private RudderAngle angle;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        angle = new RudderAngle(source, timestamp, ANGLE, SIDE);
    }

    /*
     * constructor tests
     * angle < 0
     * angle > 180
     * side = null
     */

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.RudderAngle#RudderAngle(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.VesselArea)}.
     */
    @Test
    public void testConstructorAngleLess180() {
        try {
            angle = new RudderAngle(source, timestamp, -181, SIDE);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.RudderAngle#RudderAngle(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.VesselArea)}.
     */
    @Test
    public void testConstructorAngleGreaterThan180() {
        try {
            angle = new RudderAngle(source, timestamp, 181, SIDE);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.RudderAngle#RudderAngle(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.VesselArea)}.
     */
    @Test
    public void testConstructorVesselSideNull() {
        angle = new RudderAngle(source, timestamp, ANGLE, null);
        //make sure a null vessel side is assumed to be starboard.
        assertEquals(VesselArea.STARBOARD, angle.getRudderVesselSide());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.RudderAngle#getRudderAngle()}.
     */
    @Test
    public void testGetRudderAngle() {
        assertEquals(ANGLE, angle.getRudderAngle(), 0.1);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.RudderAngle#getRudderVesselSide()}.
     */
    @Test
    public void testGetRudderVesselSide() {
        assertEquals(SIDE, angle.getRudderVesselSide());
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getExpectedDisplayString()
     */
    @Override
    protected String getExpectedDisplayString() {
        return "starboard rudder angle 12°";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getExpectedName()
     */
    @Override
    protected String getExpectedName() {
        return "rudder angle";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getInstance(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, com.svhelloworld.knotlog.measure.MeasurementUnit)
     */
    @Override
    protected RudderAngle getInstance(VesselMessageSource source, Date timestamp, VesselArea unit) {
        /*
         * a little hack here, the BaseQuantitativeMessageTest class
         * expects a null pointer exception when the unit object is null.
         * 95% of the time that's true, but not this time.
         */
        if (unit == null) {
            throw new NullPointerException("hacking around funkiness in the BaseQuantitativeMessageTest class");
        }
        return new RudderAngle(source, timestamp, ANGLE, unit);
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getMeasurementUnit()
     */
    @Override
    protected VesselArea getMeasurementUnit() {
        return SIDE;
    }

}
