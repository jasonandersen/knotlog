package com.svhelloworld.knotlog.messages;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.svhelloworld.knotlog.measure.LatitudinalHemisphere;
import com.svhelloworld.knotlog.measure.LongitudinalHemisphere;

/**
 * Unit test for <tt>GPSPosition</tt> class.
 * 
 * @author Jason Andersen
 * @since Mar 7, 2010
 *
 */
public class GPSPositionTest {
    
    private static final String LATITUDE = "2530.941";
    
    private static final String LONGITUDE = "11103.702";
    
    private static final float EXPECTED_LAT = 25.51569f;
    
    private static final float EXPECTED_LON = 111.06171f;
    
    private static final LatitudinalHemisphere LAT_HEM = LatitudinalHemisphere.NORTH;
    
    private static final LongitudinalHemisphere LON_HEM = LongitudinalHemisphere.WEST;
    
    private static final VesselMessageSource SOURCE = VesselMessageSource.NMEA0183;
    
    private static final Date TIMESTAMP = new Date();
    
    private GPSPosition target;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        target = new GPSPosition(
                SOURCE, TIMESTAMP, LATITUDE, LAT_HEM, LONGITUDE, LON_HEM);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionMessage#getLatitude()}.
     */
    @Test
    public void testGetLatitude() {
        assertEquals(EXPECTED_LAT, target.getLatitude());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionMessage#getLatitudinalHemisphere()}.
     */
    @Test
    public void testGetLatitudeHemisphere() {
        assertEquals(LAT_HEM, target.getLatitudinalHemisphere());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionMessage#getLongitude()}.
     */
    @Test
    public void testGetLongitude() {
        assertEquals(EXPECTED_LON, target.getLongitude());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionMessage#getLongitudinalHemisphere()}.
     */
    @Test
    public void testGetLongitudeHemisphere() {
        assertEquals(LON_HEM, target.getLongitudinalHemisphere());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getSource()}.
     */
    @Test
    public void testGetSource() {
        assertEquals(SOURCE, target.getSource());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getTimestamp()}.
     */
    @Test
    public void testGetTimestamp() {
        assertEquals(TIMESTAMP, target.getTimestamp());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getDisplayMessage()}.
     */
    @Test
    public void testGetDisplayMessage() {
        final String expected = "GPS position 25°30.941'N 111°03.702'W";
        assertEquals(expected, target.getDisplayMessage());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getName()}.
     */
    @Test
    public void testGetName() {
        assertEquals("GPS position", target.getName());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.BaseInstrumentMessage#toString()}.
     */
    @Test
    public void testToString() {
        final String expected = "GPS position 25°30.941'N 111°03.702'W";
        assertEquals(expected, target.toString());
        System.out.println(target);
    }

}
