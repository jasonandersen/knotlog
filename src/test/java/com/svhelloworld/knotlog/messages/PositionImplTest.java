package com.svhelloworld.knotlog.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.svhelloworld.knotlog.measure.LatitudinalHemisphere;
import com.svhelloworld.knotlog.measure.LongitudinalHemisphere;

/**
 * Unit test for <tt>PositionImpl</tt> class.
 * 
 * @author Jason Andersen
 * @since Mar 6, 2010
 *
 */
public class PositionImplTest {

    private static final String STRING_LAT = "2530.941";

    private static final String STRING_LON = "11103.702";

    private static final float NUMERIC_LAT = 25.51569f;

    private static final float NUMERIC_LON = 111.06171f;

    private static final LatitudinalHemisphere LAT_HEM = LatitudinalHemisphere.NORTH;

    private static final LongitudinalHemisphere LON_HEM = LongitudinalHemisphere.WEST;

    private PositionImpl target;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        target = new PositionImpl(STRING_LAT, LAT_HEM, STRING_LON, LON_HEM);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionImpl#PositionImpl(float, float)}.
     */
    @Test
    public void testConstructorFloatHemFloatHemNullLatHem() {
        try {
            target = new PositionImpl(NUMERIC_LAT, null, NUMERIC_LON, LON_HEM);
            fail("expected exception");
        } catch (NullPointerException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionImpl#PositionImpl(float, float)}.
     */
    @Test
    public void testConstructorFloatHemFloatHemNullLonHem() {
        try {
            target = new PositionImpl(NUMERIC_LAT, LAT_HEM, NUMERIC_LON, null);
            fail("expected exception");
        } catch (NullPointerException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionImpl#PositionImpl(float, float)}.
     */
    @Test
    public void testConstructorFloatHemFloatHemLatLT0() {
        try {
            target = new PositionImpl(0 - NUMERIC_LAT, LAT_HEM, NUMERIC_LON, LON_HEM);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionImpl#PositionImpl(float, float)}.
     */
    @Test
    public void testConstructorFloatHemFloatHemLatGT90() {
        try {
            target = new PositionImpl(90.1f, LAT_HEM, NUMERIC_LON, LON_HEM);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionImpl#PositionImpl(float, float)}.
     */
    @Test
    public void testConstructorFloatHemFloatHemLonLT0() {
        try {
            target = new PositionImpl(NUMERIC_LAT, LAT_HEM, 0 - NUMERIC_LON, LON_HEM);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionImpl#PositionImpl(float, float)}.
     */
    @Test
    public void testConstructorFloatHemFloatHemLonGT180() {
        try {
            target = new PositionImpl(NUMERIC_LAT, LAT_HEM, 180.1f, LON_HEM);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionImpl#PositionImpl(float, float)}.
     */
    @Test
    public void testConstructorFloatHemFloatHem() {
        target = new PositionImpl(NUMERIC_LAT, LAT_HEM, NUMERIC_LON, LON_HEM);
        assertEquals(NUMERIC_LAT, target.getLatitude(), 0.001);
        assertEquals(NUMERIC_LON, target.getLongitude(), 0.001);
        assertEquals(LAT_HEM, target.getLatitudinalHemisphere());
        assertEquals(LON_HEM, target.getLongitudinalHemisphere());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionImpl#PositionImpl(float, float)}.
     */
    @Test
    public void testConstructorFloatFloatLatLTNeg90() {
        try {
            target = new PositionImpl(-91, NUMERIC_LON);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionImpl#PositionImpl(float, float)}.
     */
    @Test
    public void testConstructorFloatFloatLatGT90() {
        try {
            target = new PositionImpl(90.1f, NUMERIC_LON);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionImpl#PositionImpl(float, float)}.
     */
    @Test
    public void testConstructorFloatFloatLonLTNeg180() {
        try {
            target = new PositionImpl(NUMERIC_LAT, -181);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionImpl#PositionImpl(float, float)}.
     */
    @Test
    public void testConstructorFloatFloatLonGT180() {
        try {
            target = new PositionImpl(NUMERIC_LAT, 180.1f);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionImpl#PositionImpl(float, float)}.
     */
    @Test
    public void testConstructorFloatFloatPosLatLon() {
        target = new PositionImpl(NUMERIC_LAT, NUMERIC_LON);
        assertEquals(NUMERIC_LAT, target.getLatitude(), 0.001);
        assertEquals(LatitudinalHemisphere.NORTH, target.getLatitudinalHemisphere());
        assertEquals(NUMERIC_LON, target.getLongitude(), 0.001);
        assertEquals(LongitudinalHemisphere.EAST, target.getLongitudinalHemisphere());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionImpl#PositionImpl(float, float)}.
     */
    @Test
    public void testConstructorFloatFloatNegLat() {
        target = new PositionImpl(0 - NUMERIC_LAT, NUMERIC_LON);
        assertEquals(NUMERIC_LAT, target.getLatitude(), 0.001);
        assertEquals(LatitudinalHemisphere.SOUTH, target.getLatitudinalHemisphere());
        assertEquals(NUMERIC_LON, target.getLongitude(), 0.001);
        assertEquals(LongitudinalHemisphere.EAST, target.getLongitudinalHemisphere());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionImpl#PositionImpl(float, float)}.
     */
    @Test
    public void testConstructorFloatFloatNegLon() {
        target = new PositionImpl(NUMERIC_LAT, 0 - NUMERIC_LON);
        assertEquals(NUMERIC_LAT, target.getLatitude(), 0.001);
        assertEquals(LatitudinalHemisphere.NORTH, target.getLatitudinalHemisphere());
        assertEquals(NUMERIC_LON, target.getLongitude(), 0.001);
        assertEquals(LongitudinalHemisphere.WEST, target.getLongitudinalHemisphere());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionImpl#PositionImpl(java.lang.String, com.svhelloworld.knotlog.measure.LatitudinalHemisphere, java.lang.String, com.svhelloworld.knotlog.measure.LatitudinalHemisphere)}.
     */
    @Test
    public void testConstructorStringHemStringHemNullLatitude() {
        try {
            target = new PositionImpl(null, LAT_HEM, STRING_LON, LON_HEM);
            fail("expected exception");
        } catch (NullPointerException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionImpl#PositionImpl(java.lang.String, com.svhelloworld.knotlog.measure.LatitudinalHemisphere, java.lang.String, com.svhelloworld.knotlog.measure.LatitudinalHemisphere)}.
     */
    @Test
    public void testConstructorStringHemStringHemNullLongitude() {
        try {
            target = new PositionImpl(STRING_LAT, LAT_HEM, null, LON_HEM);
            fail("expected exception");
        } catch (NullPointerException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionImpl#PositionImpl(java.lang.String, com.svhelloworld.knotlog.measure.LatitudinalHemisphere, java.lang.String, com.svhelloworld.knotlog.measure.LatitudinalHemisphere)}.
     */
    @Test
    public void testConstructorStringHemStringHemEmptyLatitude() {
        try {
            target = new PositionImpl("", LAT_HEM, STRING_LON, LON_HEM);
            fail("expected exception");
        } catch (NullPointerException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionImpl#PositionImpl(java.lang.String, com.svhelloworld.knotlog.measure.LatitudinalHemisphere, java.lang.String, com.svhelloworld.knotlog.measure.LatitudinalHemisphere)}.
     */
    @Test
    public void testConstructorStringHemStringHemEmptyLongitude() {
        try {
            target = new PositionImpl(STRING_LAT, LAT_HEM, "", LON_HEM);
            fail("expected exception");
        } catch (NullPointerException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionImpl#PositionImpl(java.lang.String, com.svhelloworld.knotlog.measure.LatitudinalHemisphere, java.lang.String, com.svhelloworld.knotlog.measure.LatitudinalHemisphere)}.
     */
    @Test
    public void testConstructorStringHemStringHemNullLatHem() {
        try {
            target = new PositionImpl(STRING_LAT, null, STRING_LON, LON_HEM);
            fail("expected exception");
        } catch (NullPointerException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionImpl#PositionImpl(java.lang.String, com.svhelloworld.knotlog.measure.LatitudinalHemisphere, java.lang.String, com.svhelloworld.knotlog.measure.LatitudinalHemisphere)}.
     */
    @Test
    public void testConstructorStringHemStringHemNullLonHem() {
        try {
            target = new PositionImpl(STRING_LAT, LAT_HEM, STRING_LON, null);
            fail("expected exception");
        } catch (NullPointerException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionImpl#PositionImpl(java.lang.String, com.svhelloworld.knotlog.measure.LatitudinalHemisphere, java.lang.String, com.svhelloworld.knotlog.measure.LatitudinalHemisphere)}.
     */
    @Test
    public void testConstructorStringHemStringHemMalformedLatitude() {
        try {
            target = new PositionImpl("123.456", LAT_HEM, STRING_LON, LON_HEM);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionImpl#PositionImpl(java.lang.String, com.svhelloworld.knotlog.measure.LatitudinalHemisphere, java.lang.String, com.svhelloworld.knotlog.measure.LatitudinalHemisphere)}.
     */
    @Test
    public void testConstructorStringHemStringHemMalformedLongitude() {
        try {
            target = new PositionImpl(STRING_LAT, LAT_HEM, "123.456", LON_HEM);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionImpl#getLatitude()}.
     */
    @Test
    public void testGetLatitude() {
        assertEquals(NUMERIC_LAT, target.getLatitude(), 0.001);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionImpl#getLatitudinalHemisphere()}.
     */
    @Test
    public void testGetLatitudeHemisphere() {
        assertEquals(LAT_HEM, target.getLatitudinalHemisphere());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionImpl#getLongitude()}.
     */
    @Test
    public void testGetLongitude() {
        assertEquals(NUMERIC_LON, target.getLongitude(), 0.001);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.messages.PositionImpl#getLongitudinalHemisphere()}.
     */
    @Test
    public void testGetLongitudeHemisphere() {
        assertEquals(LON_HEM, target.getLongitudinalHemisphere());
    }

}
