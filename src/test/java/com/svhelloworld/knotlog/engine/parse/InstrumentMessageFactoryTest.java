package com.svhelloworld.knotlog.engine.parse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.svhelloworld.knotlog.engine.parse.CSVMessageDictionary;
import com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition;
import com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory;
import com.svhelloworld.knotlog.engine.parse.MessageDictionary;
import com.svhelloworld.knotlog.measure.AngleUnit;
import com.svhelloworld.knotlog.measure.DistanceUnit;
import com.svhelloworld.knotlog.measure.LatitudinalHemisphere;
import com.svhelloworld.knotlog.measure.LongitudinalHemisphere;
import com.svhelloworld.knotlog.measure.MeasurementBasis;
import com.svhelloworld.knotlog.measure.SpeedUnit;
import com.svhelloworld.knotlog.measure.TemperatureUnit;
import com.svhelloworld.knotlog.measure.VesselArea;
import com.svhelloworld.knotlog.messages.Altitude;
import com.svhelloworld.knotlog.messages.DateZulu;
import com.svhelloworld.knotlog.messages.GPSPosition;
import com.svhelloworld.knotlog.messages.VesselMessage;
import com.svhelloworld.knotlog.messages.VesselMessageSource;
import com.svhelloworld.knotlog.messages.MagneticVariation;
import com.svhelloworld.knotlog.messages.PositionPrecision;
import com.svhelloworld.knotlog.messages.RudderAngle;
import com.svhelloworld.knotlog.messages.SeaTemperature;
import com.svhelloworld.knotlog.messages.SpeedRelativeToGround;
import com.svhelloworld.knotlog.messages.SpeedRelativeToWater;
import com.svhelloworld.knotlog.messages.VesselHeading;
import com.svhelloworld.knotlog.messages.WaterDepth;
import com.svhelloworld.knotlog.messages.WindDirection;
import com.svhelloworld.knotlog.messages.WindSpeed;

/**
 * Unit test for VesselMessageFactory class.
 * 
 * @author Jason Andersen
 * @since Feb 24, 2010
 *
 */
public class InstrumentMessageFactoryTest {
    
