package com.svhelloworld.knotlog.engine.parse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.Subscribe;
import com.svhelloworld.knotlog.domain.messages.UnrecognizedMessage;
import com.svhelloworld.knotlog.domain.messages.ValidVesselMessage;
import com.svhelloworld.knotlog.domain.messages.VesselMessages;
import com.svhelloworld.knotlog.test.BaseIntegrationTest;

/**
 * Test parsing NMEA0183 sentences.
 */
public class NMEA0183ParsingTest extends BaseIntegrationTest {

    private static Logger log = LoggerFactory.getLogger(NMEA0183ParsingTest.class);

    private VesselMessages vesselMessages = new VesselMessages();

    private UnrecognizedMessage unrecognizedMessage;

    private MessageFailure messageFailure;

    @Test
    public void testEventBusIsSetupToHandleSentences() {
        assertTrue(vesselMessages.isEmpty());
        NMEA0183Sentence sentence = new NMEA0183Sentence(
                "25757110,V,GPS,GPGGA,130048,2531.3366,N,11104.4272,W,2,09,0.9,1.7,M,-31.5,M,,");
        post(sentence);
        assertFalse(vesselMessages.isEmpty());
    }

    @Test
    public void testEmptyFieldsInRecognizedSentence() {
        parseSentence("$IIDBT,,,,");
        assertTrue(vesselMessages.isEmpty());
        assertNotNull(unrecognizedMessage);
        assertEquals(MessageFailure.INVALID_SENTENCE_FIELDS, messageFailure);
    }

    @Test
    public void testUnrecognizedTag() {
        parseSentence("54059,V,SRL,XXXXX,4,P,2,B,0,,,,,,,,,,,,,,,");
        assertTrue(vesselMessages.isEmpty());
        assertNotNull(unrecognizedMessage);
        assertEquals(MessageFailure.UNRECOGNIZED_SENTENCE, messageFailure);
    }

    @Test
    public void testMalformedSentence() {
        parseSentence("I LIKE MONKEYS. ALSO, I EAT GLUE.");
        assertTrue(vesselMessages.isEmpty());
        assertNotNull(unrecognizedMessage);
        assertEquals(MessageFailure.MALFORMED_SENTENCE, messageFailure);
    }

    /**
     * Posts an event on to the event bus.
     * @param sentence
     */
    private void parseSentence(String sentence) {
        post(new NMEA0183Sentence(sentence));
    }

    /*
     * Event bus event handler methods
     */

    @Subscribe
    public void vesselMessageDiscovered(ValidVesselMessage message) {
        log.info("vessel messages discovered: {}", message);
        vesselMessages.add(message);
    }

    @Subscribe
    public void unrecognizedMessageDiscovered(UnrecognizedMessage message) {
        unrecognizedMessage = message;
        messageFailure = unrecognizedMessage.getFailureMode();
    }
}
