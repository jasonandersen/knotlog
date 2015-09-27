package com.svhelloworld.knotlog.messages;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.svhelloworld.knotlog.i18n.BabelFish;
import com.svhelloworld.knotlog.measure.MeasurementUnit;

/**
 * Unit test for <tt>BaseQuantitativeMessage</tt> class.
 * 
 * @author Jason Andersen
 * @param <T> message type 
 * @param <M> measurement unit
 * @since Mar 4, 2010
 *
 */
@SuppressWarnings("unchecked")
public abstract class BaseQuantitativeMessageTest
        <T extends BaseQuantitativeMessage, M extends MeasurementUnit> {
    
    private T target;
    
    /**
     * message source
     */
    protected VesselMessageSource source;
    
    /**
     * timestamp
     */
    protected Date timestamp;
    
    /**
     * measurement unit
     */
    protected M unit;
    
    /**
     * Sets up the base testing class
     */
    @Before
    public void setupBaseTestClass() {
        source = VesselMessageSource.NMEA0183;
        timestamp = new Date();
        unit = getMeasurementUnit();
        target = getInstance(source, timestamp, unit);
    }
    
    /**
     * @param source 
     * @param timestamp 
     * @param unit 
     * @return an instance of message being tested.
     */
    protected abstract T getInstance(
            VesselMessageSource source,
            Date timestamp,
            M unit);
    
    /**
     * @return a measurement unit enum object
     */
    protected abstract M getMeasurementUnit();
    
    /**
     * @return the expected display string
     */
    protected abstract String getExpectedDisplayString();
    
    /**
     * @return the expected name string
     */
    protected abstract String getExpectedName();
    
    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseQuantitativeMessage#BaseQuantitativeMessage(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.MeasurementUnit)}.
     */
    @Test
    public void testConstructorSourceNull() {
        try {
            @SuppressWarnings("unused")
            T target = getInstance(null, timestamp, unit);
            fail("exception expected");
        } catch (NullPointerException e) {
            //expected
        }
    }
    
    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseQuantitativeMessage#BaseQuantitativeMessage(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.MeasurementUnit)}.
     */
    @Test
    public void testConstructorTimestampNull() {
        try {
            @SuppressWarnings("unused")
            T target = getInstance(source, null, unit);
            fail("exception expected");
        } catch (NullPointerException e) {
            //expected
        }
    }
    
    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseQuantitativeMessage#BaseQuantitativeMessage(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.MeasurementUnit)}.
     */
    @Test
    public void testConstructorUnitNull() {
        try {
            @SuppressWarnings("unused")
            T target = getInstance(source, timestamp, null);
            fail("exception expected");
        } catch (NullPointerException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseQuantitativeMessage#getMeasurementUnit()}.
     */
    @Test
    public void testGetMeasurementUnit() {
        assertNotNull(target.getMeasurementUnit());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseQuantitativeMessage#getQuantity()}.
     */
    @Test
    public void testGetQuantity() {
        assertNotNull(target.getQuantity());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getSource()}.
     */
    @Test
    public void testGetSource() {
        assertNotNull(target.getSource());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getTimestamp()}.
     */
    @Test
    public void testGetTimestamp() {
        assertNotNull(target.getTimestamp());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getDisplayMessage()}.
     */
    @Test
    public void testGetDisplayMessage() {
        assertEquals(getExpectedDisplayString(), target.getDisplayMessage());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getName()}.
     */
    @Test
    public void testGetName() {
        assertEquals(getExpectedName(), target.getName());
    }
    
    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#toString()}.
     */
    @Test
    public void testToString() {
        assertEquals(getExpectedDisplayString(), target.toString());
        System.out.println(target.toString());
    }

    /**
     * Test method for {@link java.lang.Object#toString()}.
     */
    @Test
    public void testLocalize() {
        assertEquals(getExpectedDisplayString(), BabelFish.localize(target));
    }

}
