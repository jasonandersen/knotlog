package com.svhelloworld.knotlog.engine.parse;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.Subscribe;
import com.svhelloworld.knotlog.domain.messages.UnrecognizedMessage;
import com.svhelloworld.knotlog.domain.messages.ValidVesselMessage;
import com.svhelloworld.knotlog.engine.sources.ClassPathFileSource;
import com.svhelloworld.knotlog.engine.sources.NMEA0183StreamedSource;
import com.svhelloworld.knotlog.engine.sources.StreamedSource;
import com.svhelloworld.knotlog.test.BaseIntegrationTest;

/**
 * Test how we handle new NMEA0183 sources based on files.
 */
public class NMEA0183FileSourceTest extends BaseIntegrationTest {

    private static Logger log = LoggerFactory.getLogger(NMEA0183FileSourceTest.class);

    private static final String TEST_FEED = "com/svhelloworld/knotlog/engine/parse/NMEA0183TestFeed.csv";

    private static final String GARMIN_DIAGNOSTIC_FEED = "com/svhelloworld/knotlog/engine/parse/GarminDiagFeed.csv";

    private static final String GARMIN_DIAGNOSTIC_CORRUPTED_FEED = "com/svhelloworld/knotlog/engine/parse/GarminDiagFeedCorruptedData.csv";

    private int messagesCount = 0;

    private int unrecognizedMessagesCount = 0;

    private StreamedSource source;

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        if (source != null) {
            source.close();
        }
    }

    @Test
    public void testSmallFeed() {
        source = new ClassPathFileSource(TEST_FEED);
        post(new NMEA0183StreamedSource(source));
        assertEquals(17, messagesCount);
        assertEquals(2, unrecognizedMessagesCount);
    }

    @Test
    public void testGarminDiagnosticsFeed() {
        source = new ClassPathFileSource(GARMIN_DIAGNOSTIC_FEED);
        post(new NMEA0183StreamedSource(source));
        assertEquals(99662, messagesCount);
        assertEquals(90959, unrecognizedMessagesCount);
    }

    @Test
    public void testGarminCorruptedDiagnosticFeed() {
        source = new ClassPathFileSource(GARMIN_DIAGNOSTIC_CORRUPTED_FEED);
        post(new NMEA0183StreamedSource(source));
        assertEquals(37743, messagesCount);
        assertEquals(23558, unrecognizedMessagesCount);
    }

    /*
     * Test that closed sources get removed from the NMEA0183SourcesHandler
     */

    /*
     * Event bus event handler methods
     */

    @SuppressWarnings("unused")
    @Subscribe
    public void vesselMessagesDiscovered(ValidVesselMessage message) {
        messagesCount++;
        if (messagesCount % 1000 == 0) {
            log.info("parsed {} vessel messages", messagesCount);
        }
    }

    @Subscribe
    public void handleUnrecognizedMessageDiscoveredEvent(UnrecognizedMessage message) {
        log.debug("unrecognized message sentence: {} {}", message.getFailureMode(),
                message.getSentenceFields());
        unrecognizedMessagesCount++;
        if (unrecognizedMessagesCount % 1000 == 0) {
            log.info("parsed {} unrecognized messages", unrecognizedMessagesCount);
        }
    }

}
