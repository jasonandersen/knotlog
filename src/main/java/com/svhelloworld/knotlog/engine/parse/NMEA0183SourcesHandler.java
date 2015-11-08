package com.svhelloworld.knotlog.engine.parse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.svhelloworld.knotlog.engine.sources.NMEA0183StreamedSource;

/**
 * Handles NMEA0183 sources and connects them to an {@link NMEA0183Reader}.
 */
@Service
public class NMEA0183SourcesHandler {

    private static Logger log = LoggerFactory.getLogger(NMEA0183SourcesHandler.class);

    private EventBus eventBus;

    /*
     * Event bus event handler methods
     */

    @Subscribe
    public void handleNMEA0183SourceDiscoveredEvent(NMEA0183StreamedSource source) {
        /*
         * FIXME - this is very simple minded and will eventually need some multi-threading to
         * handle real time blocking streamed sources.
         */
        log.info("NMEA0183 source discovered;source={}", source);
        NMEA0183Reader reader = new NMEA0183Reader(eventBus, source);
        reader.start();
    }

    /**
     * Inject the event bus.
     * @param eventBus
     */
    @Autowired
    private void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.register(this);
    }

}
