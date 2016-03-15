package com.svhelloworld.knotlog.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.Instant;

import org.junit.Before;
import org.junit.Test;

import com.svhelloworld.knotlog.domain.messages.VesselMessageSource;
import com.svhelloworld.knotlog.domain.messages.WindDirection;
import com.svhelloworld.knotlog.measure.MeasurementBasis;
import com.svhelloworld.knotlog.measure.VesselArea;

/**
 * Unit test for <tt>WindDirection</tt> class.
 * 
 * @author Jason Andersen
 * @since Mar 5, 2010
 *
 */
public class WindDirectionTest extends
        BaseQuantitativeMessageTest<WindDirection, MeasurementBasis> {

    private static final float DIRECTION = 88.681f;

    private static final MeasurementBasis BASIS = MeasurementBasis.RELATIVE;

    private static final VesselArea SIDE = VesselArea.PORT;

    private WindDirection direction;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        direction = new WindDirection(source, timestamp, DIRECTION, BASIS, SIDE);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.WindDirection#WindDirection(com.svhelloworld.knotlog.domain.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.MeasurementBasis, com.svhelloworld.knotlog.measure.VesselArea)}.
     */
    @Test
    public void testConstructorDirLessThan0() {
        try {
            @SuppressWarnings("unused")
            WindDirection target = new WindDirection(source, timestamp, -1, BASIS, SIDE);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.WindDirection#WindDirection(com.svhelloworld.knotlog.domain.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.MeasurementBasis, com.svhelloworld.knotlog.measure.VesselArea)}.
     */
    @Test
    public void testConstructorGreaterThan360() {
        try {
            @SuppressWarnings("unused")
            WindDirection target = new WindDirection(source, timestamp, 361, BASIS, SIDE);
            fail("expected exception");
        } catch (IllegalArgumentException e) {
            //expected
        }
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.WindDirection#WindDirection(com.svhelloworld.knotlog.domain.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.MeasurementBasis, com.svhelloworld.knotlog.measure.VesselArea)}.
     */
    @Test
    public void testConstructorDirEquals0() {
        WindDirection target = new WindDirection(source, timestamp, 0, BASIS, null);
        assertEquals(0, target.getWindDirection(), 0.001);
        assertEquals(BASIS, target.getBasis());
        assertEquals(VesselArea.STARBOARD, target.getVesselSide());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.WindDirection#WindDirection(com.svhelloworld.knotlog.domain.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.MeasurementBasis, com.svhelloworld.knotlog.measure.VesselArea)}.
     */
    @Test
    public void testConstructorDirEquals360() {
        WindDirection target = new WindDirection(source, timestamp, 360, BASIS, null);
        assertEquals(0, target.getWindDirection(), 0.001);
        assertEquals(BASIS, target.getBasis());
        assertEquals(VesselArea.PORT, target.getVesselSide());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.WindDirection#WindDirection(com.svhelloworld.knotlog.domain.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.MeasurementBasis, com.svhelloworld.knotlog.measure.VesselArea)}.
     */
    @Test
    public void testConstructorDirEquals179() {
        WindDirection target = new WindDirection(source, timestamp, 179, BASIS, null);
        assertEquals(179, target.getWindDirection(), 0.001);
        assertEquals(BASIS, target.getBasis());
        assertEquals(VesselArea.STARBOARD, target.getVesselSide());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.WindDirection#WindDirection(com.svhelloworld.knotlog.domain.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.MeasurementBasis, com.svhelloworld.knotlog.measure.VesselArea)}.
     */
    @Test
    public void testConstructorDirEquals180() {
        WindDirection target = new WindDirection(source, timestamp, 180, BASIS, null);
        assertEquals(180, target.getWindDirection(), 0.001);
        assertEquals(BASIS, target.getBasis());
        assertEquals(VesselArea.STARBOARD, target.getVesselSide());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.WindDirection#WindDirection(com.svhelloworld.knotlog.domain.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.MeasurementBasis, com.svhelloworld.knotlog.measure.VesselArea)}.
     */
    @Test
    public void testConstructorDirEquals181() {
        WindDirection target = new WindDirection(source, timestamp, 181, BASIS, null);
        assertEquals(179, target.getWindDirection(), 0.001);
        assertEquals(BASIS, target.getBasis());
        assertEquals(VesselArea.PORT, target.getVesselSide());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.WindDirection#WindDirection(com.svhelloworld.knotlog.domain.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.MeasurementBasis, com.svhelloworld.knotlog.measure.VesselArea)}.
     */
    @Test
    public void testConstructorDirEquals45() {
        WindDirection target = new WindDirection(source, timestamp, 45, BASIS, null);
        assertEquals(45, target.getWindDirection(), 0.001);
        assertEquals(BASIS, target.getBasis());
        assertEquals(VesselArea.STARBOARD, target.getVesselSide());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.WindDirection#WindDirection(com.svhelloworld.knotlog.domain.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.MeasurementBasis, com.svhelloworld.knotlog.measure.VesselArea)}.
     */
    @Test
    public void testConstructorDirEquals90() {
        WindDirection target = new WindDirection(source, timestamp, 90, BASIS, null);
        assertEquals(90, target.getWindDirection(), 0.001);
        assertEquals(BASIS, target.getBasis());
        assertEquals(VesselArea.STARBOARD, target.getVesselSide());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.WindDirection#WindDirection(com.svhelloworld.knotlog.domain.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.MeasurementBasis, com.svhelloworld.knotlog.measure.VesselArea)}.
     */
    @Test
    public void testConstructorDirEquals135() {
        WindDirection target = new WindDirection(source, timestamp, 135, BASIS, null);
        assertEquals(135, target.getWindDirection(), 0.001);
        assertEquals(BASIS, target.getBasis());
        assertEquals(VesselArea.STARBOARD, target.getVesselSide());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.WindDirection#WindDirection(com.svhelloworld.knotlog.domain.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.MeasurementBasis, com.svhelloworld.knotlog.measure.VesselArea)}.
     */
    @Test
    public void testConstructorDirEquals225() {
        WindDirection target = new WindDirection(source, timestamp, 225, BASIS, null);
        assertEquals(135, target.getWindDirection(), 0.001);
        assertEquals(BASIS, target.getBasis());
        assertEquals(VesselArea.PORT, target.getVesselSide());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.WindDirection#WindDirection(com.svhelloworld.knotlog.domain.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.MeasurementBasis, com.svhelloworld.knotlog.measure.VesselArea)}.
     */
    @Test
    public void testConstructorDirEquals270() {
        WindDirection target = new WindDirection(source, timestamp, 270, BASIS, null);
        assertEquals(90, target.getWindDirection(), 0.001);
        assertEquals(BASIS, target.getBasis());
        assertEquals(VesselArea.PORT, target.getVesselSide());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.WindDirection#WindDirection(com.svhelloworld.knotlog.domain.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.MeasurementBasis, com.svhelloworld.knotlog.measure.VesselArea)}.
     */
    @Test
    public void testConstructorDirEquals315() {
        WindDirection target = new WindDirection(source, timestamp, 315, BASIS, null);
        assertEquals(45, target.getWindDirection(), 0.001);
        assertEquals(BASIS, target.getBasis());
        assertEquals(VesselArea.PORT, target.getVesselSide());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.WindDirection#WindDirection(com.svhelloworld.knotlog.domain.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.MeasurementBasis, com.svhelloworld.knotlog.measure.VesselArea)}.
     */
    @Test
    public void testConstructorLessThan180PORT() {
        WindDirection target = new WindDirection(source, timestamp, 90, BASIS, VesselArea.PORT);
        assertEquals(90, target.getWindDirection(), 0.001);
        assertEquals(BASIS, target.getBasis());
        assertEquals(VesselArea.PORT, target.getVesselSide());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.WindDirection#WindDirection(com.svhelloworld.knotlog.domain.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.MeasurementBasis, com.svhelloworld.knotlog.measure.VesselArea)}.
     */
    @Test
    public void testConstructorLessThan180STARBOARD() {
        WindDirection target = new WindDirection(source, timestamp, 90, BASIS, VesselArea.STARBOARD);
        assertEquals(90, target.getWindDirection(), 0.001);
        assertEquals(BASIS, target.getBasis());
        assertEquals(VesselArea.STARBOARD, target.getVesselSide());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.WindDirection#WindDirection(com.svhelloworld.knotlog.domain.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.MeasurementBasis, com.svhelloworld.knotlog.measure.VesselArea)}.
     */
    @Test
    public void testConstructorGreaterThan180PORT() {
        WindDirection target = new WindDirection(source, timestamp, 270, BASIS, VesselArea.PORT);
        assertEquals(90, target.getWindDirection(), 0.001);
        assertEquals(BASIS, target.getBasis());
        assertEquals(VesselArea.PORT, target.getVesselSide());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.WindDirection#WindDirection(com.svhelloworld.knotlog.domain.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.MeasurementBasis, com.svhelloworld.knotlog.measure.VesselArea)}.
     */
    @Test
    public void testConstructorGreaterThan180STARBOARD() {
        WindDirection target = new WindDirection(source, timestamp, 270, BASIS, VesselArea.STARBOARD);
        assertEquals(90, target.getWindDirection(), 0.001);
        assertEquals(BASIS, target.getBasis());
        assertEquals(VesselArea.PORT, target.getVesselSide());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.WindDirection#WindDirection(com.svhelloworld.knotlog.domain.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.MeasurementBasis, com.svhelloworld.knotlog.measure.VesselArea)}.
     */
    @Test
    public void testConstructorDirEquals180STARBOARD() {
        WindDirection target = new WindDirection(source, timestamp, 180, BASIS, VesselArea.STARBOARD);
        assertEquals(180, target.getWindDirection(), 0.001);
        assertEquals(BASIS, target.getBasis());
        assertEquals(VesselArea.STARBOARD, target.getVesselSide());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.WindDirection#WindDirection(com.svhelloworld.knotlog.domain.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.MeasurementBasis, com.svhelloworld.knotlog.measure.VesselArea)}.
     */
    @Test
    public void testConstructorDirEquals180PORT() {
        WindDirection target = new WindDirection(source, timestamp, 180, BASIS, VesselArea.PORT);
        assertEquals(180, target.getWindDirection(), 0.001);
        assertEquals(BASIS, target.getBasis());
        assertEquals(VesselArea.PORT, target.getVesselSide());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.WindDirection#WindDirection(com.svhelloworld.knotlog.domain.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.MeasurementBasis, com.svhelloworld.knotlog.measure.VesselArea)}.
     */
    @Test
    public void testConstructorDirEquals0STARBOARD() {
        WindDirection target = new WindDirection(source, timestamp, 0, BASIS, VesselArea.STARBOARD);
        assertEquals(0, target.getWindDirection(), 0.001);
        assertEquals(BASIS, target.getBasis());
        assertEquals(VesselArea.STARBOARD, target.getVesselSide());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.WindDirection#WindDirection(com.svhelloworld.knotlog.domain.messages.VesselMessageSource, java.util.Date, float, com.svhelloworld.knotlog.measure.MeasurementBasis, com.svhelloworld.knotlog.measure.VesselArea)}.
     */
    @Test
    public void testConstructorDirEquals0PORT() {
        WindDirection target = new WindDirection(source, timestamp, 0, BASIS, VesselArea.PORT);
        assertEquals(0, target.getWindDirection(), 0.001);
        assertEquals(BASIS, target.getBasis());
        assertEquals(VesselArea.PORT, target.getVesselSide());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.WindDirection#getBasis()}.
     */
    @Test
    public void testGetBasis() {
        assertEquals(BASIS, direction.getBasis());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.domain.messages.WindDirection#getVesselSide()}.
     */
    @Test
    public void testGetVesselSide() {
        assertEquals(SIDE, direction.getVesselSide());
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getExpectedDisplayString()
     */
    @Override
    protected String getExpectedDisplayString() {
        return "relative wind direction 89° to port";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getExpectedName()
     */
    @Override
    protected String getExpectedName() {
        return "wind direction";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getInstance(com.svhelloworld.knotlog.domain.messages.VesselMessageSource, java.util.Date, com.svhelloworld.knotlog.measure.MeasurementUnit)
     */
    @Override
    protected WindDirection getInstance(VesselMessageSource source, Instant timestamp, MeasurementBasis basis) {
        return new WindDirection(source, timestamp, DIRECTION, basis, SIDE);
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseQuantitativeMessageTest#getMeasurementUnit()
     */
    @Override
    protected MeasurementBasis getMeasurementUnit() {
        return BASIS;
    }

}
