package com.svhelloworld.knotlog.engine.parse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.svhelloworld.knotlog.domain.messages.Altitude;
import com.svhelloworld.knotlog.domain.messages.DateZulu;
import com.svhelloworld.knotlog.domain.messages.GPSPosition;
import com.svhelloworld.knotlog.domain.messages.MagneticVariation;
import com.svhelloworld.knotlog.domain.messages.PositionPrecision;
import com.svhelloworld.knotlog.domain.messages.RudderAngle;
import com.svhelloworld.knotlog.domain.messages.SeaTemperature;
import com.svhelloworld.knotlog.domain.messages.SpeedRelativeToGround;
import com.svhelloworld.knotlog.domain.messages.SpeedRelativeToWater;
import com.svhelloworld.knotlog.domain.messages.VesselHeading;
import com.svhelloworld.knotlog.domain.messages.VesselMessage;
import com.svhelloworld.knotlog.domain.messages.VesselMessageSource;
import com.svhelloworld.knotlog.domain.messages.WaterDepth;
import com.svhelloworld.knotlog.domain.messages.WindDirection;
import com.svhelloworld.knotlog.domain.messages.WindSpeed;
import com.svhelloworld.knotlog.measure.AngleUnit;
import com.svhelloworld.knotlog.measure.DistanceUnit;
import com.svhelloworld.knotlog.measure.LatitudinalHemisphere;
import com.svhelloworld.knotlog.measure.LongitudinalHemisphere;
import com.svhelloworld.knotlog.measure.MeasurementBasis;
import com.svhelloworld.knotlog.measure.SpeedUnit;
import com.svhelloworld.knotlog.measure.TemperatureUnit;
import com.svhelloworld.knotlog.measure.VesselArea;
import com.svhelloworld.knotlog.test.BaseIntegrationTest;
import com.svhelloworld.knotlog.util.Now;

/**
 * Unit test for VesselMessageFactory class.
 * 
 * @author Jason Andersen
 * @since Feb 24, 2010
 *
 */
public class InstrumentMessageFactoryTest extends BaseIntegrationTest {

    private VesselMessageSource source;

    private Instant instant;

    private MessageDictionary dictionary;

