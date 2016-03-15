package com.svhelloworld.knotlog.messages;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.svhelloworld.knotlog.domain.messages.PositionFormat;
import com.svhelloworld.knotlog.domain.messages.PositionImpl;
import com.svhelloworld.knotlog.measure.LatitudinalHemisphere;
import com.svhelloworld.knotlog.measure.LongitudinalHemisphere;

/**
 * Unit test for <tt>PositionFormat</tt> class.
 * 
 * @author Jason Andersen
 * @since Mar 6, 2010
 *
 */
public class PositionFormatTest {

    private PositionImpl position;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        position = new PositionImpl("2530.941", LatitudinalHemisphere.NORTH, "11103.702", LongitudinalHemisphere.WEST);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.PositionFormat#pattern(com.svhelloworld.knotlog.messages.Position)}.
     */
    @Test
    public void testFormatDeg() {
        final String expected = "25.51568°N 111.06170°W";
        final String result = PositionFormat.DEGREES.format(position);
        assertEquals(expected, result);
        System.out.println(result);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.PositionFormat#pattern(com.svhelloworld.knotlog.messages.Position)}.
     */
    @Test
    public void testFormatDegMin() {
        final String expected = "25°30.941'N 111°03.702'W";
        assertEquals(expected, PositionFormat.DEGREES_MINUTES.format(position));
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.PositionFormat#pattern(com.svhelloworld.knotlog.messages.Position)}.
     */
    @Test
    public void testFormatDegMinSec() {
        final String expected = "25°30'56.5\"N 111°03'42.1\"W";
        assertEquals(expected, PositionFormat.DEGREES_MINUTES_SECONDS.format(position));
    }

}