    private VesselMessageSource source;
    private Date date;
    private MessageDictionary dictionary;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        dictionary = new CSVMessageDictionary();
        dictionary.initialize(
                "com/svhelloworld/knotlog/engine/parsers/TestInstrumentMessageFactory.csv");
        date = new Date();
        source = VesselMessageSource.NMEA0183;
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory#createVesselMessage(com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition, java.util.List)}.
     */
    @Test
    public void testCreateAltitude() {
        InstrumentMessageDefinition def = getDefinition("Altitude");
        String[] fields = {"", "", "", "", "", "", "", "", "1234.56"};
        VesselMessage message = InstrumentMessageFactory.createInstrumentMessage(
                source, date, def, Arrays.asList(fields));
        assertTrue(message instanceof Altitude);
        Altitude altitude = (Altitude)message;
        assertEquals(1234.56, altitude.getAltitude());
        assertEquals(DistanceUnit.METERS, altitude.getDistanceUnit());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory#createVesselMessage(com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition, java.util.List)}.
     */
    @Test
    public void testCreateDateZulu() {
        InstrumentMessageDefinition def = getDefinition("DateZulu");
        String[] fields = {"", "", "", "", "", "", "", "", "190510"};
        VesselMessage message = InstrumentMessageFactory.createInstrumentMessage(
                source, date, def, Arrays.asList(fields));
        assertTrue(message instanceof DateZulu);
        DateZulu date = (DateZulu)message;
        Calendar expected = new GregorianCalendar();
        expected.set(2010, 4, 19);
        assertEquals(expected.getTimeInMillis(), date.getDateMilliseconds());
    }
    
    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory#createVesselMessage(com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition, java.util.List)}.
     */
    @Test
    public void testCreateGPSPosition() {
        InstrumentMessageDefinition def = getDefinition("GPSPosition");
        String[] fields = {"", "2530.941", "N", "11103.702", "W"};
        VesselMessage message = InstrumentMessageFactory.createInstrumentMessage(
                source, date, def, Arrays.asList(fields));
        assertTrue(message instanceof GPSPosition);
        GPSPosition position = (GPSPosition)message;
        assertEquals(25.51569, position.getLatitude());
        assertEquals(LatitudinalHemisphere.NORTH, position.getLatitudinalHemisphere());
        assertEquals(111.06171, position.getLongitude());
        assertEquals(LongitudinalHemisphere.WEST, position.getLongitudinalHemisphere());
    }
    
    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory#createVesselMessage(com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition, java.util.List)}.
     */
    @Test
    public void testCreateMagneticVariation() {
        InstrumentMessageDefinition def = getDefinition("MagneticVariation");
        String[] fields = {"", "", "", "", "", "", "", "3.451", "", "10.1", "E"};
        VesselMessage message = InstrumentMessageFactory.createInstrumentMessage(
                source, date, def, Arrays.asList(fields));
        MagneticVariation variation = (MagneticVariation)message;
        assertEquals(10.1, variation.getMagneticVariation());
        assertEquals(LongitudinalHemisphere.EAST, variation.getMagneticVariationDirection());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory#createVesselMessage(com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition, java.util.List)}.
     */
    @Test
    public void testCreatePositionPrecision() {
        InstrumentMessageDefinition def = getDefinition("PositionPrecision");
        String[] fields = {"", "", "", "", "", "", "", "3.451", ""};
        VesselMessage message = InstrumentMessageFactory.createInstrumentMessage(
                source, date, def, Arrays.asList(fields));
        assertTrue(message instanceof PositionPrecision);
        PositionPrecision precision = (PositionPrecision)message;
        assertEquals(3.451, precision.getPositionPrecision());
        assertEquals(DistanceUnit.METERS, precision.getDistanceUnit());
    }
        
    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory#createVesselMessage(com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition, java.util.List)}.
     */
    @Test
    public void testCreateRudderAngle() {
        InstrumentMessageDefinition def = getDefinition("RudderAngle");
        String[] fields = {"-12.2", "", "", "", "", "", "", "3.451", "", "10.1", "E"};
        VesselMessage message = InstrumentMessageFactory.createInstrumentMessage(
                source, date, def, Arrays.asList(fields));
        RudderAngle rudder = (RudderAngle)message;
        assertEquals(-12.2, rudder.getRudderAngle());
        assertEquals(VesselArea.STARBOARD, rudder.getRudderVesselSide());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory#createVesselMessage(com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition, java.util.List)}.
     */
    @Test
    public void testCreateSeaTemperature() {
        InstrumentMessageDefinition def = getDefinition("SeaTemperature");
        String[] fields = {"18.14", "", "", "", "", "", "", "3.451", "", "10.1", "E"};
        VesselMessage message = InstrumentMessageFactory.createInstrumentMessage(
                source, date, def, Arrays.asList(fields));
        SeaTemperature temp = (SeaTemperature)message;
        assertEquals(18.14, temp.getTemperature());
        assertEquals(TemperatureUnit.CELSIUS, temp.getMeasurementUnit());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory#createVesselMessage(com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition, java.util.List)}.
     */
    @Test
    public void testCreateSpeedRelativeToGround() {
        InstrumentMessageDefinition def = getDefinition("SpeedRelativeToGround");
        String[] fields = {"18.14", "", "", "", "", "", "6.74", "3.451", "", "10.1", "E"};
        VesselMessage message = InstrumentMessageFactory.createInstrumentMessage(
                source, date, def, Arrays.asList(fields));
        SpeedRelativeToGround speed = (SpeedRelativeToGround)message;
        assertEquals(6.74, speed.getSpeed());
        assertEquals(SpeedUnit.KNOTS, speed.getSpeedUnit());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory#createVesselMessage(com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition, java.util.List)}.
     */
    @Test
    public void testCreateSpeedRelativeToWater() {
        InstrumentMessageDefinition def = getDefinition("SpeedRelativeToWater");
        String[] fields = {"18.14", "", "", "", "7.82", "", "", "3.451", "", "10.1", "E"};
        VesselMessage message = InstrumentMessageFactory.createInstrumentMessage(
                source, date, def, Arrays.asList(fields));
        SpeedRelativeToWater speed = (SpeedRelativeToWater)message;
        assertEquals(7.82, speed.getSpeed());
        assertEquals(SpeedUnit.KNOTS, speed.getSpeedUnit());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory#createVesselMessage(com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition, java.util.List)}.
     */
    @Test
    public void testCreateVesselHeading() {
        InstrumentMessageDefinition def = getDefinition("VesselHeading");
        String[] fields = {"278.3", "", "", "", "7.82", "", "", "3.451", "", "10.1", "E"};
        VesselMessage message = InstrumentMessageFactory.createInstrumentMessage(
                source, date, def, Arrays.asList(fields));
        VesselHeading heading = (VesselHeading)message;
        assertEquals(278.3, heading.getVesselHeading());
        assertEquals(AngleUnit.DEGREES_MAGNETIC, heading.getMeasurementUnit());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory#createVesselMessage(com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition, java.util.List)}.
     */
    @Test
    public void testCreateWaterDepth() {
        InstrumentMessageDefinition def = getDefinition("WaterDepth");
        String[] fields = {"584.19", "", "", "", "7.82", "", "", "3.451", "", "10.1", "E"};
        VesselMessage message = InstrumentMessageFactory.createInstrumentMessage(
                source, date, def, Arrays.asList(fields));
        WaterDepth depth = (WaterDepth)message;
        assertEquals(584.19, depth.getWaterDepth());
        assertEquals(DistanceUnit.FEET, depth.getMeasurementUnit());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory#createVesselMessage(com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition, java.util.List)}.
     */
    @Test
    public void testCreateWindDirection() {
        InstrumentMessageDefinition def = getDefinition("WindDirection");
        String[] fields = {"274", "R", "", "", "7.82", "", "", "3.451", "", "10.1", "E"};
        VesselMessage message = InstrumentMessageFactory.createInstrumentMessage(
                source, date, def, Arrays.asList(fields));
        WindDirection direction = (WindDirection)message;
        assertEquals(86, direction.getWindDirection());
        assertEquals(MeasurementBasis.RELATIVE, direction.getBasis());
        assertEquals(VesselArea.PORT, direction.getVesselSide());
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.InstrumentMessageFactory#createVesselMessage(com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition, java.util.List)}.
     */
    @Test
    public void testCreateWindSpeed() {
        InstrumentMessageDefinition def = getDefinition("WindSpeed");
        String[] fields = {"18.14", "R", "21.287", "N", "7.82", "", "", "3.451", "", "10.1", "E"};
        VesselMessage message = InstrumentMessageFactory.createInstrumentMessage(
                source, date, def, Arrays.asList(fields));
        WindSpeed speed = (WindSpeed)message;
        assertEquals(21.287, speed.getWindSpeed());
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
