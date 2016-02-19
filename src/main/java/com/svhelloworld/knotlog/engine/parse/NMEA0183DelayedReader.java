package com.svhelloworld.knotlog.engine.parse;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;
import com.svhelloworld.knotlog.engine.sources.StreamedSource;

/**
 * An {@link NMEA0183Reader} that places new sentences on the event bus at delayed intervals.
 */
public class NMEA0183DelayedReader extends NMEA0183Reader {

    private static Logger log = LoggerFactory.getLogger(NMEA0183DelayedReader.class);

    private final ScheduledThreadPoolExecutor threadPool;

    private final int delayPeriod;

    private AtomicLong cumulativeDelay;

    /**
     * @param eventBus
     * @param source
     * @param delayPeriod the amount of delay in milliseconds between sentences posted to event bus
     */
    public NMEA0183DelayedReader(EventBus eventBus, StreamedSource source, int delayPeriod) {
        super(eventBus, source);
        this.threadPool = new ScheduledThreadPoolExecutor(1);
        this.delayPeriod = delayPeriod;
        cumulativeDelay = new AtomicLong(0L);
    }

    /**
     * Handles each line by scheduling the posting of that line to the event bus on a delay.
     * @see com.svhelloworld.knotlog.engine.parse.NMEA0183Reader#handleLine(java.lang.String)
     */
    @Override
    protected void handleLine(String line) {
        log.debug("handling line: {}", line);
        NMEA0183Sentence sentence = new NMEA0183Sentence(line);
        long delay = cumulativeDelay.addAndGet(delayPeriod);
        threadPool.schedule(new Runnable() {
            @Override
            public void run() {
                log.debug("threadPool.size: {}; posting delayed sentence to event bus: {}", threadPool.getQueue().size(), line);
                getEventBus().post(sentence);
            }

        }, delay, TimeUnit.MILLISECONDS);
    }

    /**
     * Stop the delayed reading, clear out the queue.
     */
    public void stop() {
        threadPool.shutdownNow();
    }

}
