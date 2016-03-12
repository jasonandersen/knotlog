package com.svhelloworld.knotlog.engine.parse;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.svhelloworld.knotlog.engine.sources.ClassPathFileSource;
import com.svhelloworld.knotlog.engine.sources.StreamedSource;

/**
 * Testing the {@link NMEA0183DelayedReader} class.
 */
public class NMEA0183DelayedReaderTest {

    private static Logger log = LoggerFactory.getLogger(NMEA0183DelayedReaderTest.class);

    private static final String FEED = "com/svhelloworld/knotlog/engine/parse/GarminDiagFeed.csv";

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
    public void testSentenceIsPostedAfter500Ms() throws InterruptedException {
        initReader(500);
        reader.processLine("I LIKE MONKEYS");
        Thread.sleep(1000);
        assertFalse(events.isEmpty());
    }

    @Test
    public void testStop() throws InterruptedException {
        ScheduledThreadPoolExecutor threadPool = new ScheduledThreadPoolExecutor(1);
        StreamedSource source = new ClassPathFileSource(FEED);
        reader = new NMEA0183DelayedReader(eventBus, source, 100);
        threadPool.schedule(reader, 0, TimeUnit.MILLISECONDS);
        Thread.sleep(100);
        assertTrue(reader.isReading());
        reader.stop();
        Thread.sleep(100);
        assertFalse(reader.isReading());

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
    @SuppressWarnings("unused")
    private class SentenceReceivedEvent {
        long timestamp;
        NMEA0183Sentence sentence;

        SentenceReceivedEvent(long timestamp, NMEA0183Sentence sentence) {
            this.sentence = sentence;
            this.timestamp = timestamp;
        }
    }
}
