package com.svhelloworld.knotlog.messages;

import static org.junit.Assert.assertEquals;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

    private String expectedDisplay;

    private VesselMessageSource source = VesselMessageSource.NMEA0183;

    private Date timestamp = new Date();

    private TimeOfDay message;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.of("GMT"));
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        expectedDisplay = String.format("time %sT19:56:02Z", formatter.format(dateTime));
        message = new TimeOfDay(source, timestamp, "195602.39");
        System.out.println(message.getDate());
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
        assertEquals(expectedDisplay, message.getDisplayMessage());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#toString()}.
     */
    @Test
    public void testToString() {
        assertEquals(expectedDisplay, message.toString());
    }

}
