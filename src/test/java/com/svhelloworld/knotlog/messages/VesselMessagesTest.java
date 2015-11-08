package com.svhelloworld.knotlog.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.svhelloworld.knotlog.engine.parse.MessageFailure;
import com.svhelloworld.knotlog.measure.DistanceUnit;
import com.svhelloworld.knotlog.measure.LatitudinalHemisphere;
import com.svhelloworld.knotlog.measure.LongitudinalHemisphere;
import com.svhelloworld.knotlog.util.Now;

/**
 * Test to ensure the {@link VesselMessages} class is behaving well.
 */
public class VesselMessagesTest {

    private VesselMessages messages;

    private GPSPosition position;

    private WaterDepth waterDepth;

    private UnrecognizedMessage whut;

    @Before
    public void setup() {
        messages = new VesselMessages();
        position = new GPSPosition(VesselMessageSource.NMEA0183, Now.getInstant(), "2531.3369", LatitudinalHemisphere.NORTH,
                "11104.4274", LongitudinalHemisphere.WEST);
        waterDepth = new WaterDepth(VesselMessageSource.NMEA0183, Now.getInstant(), 100f, DistanceUnit.FATHOMS);
        whut = new UnrecognizedMessage(VesselMessageSource.NMEA0183, Now.getInstant(), MessageFailure.UNRECOGNIZED_SENTENCE,
                new ArrayList<String>());
    }

    @Test
    public void testEmpty() {
        assertEquals(0, messages.size());
        assertTrue(messages.isEmpty());
    }

    @Test
    public void testAdd() {
        messages.add(position);
        assertEquals(1, messages.size());
        assertFalse(messages.isEmpty());
    }

    @Test
    public void testContainsMessageType() {
        messages.add(position);
        assertTrue(messages.containsMessageType(GPSPosition.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCantAddUnrecognizedMessages() {
        messages.add(waterDepth);
        messages.add(whut);
    }

    /**
     * Test to see if containsMessageType will return true for super classes
     */
    @Test
    @Ignore //FIXME - is it worth fixing this?
    public void testContainsMessageTypeSuperClass() {
        messages.add(position);
        assertTrue(messages.containsMessageType(PositionMessage.class));
    }

    /**
     * Test to see if containsMessageType will return true for an interface
     */
    @Test
    @Ignore //FIXME - is it worth fixing this?
    public void testContainsMessageTypeInterface() {
        messages.add(waterDepth);
        assertTrue(messages.containsMessageType(QuantitativeMessage.class));
    }

}
