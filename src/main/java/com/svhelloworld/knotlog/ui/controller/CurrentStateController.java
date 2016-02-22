package com.svhelloworld.knotlog.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.svhelloworld.knotlog.events.StartNMEA0183SimulationRequest;
import com.svhelloworld.knotlog.events.StopNMEA0183SimulationRequest;
import com.svhelloworld.knotlog.i18n.BabelFish;
import com.svhelloworld.knotlog.messages.GPSPosition;
import com.svhelloworld.knotlog.messages.PositionFormat;
import com.svhelloworld.knotlog.messages.ValidVesselMessage;
import com.svhelloworld.knotlog.messages.WaterDepth;
import com.svhelloworld.knotlog.messages.WindSpeed;
import com.svhelloworld.knotlog.ui.screen.CurrentStateScreen;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Controller for the {@link CurrentStateScreen}. This controller will act as session state
 * for current conditions being reported by vessel instruments.
 */
@Component
public class CurrentStateController {

    private static Logger log = LoggerFactory.getLogger(CurrentStateController.class);

    private StringProperty waterDepthLabel = new SimpleStringProperty();

    private StringProperty waterDepth = new SimpleStringProperty();

    private StringProperty windSpeedLabel = new SimpleStringProperty();

    private StringProperty windSpeed = new SimpleStringProperty();

    private StringProperty positionLabel = new SimpleStringProperty();

    private StringProperty position = new SimpleStringProperty();

    private final EventBus eventBus;

    /*
     * wind direction
     * speed over ground
     * altitude
     * water temperature
     * rudder angle
     * vessel heading
     */

    /**
     * Constructor
     */
    @Autowired
    public CurrentStateController(EventBus eventBus) {
        log.info("initializing");
        this.eventBus = eventBus;
        eventBus.register(this);
        waterDepthLabel.set(BabelFish.localizeKey("name.water.depth"));
        windSpeedLabel.set(BabelFish.localizeKey("name.wind.speed"));
        positionLabel.set(BabelFish.localizeKey("name.gps.position"));
    }

    /**
     * Handle incoming parsed {@link ValidVesselMessage}s from the event bus.
     * @param message
     */
    @Subscribe
    public void handleVesselMessages(ValidVesselMessage message) {
        log.debug("handleMessage {}", message);
        if (message instanceof WaterDepth) {
            updateWaterDepth((WaterDepth) message);
        } else if (message instanceof WindSpeed) {
            updateWindSpeed((WindSpeed) message);
        } else if (message instanceof GPSPosition) {
            updatePosition((GPSPosition) message);
        }
    }

    /**
     * Update position property
     * @param message
     */
    private void updatePosition(GPSPosition message) {
        String text = PositionFormat.DEGREES_MINUTES.format(message);
        updateProperty(position, text);
    }

    /**
     * Update wind speed property
     * @param message
     */
    private void updateWindSpeed(WindSpeed message) {
        String text = String.format("%.1f %s", message.getSpeed(), BabelFish.localize(message.getSpeedUnit()));
        updateProperty(windSpeed, text);
    }

    /**
     * Update water depth property
     * @param message
     */
    private void updateWaterDepth(WaterDepth message) {
        String text = String.format("%.1f %s", message.getDistance(), BabelFish.localize(message.getDistanceUnit()));
        updateProperty(waterDepth, text);
    }

    /**
     * Updates property.
     * @param message
     */
    private void updateProperty(StringProperty property, String message) {
        log.debug("update property: {}", message);
        try {
            /*
             * We have to make sure this update takes place on a JavaFX managed thread
             */
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    property.set(message);
                }
            });
        } catch (IllegalStateException e) {
            log.warn("setting property values outside of the JavaFX framework");
            /*
             * This means the JavaFX toolkit hasn't been booted up. Most likely, we're running
             * unit tests against the controller so just set the property value outside of the
             * JavaFX threading.
             */
            property.set(message);
        }

    }

    /**
     * Starting the parsing!
     */
    public void startParsing() {
        eventBus.post(new StartNMEA0183SimulationRequest());
    }

    /**
     * Stop the parsing.
     */
    public void stopParsing() {
        eventBus.post(new StopNMEA0183SimulationRequest());
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

    public String getWaterDepthLabel() {
        return waterDepthLabel.get();
    }

    public StringProperty waterDepthLabelProperty() {
        return waterDepthLabel;
    }

    public StringProperty windSpeedLabelProperty() {
        return windSpeedLabel;
    }

    public String getWindSpeedLabel() {
        return windSpeedLabel.get();
    }

    public StringProperty positionLabelProperty() {
        return positionLabel;
    }

    public String getPositionLabel() {
        return positionLabel.get();
    }

    public StringProperty positionProperty() {
        return position;
    }

    public String getPosition() {
        return position.get();
    }

}