    @Autowired
    private InstrumentMessageFactory factory;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        dictionary = new CSVMessageDictionary();
        dictionary.initialize(
                "com/svhelloworld/knotlog/engine/parse/TestInstrumentMessageFactory.csv");
        instant = Now.getInstant();
        source = VesselMessageSource.NMEA0183;
    }

    @Test
    public void testFactoryInjected() {
        assertNotNull(factory);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory#createVesselMessage(com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition, java.util.List)}.
     */
    @Test
    public void testCreateAltitude() {
        InstrumentMessageDefinition def = getDefinition("Altitude");
        String[] fields = { "", "", "", "", "", "", "", "", "1234.56" };
        VesselMessage message = factory.createInstrumentMessage(
                source, instant, def, Arrays.asList(fields));
        assertTrue(message instanceof Altitude);
        Altitude altitude = (Altitude) message;
        assertEquals(1234.56, altitude.getAltitude(), 0.001);
        assertEquals(DistanceUnit.METERS, altitude.getDistanceUnit());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory#createVesselMessage(com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition, java.util.List)}.
     */
    @Test
    public void testCreateDateZulu() {
        InstrumentMessageDefinition def = getDefinition("DateZulu");
        String[] fields = { "", "", "", "", "", "", "", "", "190510" };
        VesselMessage message = factory.createInstrumentMessage(
                source, instant, def, Arrays.asList(fields));
        assertTrue(message instanceof DateZulu);
        DateZulu date = (DateZulu) message;
        Calendar expected = new GregorianCalendar();
        expected.set(2010, 4, 19);
        assertEquals(expected.getTimeInMillis(), date.getDateMilliseconds(), 100);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory#createVesselMessage(com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition, java.util.List)}.
     */
    @Test
    public void testCreateGPSPosition() {
        InstrumentMessageDefinition def = getDefinition("GPSPosition");
        String[] fields = { "", "2530.941", "N", "11103.702", "W" };
        VesselMessage message = factory.createInstrumentMessage(
                source, instant, def, Arrays.asList(fields));
        assertTrue(message instanceof GPSPosition);
        GPSPosition position = (GPSPosition) message;
        assertEquals(25.51569, position.getLatitude(), 0.001);
        assertEquals(LatitudinalHemisphere.NORTH, position.getLatitudinalHemisphere());
        assertEquals(111.06171, position.getLongitude(), 0.001);
        assertEquals(LongitudinalHemisphere.WEST, position.getLongitudinalHemisphere());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory#createVesselMessage(com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition, java.util.List)}.
     */
    @Test
    public void testCreateMagneticVariation() {
        InstrumentMessageDefinition def = getDefinition("MagneticVariation");
        String[] fields = { "", "", "", "", "", "", "", "3.451", "", "10.1", "E" };
        VesselMessage message = factory.createInstrumentMessage(
                source, instant, def, Arrays.asList(fields));
        MagneticVariation variation = (MagneticVariation) message;
        assertEquals(10.1, variation.getMagneticVariation(), 0.001);
        assertEquals(LongitudinalHemisphere.EAST, variation.getMagneticVariationDirection());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory#createVesselMessage(com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition, java.util.List)}.
     */
    @Test
    public void testCreatePositionPrecision() {
        InstrumentMessageDefinition def = getDefinition("PositionPrecision");
        String[] fields = { "", "", "", "", "", "", "", "3.451", "" };
        VesselMessage message = factory.createInstrumentMessage(
                source, instant, def, Arrays.asList(fields));
        assertTrue(message instanceof PositionPrecision);
        PositionPrecision precision = (PositionPrecision) message;
        assertEquals(3.451, precision.getPositionPrecision(), 0.001);
        assertEquals(DistanceUnit.METERS, precision.getDistanceUnit());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory#createVesselMessage(com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition, java.util.List)}.
     */
    @Test
    public void testCreateRudderAngle() {
        InstrumentMessageDefinition def = getDefinition("RudderAngle");
        String[] fields = { "-12.2", "", "", "", "", "", "", "3.451", "", "10.1", "E" };
        VesselMessage message = factory.createInstrumentMessage(
                source, instant, def, Arrays.asList(fields));
        RudderAngle rudder = (RudderAngle) message;
        assertEquals(-12.2, rudder.getRudderAngle(), 0.001);
        assertEquals(VesselArea.STARBOARD, rudder.getRudderVesselSide());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory#createVesselMessage(com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition, java.util.List)}.
     */
    @Test
    public void testCreateSeaTemperature() {
        InstrumentMessageDefinition def = getDefinition("SeaTemperature");
        String[] fields = { "18.14", "", "", "", "", "", "", "3.451", "", "10.1", "E" };
        VesselMessage message = factory.createInstrumentMessage(
                source, instant, def, Arrays.asList(fields));
        SeaTemperature temp = (SeaTemperature) message;
        assertEquals(18.14, temp.getTemperature(), 0.001);
        assertEquals(TemperatureUnit.CELSIUS, temp.getMeasurementUnit());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory#createVesselMessage(com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition, java.util.List)}.
     */
    @Test
    public void testCreateSpeedRelativeToGround() {
        InstrumentMessageDefinition def = getDefinition("SpeedRelativeToGround");
        String[] fields = { "18.14", "", "", "", "", "", "6.74", "3.451", "", "10.1", "E" };
        VesselMessage message = factory.createInstrumentMessage(
                source, instant, def, Arrays.asList(fields));
        SpeedRelativeToGround speed = (SpeedRelativeToGround) message;
        assertEquals(6.74, speed.getSpeed(), 0.001);
        assertEquals(SpeedUnit.KNOTS, speed.getSpeedUnit());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory#createVesselMessage(com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition, java.util.List)}.
     */
    @Test
    public void testCreateSpeedRelativeToWater() {
        InstrumentMessageDefinition def = getDefinition("SpeedRelativeToWater");
        String[] fields = { "18.14", "", "", "", "7.82", "", "", "3.451", "", "10.1", "E" };
        VesselMessage message = factory.createInstrumentMessage(
                source, instant, def, Arrays.asList(fields));
        SpeedRelativeToWater speed = (SpeedRelativeToWater) message;
        assertEquals(7.82, speed.getSpeed(), 0.001);
        assertEquals(SpeedUnit.KNOTS, speed.getSpeedUnit());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory#createVesselMessage(com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition, java.util.List)}.
     */
    @Test
    public void testCreateVesselHeading() {
        InstrumentMessageDefinition def = getDefinition("VesselHeading");
        String[] fields = { "278.3", "", "", "", "7.82", "", "", "3.451", "", "10.1", "E" };
        VesselMessage message = factory.createInstrumentMessage(
                source, instant, def, Arrays.asList(fields));
        VesselHeading heading = (VesselHeading) message;
        assertEquals(278.3, heading.getVesselHeading(), 0.001);
        assertEquals(AngleUnit.DEGREES_MAGNETIC, heading.getMeasurementUnit());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory#createVesselMessage(com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition, java.util.List)}.
     */
    @Test
    public void testCreateWaterDepth() {
        InstrumentMessageDefinition def = getDefinition("WaterDepth");
        String[] fields = { "584.19", "", "", "", "7.82", "", "", "3.451", "", "10.1", "E" };
        VesselMessage message = factory.createInstrumentMessage(
                source, instant, def, Arrays.asList(fields));
        WaterDepth depth = (WaterDepth) message;
        assertEquals(584.19, depth.getWaterDepth(), 0.001);
        assertEquals(DistanceUnit.FEET, depth.getMeasurementUnit());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory#createVesselMessage(com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition, java.util.List)}.
     */
    @Test
    public void testCreateWindDirection() {
        InstrumentMessageDefinition def = getDefinition("WindDirection");
        String[] fields = { "274", "R", "", "", "7.82", "", "", "3.451", "", "10.1", "E" };
        VesselMessage message = factory.createInstrumentMessage(
                source, instant, def, Arrays.asList(fields));
        WindDirection direction = (WindDirection) message;
        assertEquals(86, direction.getWindDirection(), 0.001);
        assertEquals(MeasurementBasis.RELATIVE, direction.getBasis());
        assertEquals(VesselArea.PORT, direction.getVesselSide());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory#createVesselMessage(com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition, java.util.List)}.
     */
    @Test
    public void testCreateWindSpeed() {
        InstrumentMessageDefinition def = getDefinition("WindSpeed");
        String[] fields = { "18.14", "R", "21.287", "N", "7.82", "", "", "3.451", "", "10.1", "E" };
        VesselMessage message = factory.createInstrumentMessage(
                source, instant, def, Arrays.asList(fields));
        WindSpeed speed = (WindSpeed) message;
        assertEquals(21.287, speed.getWindSpeed(), 0.001);
        assertEquals(MeasurementBasis.RELATIVE, speed.getBasis());
        assertEquals(SpeedUnit.KNOTS, speed.getSpeedUnit());
    }

    /**
     * @param className
     * @return the first definition found for the given class name,
     *          returns null if none found.
     */
    private InstrumentMessageDefinition getDefinition(String className) {
        List<InstrumentMessageDefinition> definitions = dictionary.getAllDefinitions();
        for (InstrumentMessageDefinition definition : definitions) {
            if (definition.getMessageClassName().equals(className)) {
                return definition;
            }
        }
        return null;
    }

}
