package com.svhelloworld.knotlog.messages;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for <tt>TimeOfDayZulu</tt> class.
 * 
 * @author Jason Andersen
 * @since Mar 7, 2010
 *
 */
public class TimeOfDayZuluTest {

    private static final String EXPECTED_DISPLAY = "time 2015-10-10T19:56:02Z";

    private VesselMessageSource source = VesselMessageSource.NMEA0183;

    private Date timestamp = new Date();

    private TimeOfDayZulu message;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        message = new TimeOfDayZulu(source, timestamp, "195602.39");
        System.out.println(message.getDate());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.TimeOfDayZulu#TimeOfDayZulu(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, java.lang.String)}.
     * @throws ParseException 
     */
    @Test
    public void testTimeOfDayZulu() throws ParseException {
        String time = "083422.12";
        message = new TimeOfDayZulu(source, timestamp, time);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getTimeOfDay()}.
     */
    @Test
    public void testGetTimeOfDay() {
        String expectedTimeOfDay = "19:56:02";
        assertEquals(expectedTimeOfDay, message.getTimeOfDay());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getSource()}.
     */
    @Test
    public void testGetSource() {
        assertEquals(message.getSource(), source);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getTimestamp()}.
     */
    @Test
    public void testGetTimestamp() {
        assertEquals(message.getTimestamp(), timestamp);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getName()}.
     */
    @Test
    public void testGetName() {
        assertEquals("time", message.getName());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getLocalizeKey()}.
     */
    @Test
    public void testGetDisplayMessage() {
        assertEquals(EXPECTED_DISPLAY, message.getDisplayMessage());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#toString()}.
     */
    @Test
    public void testToString() {
        assertEquals(EXPECTED_DISPLAY, message.toString());
    }

}
