package com.svhelloworld.knotlog.engine.parse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.svhelloworld.knotlog.engine.sources.StreamedSource;

/**
 * Testing the {@link NMEA0183DelayedReader} class.
 */
public class NMEA0183DelayedReaderTest {

    private static Logger log = LoggerFactory.getLogger(NMEA0183DelayedReaderTest.class);

    private NMEA0183DelayedReader reader;

    private EventBus eventBus;

    private List<SentenceReceivedEvent> events;

    @Before
    public void setup() {
        eventBus = new EventBus();
        eventBus.register(this);
        events = new ArrayList<SentenceReceivedEvent>();
    }

    @Test
    public void testSentenceIsNotPostedImmediately() {
        initReader(500);
        reader.handleLine("I LIKE MONKEYS");
        assertTrue(events.isEmpty());
    }

    @Test
    public void testSentenceIsPostedAfter500Ms() throws InterruptedException {
        initReader(500);
        reader.handleLine("I LIKE MONKEYS");
        Thread.sleep(1000);
        assertFalse(events.isEmpty());
    }

    @Test
    public void testEachSentenceIsDelayedInSequence() throws InterruptedException {
        initReader(500);
        reader.handleLine("this is the first sentence");
        reader.handleLine("this is the second sentence");
        Thread.sleep(600);
        assertEquals(1, events.size());
        Thread.sleep(600);
        assertEquals(2, events.size());
    }

    @Subscribe
    public void sentenceReceived(NMEA0183Sentence sentence) {
        log.info("sentence received {}", sentence);
        events.add(new SentenceReceivedEvent(System.currentTimeMillis(), sentence));
    }

    /**
     * Initialize reader
     * @param millis delay in milliseconds
     */
    private void initReader(int millis) {
        reader = new NMEA0183DelayedReader(eventBus, mockSource(), millis);
    }

    /**
     * @return a mocked {@link StreamedSource} that doesn't do anything.
     */
    private StreamedSource mockSource() {
        return new StreamedSource() {

            @Override
            public InputStream open() {
                return null;
            }

            @Override
            public void close() {
                //noop
            }
        };
    }

    /**
     * Records the event of receiving an NMEA0183 sentence.
     */
    private class SentenceReceivedEvent {
        long timestamp;
        NMEA0183Sentence sentence;

        SentenceReceivedEvent(long timestamp, NMEA0183Sentence sentence) {
            this.sentence = sentence;
            this.timestamp = timestamp;
        }
    }
}
