package com.svhelloworld.knotlog.messages;

import static org.junit.Assert.assertEquals;

import java.time.Instant;

import org.junit.Before;
import org.junit.Test;

import com.svhelloworld.knotlog.domain.messages.SeaTemperature;
import com.svhelloworld.knotlog.domain.messages.VesselMessageSource;
import com.svhelloworld.knotlog.measure.TemperatureUnit;

/**
 * Unit test <tt>SeaTemperature</tt>
 * 
 * @author Jason Andersen
 * @since Mar 6, 2010
 *
 */
public class SeaTemperatureTest extends
        BaseQuantitativeMessageTest<SeaTemperature, TemperatureUnit> {

    private static final float TEMP = 72.48f;

    private static final TemperatureUnit UNIT = TemperatureUnit.FAHRENHEIT;

    private SeaTemperature temp;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        temp = new SeaTemperature(source, timestamp, TEMP, UNIT);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.Temperature#getTemperature()}.
     */
    @Test
    public void testGetTemperature() {
        assertEquals(TEMP, temp.getTemperature(), 0.001);
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getExpectedDisplayString()
     */
    @Override
    protected String getExpectedDisplayString() {
        return "sea temperature 72.5°F";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getExpectedName()
     */
    @Override
    protected String getExpectedName() {
        return "sea temperature";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getInstance(com.svhelloworld.knotlog.domain.messages.VesselMessageSource, java.util.Date, com.svhelloworld.knotlog.measure.MeasurementUnit)
     */
    @Override
    protected SeaTemperature getInstance(VesselMessageSource source, Instant timestamp, TemperatureUnit unit) {
        return new SeaTemperature(source, timestamp, TEMP, unit);
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getMeasurementUnit()
     */
    @Override
    protected TemperatureUnit getMeasurementUnit() {
        return UNIT;
    }

}
