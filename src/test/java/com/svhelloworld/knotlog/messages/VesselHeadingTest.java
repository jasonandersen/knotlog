package com.svhelloworld.knotlog.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.svhelloworld.knotlog.measure.AngleUnit;

/**
 * 
 * @author Jason Andersen
 * @since Mar 6, 2010
 *
 */
public class VesselHeadingTest
        extends BaseQuantitativeMessageTest<VesselHeading, AngleUnit> {

    private static final float HEADING = 192.502f;

    private static final AngleUnit UNIT = AngleUnit.DEGREES_MAGNETIC;

    private VesselHeading heading;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        heading = new VesselHeading(source, timestamp, HEADING, UNIT);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.VesselHeading#VesselHeading(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.AngleUnit)}.
     */
    @Test
    public void testConstructorHeadingLessThan0() {
        try {
            heading = new VesselHeading(source, timestamp, -1, UNIT);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.VesselHeading#VesselHeading(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.AngleUnit)}.
     */
    @Test
    public void testConstructorHeadingGreaterThan360() {
        try {
            heading = new VesselHeading(source, timestamp, 361, UNIT);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.VesselHeading#VesselHeading(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.AngleUnit)}.
     */
    @Test
    public void testConstructorUnitEqualsDEGREES() {
        try {
            heading = new VesselHeading(source, timestamp, HEADING, AngleUnit.DEGREES);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.VesselHeading#getVesselHeading()}.
     */
    @Test
    public void testGetVesselHeading() {
        assertEquals(HEADING, heading.getVesselHeading(), 0.001);
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getExpectedDisplayString()
     */
    @Override
    protected String getExpectedDisplayString() {
        return "heading 193°M";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getExpectedName()
     */
    @Override
    protected String getExpectedName() {
        return "heading";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getInstance(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, com.svhelloworld.knotlog.measure.MeasurementUnit)
     */
    @Override
    protected VesselHeading getInstance(VesselMessageSource source, Date timestamp, AngleUnit unit) {
        return new VesselHeading(source, timestamp, HEADING, unit);
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getMeasurementUnit()
     */
    @Override
    protected AngleUnit getMeasurementUnit() {
        return UNIT;
    }

}
