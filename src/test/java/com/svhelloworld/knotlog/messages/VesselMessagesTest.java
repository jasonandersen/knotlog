package com.svhelloworld.knotlog.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.svhelloworld.knotlog.engine.parse.MessageFailure;
import com.svhelloworld.knotlog.measure.DistanceUnit;
import com.svhelloworld.knotlog.measure.LatitudinalHemisphere;
import com.svhelloworld.knotlog.measure.LongitudinalHemisphere;
import com.svhelloworld.knotlog.messages.GPSPosition;
import com.svhelloworld.knotlog.messages.PositionMessage;
import com.svhelloworld.knotlog.messages.QuantitativeMessage;
import com.svhelloworld.knotlog.messages.UnrecognizedMessage;
import com.svhelloworld.knotlog.messages.VesselMessage;
import com.svhelloworld.knotlog.messages.VesselMessageSource;
import com.svhelloworld.knotlog.messages.WaterDepth;

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
        position = new GPSPosition(VesselMessageSource.NMEA0183, new Date(), "2531.3369", LatitudinalHemisphere.NORTH,
                "11104.4274", LongitudinalHemisphere.WEST);
        waterDepth = new WaterDepth(VesselMessageSource.NMEA0183, new Date(), 100f, DistanceUnit.FATHOMS);
        whut = new UnrecognizedMessage(VesselMessageSource.NMEA0183, new Date(), MessageFailure.UNRECOGNIZED_SENTENCE,
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

    @Test
    public void testDoesntContainsUnrecognizedMessage() {
        messages.add(waterDepth);
        assertFalse(messages.containsUnrecognizedMessage());
    }

    @Test
    public void testContainsUnrecognizedMessages() {
        messages.add(waterDepth);
        messages.add(whut);
        assertTrue(messages.containsUnrecognizedMessage());
    }

    @Test
    public void testUnrecognizedMessagesDontCountInSize() {
        messages.add(waterDepth);
        messages.add(position);
        messages.add(whut);
        assertEquals(2, messages.size());
    }

    @Test
    public void testUnrecognizedMessagesDontGetIterated() {
        messages.add(waterDepth);
        messages.add(position);
        messages.add(whut);
        for (VesselMessage message : messages) {
            if (message instanceof UnrecognizedMessage) {
                fail("found an unrecognized message");
            }
        }
    }

    @Test
    public void testGetUnrecognizedMessages() {
        messages.add(waterDepth);
        messages.add(position);
        messages.add(whut);
        List<UnrecognizedMessage> unrecognized = messages.getUnrecognizedMessages();
        assertEquals(1, unrecognized.size());
        assertTrue(unrecognized.contains(whut));
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
