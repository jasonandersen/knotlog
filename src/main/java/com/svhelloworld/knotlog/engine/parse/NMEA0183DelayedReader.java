package com.svhelloworld.knotlog.engine.parse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;
import com.svhelloworld.knotlog.engine.sources.StreamedSource;

/**
 * An {@link NMEA0183Reader} that places new sentences on the event bus at delayed intervals. This enables
 * a real-time NMEA0183 simulation.
 */
public class NMEA0183DelayedReader extends NMEA0183Reader {

    private static Logger log = LoggerFactory.getLogger(NMEA0183DelayedReader.class);

    private final long delayInterval;

    /**
     * @param eventBus
     * @param source
     * @param delayPeriod the amount of delay in milliseconds between sentences posted to event bus
     */
    public NMEA0183DelayedReader(EventBus eventBus, StreamedSource source, long delayInterval) {
        super(eventBus, source);
        this.delayInterval = delayInterval;
    }

    /**
     * @see com.svhelloworld.knotlog.engine.parse.NMEA0183Reader#fetchNextLine()
     */
    @Override
    protected String fetchNextLine() {
        try {
            Thread.sleep(delayInterval);
            return getReader().readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Stop the delayed reading, clear out the queue.
     */
    public void stop() {
        log.info("parsing halted");
        throw new UnsupportedOperationException("not implemented yet!");
    }

}
