package com.svhelloworld.knotlog.ui;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.svhelloworld.knotlog.engine.parse.NMEA0183DelayedReader;
import com.svhelloworld.knotlog.engine.parse.NMEA0183Reader;
import com.svhelloworld.knotlog.engine.sources.ClassPathFileSource;
import com.svhelloworld.knotlog.engine.sources.StreamedSource;
import com.svhelloworld.knotlog.events.StartNMEA0183SimulationRequest;
import com.svhelloworld.knotlog.events.StopNMEA0183SimulationRequest;

/**
 * Simulates an actual NMEA0183 source by throwing NMEA0183 sentences onto the bus at delayed
 * intervals.
 */
@Service
public class NMEA0183Simulator {

    private static Logger log = LoggerFactory.getLogger(NMEA0183Simulator.class);

    private static final String FEED = "data/GarminDiagFeed.csv";

    private final EventBus eventBus;

    private long delay = 100;

    private ScheduledThreadPoolExecutor threadPool;

    private NMEA0183Reader reader;

    /**
     * @param eventBus
     * @param delay 
     */
    @Autowired
    public NMEA0183Simulator(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.register(this);
    }

    /**
     * Receiving a start request from the event bus.
     * @param event
     */
    @Subscribe
    public void handleStartRequest(StartNMEA0183SimulationRequest event) {
        log.info("received event to request simulation start");
        start();
    }

    /**
     * Receiving a stop request from the event bus.
     * @param event
     */
    @Subscribe
    public void handleStopRequest(StopNMEA0183SimulationRequest event) {
        log.info("received event to request simulation stop");
        stop();
    }

    /**
     * Sets the average delay between sentences.
     * @param newDelay
     */
    public void setDelay(long newDelay) {
        delay = newDelay;
    }

    /**
     * Starts the simulation.
     */
    public void start() {
        log.info("starting simulation");
        if (isRunning()) {
            log.debug("simulation is currently running, stopping current run to start a new one");
            stop();
        }
        initThreadPool();
        StreamedSource source = new ClassPathFileSource(FEED);
        reader = new NMEA0183DelayedReader(eventBus, source, 100);
        threadPool.schedule(reader, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * Clean up upon application exit.
     */
    @PreDestroy
    public void tearDown() {
        log.info("tearing down");
        if (isRunning()) {
            stop();
        }
    }

    /**
     * @return true if the simulator is currently running
     */
    public boolean isRunning() {
        return reader == null ? false : reader.isReading();
    }

    /**
     * Stops the simulation.
     */
    public void stop() {
        log.info("stopping simulation");
        if (!(threadPool == null)) {
            threadPool.shutdownNow();
            threadPool.getQueue().clear();
        }
    }

    /**
     * Initializes thread pool.
     */
    private void initThreadPool() {
        log.debug("initializing thread pool");
        threadPool = new ScheduledThreadPoolExecutor(1);
        threadPool.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
        threadPool.setRemoveOnCancelPolicy(true);
    }

}
