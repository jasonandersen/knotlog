package com.svhelloworld.knotlog.messages;

import static org.junit.Assert.*;

import java.util.Date;

import com.svhelloworld.knotlog.measure.DistanceUnit;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for <tt>Altitude</tt> class.
 * 
 * @author Jason Andersen 
 * @since Mar 4, 2010
 *
 */
public class AltitudeTest extends BaseQuantitativeMessageTest<Altitude, DistanceUnit> {
    
    private static final float ALTITUDE = 3715.693f;
    
    private static final DistanceUnit UNIT = DistanceUnit.FEET;
    
    private Altitude altitude;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        altitude = new Altitude(super.source, super.timestamp, ALTITUDE, UNIT);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.Altitude#getDistance()}.
     */
    @Test
    public void testGetDistance() {
        assertEquals(ALTITUDE, altitude.getDistance());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.Altitude#getDistanceUnit()}.
     */
    @Test
    public void testGetDistanceUnit() {
        assertEquals(UNIT, altitude.getDistanceUnit());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.Altitude#getAltitude()}.
     */
    @Test
    public void testGetAltitude() {
        assertEquals(ALTITUDE, altitude.getAltitude());
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getExpectedDisplayString()
     */
    @Override
    protected String getExpectedDisplayString() {
        return "altitude 3716 ft";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getExpectedName()
     */
    @Override
    protected String getExpectedName() {
        return "altitude";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getInstance(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, com.svhelloworld.knotlog.measure.MeasurementUnit)
     */
    @Override
    protected Altitude getInstance(VesselMessageSource source, Date timestamp, DistanceUnit unit) {
        return new Altitude(source, timestamp, ALTITUDE, unit);
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getMeasurementUnit()
     */
    @Override
    protected DistanceUnit getMeasurementUnit() {
        return UNIT;
    }

}
