package com.svhelloworld.knotlog.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.svhelloworld.knotlog.engine.parse.NMEA0183Sentence;

/**
 * Tests the {@link NMEA0183Simulator} class.
 */
public class NMEA0183SimulatorTest {

    private static Logger log = LoggerFactory.getLogger(NMEA0183SimulatorTest.class);

    private static final long DELAY = 200;

    private EventBus eventBus;

    private NMEA0183Simulator sim;

    private List<NMEA0183Sentence> sentences;

    @Before
    public void setup() {
        eventBus = new EventBus();
        eventBus.register(new SentenceListener());
        sentences = new ArrayList<NMEA0183Sentence>();
        sim = new NMEA0183Simulator(eventBus);
        sim.setDelay(DELAY);
    }

    @After
    public void tearDown() {
        log.info("tearing down test");
        sim.stop();
    }

    @Test
    public void testSimulation() throws InterruptedException {
        sim.start();
        assertTrue(sentences.isEmpty());
        Thread.sleep(DELAY * 2 + 300);
        int size = sentences.size();
        assertTrue(size > 0);
        Thread.sleep(DELAY + 100);
        assertTrue(sentences.size() > size);
    }

    @Test
    public void testSimulationStop() throws InterruptedException {
        sim.start();
        Thread.sleep(DELAY * 2 + 500);
        int size = sentences.size();
        sim.stop();
        assertTrue(size > 0);
        Thread.sleep(DELAY * 3);
        assertEquals(size, sentences.size());
    }

    @Test
    public void testMultipleStarts() throws InterruptedException {
        sim.start();
        Thread.sleep(DELAY * 3);
        sim.start();
        Thread.sleep(DELAY * 3);
    }

    @Test
    public void testIsRunning() throws InterruptedException {
        sim.start();
        Thread.sleep(DELAY + 100);
        assertTrue(sim.isRunning());
        Thread.sleep(DELAY * 3);
        assertTrue(sim.isRunning());
    }

    /*
     * tests:
     * 
     * make sure stop() actually stops it
     * isRunning()?
     * stop() before start()
     * start() and then start()
     * stop() and then stop()
     */

    private class SentenceListener {
        @Subscribe
        public void handleSentence(NMEA0183Sentence sentence) {
            log.debug(sentence.toString());
            sentences.add(sentence);
        }
    }
}
