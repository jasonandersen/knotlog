package com.svhelloworld.knotlog.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.svhelloworld.knotlog.measure.DistanceUnit;

/**
 * Unit test for <tt>PositionPrecision</tt> class.
 * 
 * @author Jason Andersen
 * @since Mar 5, 2010
 *
 */
public class PositionPrecisionTest extends
        BaseQuantitativeMessageTest<PositionPrecision, DistanceUnit> {

    private static final float PRECISION = 18.215f;

    private static final DistanceUnit UNIT = DistanceUnit.FEET;

    private PositionPrecision precision;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        precision = new PositionPrecision(super.source, super.timestamp, PRECISION, UNIT);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionPrecision#HorizontalPrecision(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.DistanceUnit)}.
     */
    @Test
    public void testConstructorNegativePrecision() {
        try {
            @SuppressWarnings("unused")
            PositionPrecision test = new PositionPrecision(source, timestamp, -18.3f, UNIT);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            //expected
        }

    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionPrecision#getPositionPrecision()}.
     */
    @Test
    public void testGetHorizontalPrecision() {
        assertEquals(PRECISION, precision.getPositionPrecision(), 0.001);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionPrecision#getDistance()}.
     */
    @Test
    public void testGetDistance() {
        assertEquals(PRECISION, precision.getDistance(), 0.001);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionPrecision#getDistanceUnit()}.
     */
    @Test
    public void testGetDistanceUnit() {
        assertEquals(UNIT, precision.getDistanceUnit());
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getExpectedDisplayString()
     */
    @Override
    protected String getExpectedDisplayString() {
        return "position precision 18.2 ft";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getExpectedName()
     */
    @Override
    protected String getExpectedName() {
        return "position precision";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getInstance(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, com.svhelloworld.knotlog.measure.MeasurementUnit)
     */
    @Override
    protected PositionPrecision getInstance(
            VesselMessageSource source, Date timestamp, DistanceUnit unit) {
        return new PositionPrecision(source, timestamp, PRECISION, unit);
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getMeasurementUnit()
     */
    @Override
    protected DistanceUnit getMeasurementUnit() {
        return UNIT;
    }

}
