package com.svhelloworld.knotlog.messages;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Date;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit test for <tt>TimeOfDayZulu</tt> class.
 * 
 * @author Jason Andersen
 * @since Mar 7, 2010
 *
 */
public class TimeOfDayZuluTest {

    private static final String EXPECTED_DISPLAY = "time 19:56:02UTC";

    private VesselMessageSource source = VesselMessageSource.NMEA0183;

    private Date timestamp = new Date();

    private TimeOfDayZulu target;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        target = new TimeOfDayZulu(source, timestamp, "195602.39");
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.TimeOfDayZulu#TimeOfDayZulu(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, java.lang.String)}.
     * @throws ParseException 
     */
    @Test
    public void testTimeOfDayZulu() throws ParseException {
        String time = "083422.12";
        target = new TimeOfDayZulu(source, timestamp, time);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getTimeOfDay()}.
     */
    @Test
    public void testGetTimeOfDay() {
        long expected = (long) (19 * 60 * 60 * 100 + 56 * 60 * 100 + 02.39 * 100);
        assertEquals(expected, target.getTimeMilliseconds());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getSource()}.
     */
    @Test
    public void testGetSource() {
        assertEquals(target.getSource(), source);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getTimestamp()}.
     */
    @Test
    public void testGetTimestamp() {
        assertEquals(target.getTimestamp(), timestamp);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getName()}.
     */
    @Test
    public void testGetName() {
        assertEquals("time", target.getName());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getLocalizeKey()}.
     */
    @Ignore //FIXME - there is a straight up defect in the time zone handling here, need to change to JDK8 time API
    @Test
    public void testGetDisplayMessage() {
        assertEquals(EXPECTED_DISPLAY, target.getDisplayMessage());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#toString()}.
     */
    @Ignore //FIXME - there is a straight up defect in the time zone handling here, need to change to JDK8 time API
    @Test
    public void testToString() {
        assertEquals(EXPECTED_DISPLAY, target.toString());
    }

}
