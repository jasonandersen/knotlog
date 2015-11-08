package com.svhelloworld.knotlog.engine.parse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.svhelloworld.knotlog.event.NMEA0183SentenceDiscovered;
import com.svhelloworld.knotlog.event.UnrecognizedMessageDiscovered;
import com.svhelloworld.knotlog.event.VesselMessagesDiscovered;
import com.svhelloworld.knotlog.messages.UnrecognizedMessage;
import com.svhelloworld.knotlog.messages.VesselMessages;
import com.svhelloworld.knotlog.test.BaseIntegrationTest;

/**
 * Test parsing NMEA0183 sentences.
 */
public class NMEA0183ParserTest extends BaseIntegrationTest {

    private static Logger log = LoggerFactory.getLogger(NMEA0183ParserTest.class);

    @Autowired
    private EventBus eventBus;

    private VesselMessagesDiscovered vesselMessagesEvent;

    private VesselMessages vesselMessages;

    private UnrecognizedMessageDiscovered unrecognizedMessageEvent;

    private UnrecognizedMessage unrecognizedMessage;

    private MessageFailure messageFailure;

    @Before
    public void registerInEventBus() {
        eventBus.register(this);
    }

    @After
    public void deregisterInEventBus() {
        eventBus.unregister(this);
    }

    @Test
    public void testDependencyInjection() {
        assertNotNull(eventBus);
    }

    @Test
    public void testNMEA0183SentenceDiscoveredEvent() {
        NMEA0183Sentence sentence = new NMEA0183Sentence(
                "25757110,V,GPS,GPGGA,130048,2531.3366,N,11104.4272,W,2,09,0.9,1.7,M,-31.5,M,,");
        NMEA0183SentenceDiscovered event = new NMEA0183SentenceDiscovered(sentence);
        eventBus.post(event);
        assertNotNull(vesselMessagesEvent);
    }

    @Test
    public void testEmptyFieldsInRecognizedSentence() {
        parseSentence("$IIDBT,,,,");
        assertNull(vesselMessages);
        assertNotNull(unrecognizedMessageEvent);
        assertNotNull(unrecognizedMessage);
        assertEquals(MessageFailure.INVALID_SENTENCE_FIELDS, messageFailure);
    }

    @Test
    public void testUnrecognizedTag() {
        parseSentence("54059,V,SRL,XXXXX,4,P,2,B,0,,,,,,,,,,,,,,,");
        assertNull(vesselMessages);
        assertNotNull(unrecognizedMessageEvent);
        assertNotNull(unrecognizedMessage);
        assertEquals(MessageFailure.UNRECOGNIZED_SENTENCE, messageFailure);
    }

    @Test
    public void testMalformedSentence() {
        parseSentence("I LIKE MONKEYS. ALSO, I EAT GLUE.");
        assertNull(vesselMessages);
        assertNotNull(unrecognizedMessageEvent);
        assertNotNull(unrecognizedMessage);
        assertEquals(MessageFailure.UNRECOGNIZED_SENTENCE, messageFailure);
    }

    /**
     * Throway test to see how Garmin's timestamps work.
     */
    @Test
    public void testGarminDateStamps() {
        LocalTime time;

        time = LocalTime.ofSecondOfDay(25712211 / 1000);
        log.info(time.toString());
        time = LocalTime.ofSecondOfDay(25712251 / 1000);
        log.info(time.toString());
        time = LocalTime.ofSecondOfDay(25712380 / 1000);
        log.info(time.toString());
        time = LocalTime.ofSecondOfDay(25712397 / 1000);
        log.info(time.toString());
        time = LocalTime.ofSecondOfDay(25712424 / 1000);
        log.info(time.toString());
        time = LocalTime.ofSecondOfDay(25712461 / 1000);
        log.info(time.toString());
        time = LocalTime.ofSecondOfDay(25712543 / 1000);
        log.info(time.toString());
        time = LocalTime.ofSecondOfDay(25712689 / 1000);
        log.info(time.toString());
        time = LocalTime.ofSecondOfDay(25712781 / 1000);
        log.info(time.toString());
    }

    /**
     * Posts an event on to the event bus.
     * @param sentence
     */
    private void parseSentence(String sentence) {
        NMEA0183SentenceDiscovered event = new NMEA0183SentenceDiscovered(sentence);
        eventBus.post(event);
    }

    /*
     * Event bus event handler methods
     */

    @Subscribe
    public void handleVesselMessagesDiscoveredEvent(VesselMessagesDiscovered event) {
        log.info("vessel messages discovered: {}", event);
        vesselMessagesEvent = event;
        vesselMessages = event.getVesselMessages();
    }

    @Subscribe
    public void handleUnrecognizedMessageDiscovered(UnrecognizedMessageDiscovered event) {
        unrecognizedMessageEvent = event;
        unrecognizedMessage = unrecognizedMessageEvent.getUnrecognizedMessage();
        messageFailure = unrecognizedMessage.getFailureMode();
    }
}
