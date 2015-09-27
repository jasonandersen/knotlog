package com.svhelloworld.knotlog.messages;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for <tt>DateZulu</tt> class.
 * 
 * @author Jason Andersen
 * @since Mar 8, 2010
 *
 */
public class DateZuluTest {
    
    private static final String date = "190510";
    
    private static final Date timestamp = new Date();
    
    private static final VesselMessageSource source = VesselMessageSource.NMEA0183;
    
    private static final String EXPECTED_DISPLAY = "date 5/19/10";
    
    private long expected;
    
    private DateZulu target;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        target = new DateZulu(source, timestamp, date);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(2010, 4, 19); //month is zero-based index
        expected = calendar.getTimeInMillis();
    }
    
    
    /*
     * constructor tests
     * date null
     * date malformed
     */
    
    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.DateZulu#DateZulu(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, java.lang.String)}.
     */
    @Test
    public void testConstructorNullDate() {
        try {
            target = new DateZulu(source, timestamp, null);
        } catch (NullPointerException e) {
            //expected
        }
    }
    
    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.DateZulu#DateZulu(com.svhelloworld.knotlog.messages.VesselMessageSource, java.util.Date, java.lang.String)}.
     */
    @Test
    public void testConstructorMalformedDate() {
        try {
            target = new DateZulu(source, timestamp, "400510");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.DateZulu#getDateMilliseconds()}.
     */
    @Test
    public void testGetDateMilliseconds() {
        assertEquals(expected, target.getDateMilliseconds());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getSource()}.
     */
    @Test
    public void testGetSource() {
        assertEquals(source, target.getSource());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getTimestamp()}.
     */
    @Test
    public void testGetTimestamp() {
        assertEquals(timestamp, target.getTimestamp());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getDisplayMessage()}.
     */
    @Test
    public void testGetDisplayMessage() {
        assertEquals(EXPECTED_DISPLAY, target.getDisplayMessage());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getName()}.
     */
    @Test
    public void testGetName() {
        assertEquals("date", target.getName());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getLocalizeKey()}.
     */
    @Test
    public void testGetLocalizeKey() {
        assertEquals(EXPECTED_DISPLAY, target.getDisplayMessage());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#toString()}.
     */
    @Test
    public void testToString() {
        assertEquals(EXPECTED_DISPLAY, target.getDisplayMessage());
    }

}
