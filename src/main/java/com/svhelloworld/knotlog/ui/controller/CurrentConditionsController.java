package com.svhelloworld.knotlog.ui.controller;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.svhelloworld.knotlog.engine.parse.NMEA0183DelayedReader;
import com.svhelloworld.knotlog.engine.sources.ClassPathFileSource;
import com.svhelloworld.knotlog.engine.sources.StreamedSource;
import com.svhelloworld.knotlog.messages.ValidVesselMessage;
import com.svhelloworld.knotlog.messages.VesselMessage;
import com.svhelloworld.knotlog.messages.WaterDepth;
import com.svhelloworld.knotlog.messages.WindSpeed;
import com.svhelloworld.knotlog.ui.screen.CurrentConditionsScreen;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Controller for the {@link CurrentConditionsScreen}.
 */
@Component
public class CurrentConditionsController {

    private static Logger log = LoggerFactory.getLogger(CurrentConditionsController.class);

    private static final String GARMIN_DIAGNOSTIC_FEED = "data/GarminDiagFeed.csv";

    private StringProperty waterDepth;

    private StringProperty windSpeed;

    private NMEA0183DelayedReader reader;

    @Autowired
    private EventBus eventBus;

    /**
     * Constructor
     */
    public CurrentConditionsController() {
        log.info("initializing");
        waterDepth = new SimpleStringProperty();
        windSpeed = new SimpleStringProperty();
    }

    /**
     * Post construction, register on event bus
     */
    @PostConstruct
    private void postConstruct() {
        log.info("postConstruct method");
        eventBus.register(this);
    }

    /**
     * Handle incoming parsed {@link ValidVesselMessage}s from the event bus.
     * @param message
     */
    @Subscribe
    public void handleVesselMessages(ValidVesselMessage message) {
        log.debug("handleMessage {}", message);
        if (message instanceof WaterDepth) {
            updateProperty(waterDepth, message);
        } else if (message instanceof WindSpeed) {
            updateProperty(windSpeed, message);
        }
    }

    /**
     * Updates property.
     * @param message
     */
    private void updateProperty(StringProperty property, VesselMessage message) {
        log.debug("update property: {}", message);
        /*
         * We have to make sure this update takes place on a JavaFX managed thread
         */
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                log.debug("updating water depth on JavaFX thread");
                property.set(message.toString());
            }
        });
    }

    /**
     * Starting the parsing!
     */
    public void startParsing() {
        StreamedSource source = new ClassPathFileSource(GARMIN_DIAGNOSTIC_FEED);
        reader = new NMEA0183DelayedReader(eventBus, source, 100);
        reader.start();
    }

    /**
     * Stop the parsing and throw away any unparsed lines.
     */
    public void stopParsing() {
        reader.stop();
    }

    /*
     * Property accessor methods
     */

    public String getWaterDepth() {
        return waterDepth.get();
    }

    public StringProperty waterDepthProperty() {
        return waterDepth;
    }

    public String getWindSpeed() {
        return windSpeed.get();
    }

    public StringProperty windSpeedProperty() {
        return windSpeed;
    }

}
