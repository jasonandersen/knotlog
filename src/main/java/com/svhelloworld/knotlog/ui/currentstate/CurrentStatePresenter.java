package com.svhelloworld.knotlog.ui.currentstate;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.svhelloworld.knotlog.domain.messages.GPSPosition;
import com.svhelloworld.knotlog.domain.messages.SpeedRelativeToGround;
import com.svhelloworld.knotlog.domain.messages.ValidVesselMessage;
import com.svhelloworld.knotlog.domain.messages.VesselHeading;
import com.svhelloworld.knotlog.domain.messages.VesselMessage;
import com.svhelloworld.knotlog.domain.messages.VesselMessageSource;
import com.svhelloworld.knotlog.domain.messages.WaterDepth;
import com.svhelloworld.knotlog.domain.messages.WindSpeed;
import com.svhelloworld.knotlog.events.StartNMEA0183SimulationRequest;
import com.svhelloworld.knotlog.events.StopNMEA0183SimulationRequest;
import com.svhelloworld.knotlog.measure.AngleUnit;
import com.svhelloworld.knotlog.measure.DistanceUnit;
import com.svhelloworld.knotlog.measure.LatitudinalHemisphere;
import com.svhelloworld.knotlog.measure.LongitudinalHemisphere;
import com.svhelloworld.knotlog.measure.MeasurementBasis;
import com.svhelloworld.knotlog.measure.SpeedUnit;
import com.svhelloworld.knotlog.ui.UI;
import com.svhelloworld.knotlog.ui.view.VesselMessageView;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Presenter for the {@link CurrentStateScreen}. This presenter will act as session state
 * for current conditions being reported by vessel instruments.
 */
@Component
public class CurrentStatePresenter {

    private static Logger log = LoggerFactory.getLogger(CurrentStatePresenter.class);

    private final EventBus eventBus;

    private final ConversionService conversionService;

    private final ObservableList<VesselMessageView<?>> messages;

    private final ListProperty<VesselMessageView<?>> messagesProperty;

    /**
     * Constructor
     */
    @Autowired
    public CurrentStatePresenter(EventBus eventBus, ConversionService conversionService) {
        log.info("initializing");
        this.conversionService = conversionService;
        this.eventBus = eventBus;
        eventBus.register(this);

        messages = FXCollections.observableArrayList();
        messagesProperty = new SimpleListProperty<VesselMessageView<?>>(messages);
        initializeMessages();
    }

    /**
     * Handle incoming parsed {@link ValidVesselMessage}s from the event bus.
     * @param message
     */
    @Subscribe
    public void handleVesselMessages(ValidVesselMessage message) {
        log.debug("handleMessage {}", message);
        VesselMessageView<?> view = convertMessageToView(message);
        if (view != null) {
            updateCurrentStateMessages(view);
        }
    }

    /**
     * Clears out any state. Useful for testing purposes.
     */
    public void reset() {
        messages.clear();
        initializeMessages();
    }

    /**
     * Converts a {@link VesselMessage} to a {@link VesselMessageView}.
     * @param message
     * @return will return the view object if successful, if the conversion failed will return null
     */
    private VesselMessageView<?> convertMessageToView(ValidVesselMessage message) {
        VesselMessageView<?> view = null;
        try {
            view = conversionService.convert(message, VesselMessageView.class);
        } catch (Exception e) {
            /*
             * Unable to convert this message to a view, just return null.
             * FIXME - I should just be catching IllegalArgumentException because that's what gets
             * thrown from the conversion service but when I do, it doesn't seem to get caught by 
             * this catch for reasons I'm too lazy to figure out right now. So for now, we're just
             * catching all exceptions.
             */
        }
        return view;
    }

    /**
     * Add this view to the current messages.
     * @param view
     */
    private void updateCurrentStateMessages(VesselMessageView<?> view) {
        if (view == null) {
            return;
        }
        log.debug("uppdate current state messages");
        UI.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                addUniqueMessage(view);
            }

        });
    }

    /**
     * If this message type already exists in the list, replace it. Otherwise add it.
     * @param view
     */
    private void addUniqueMessage(VesselMessageView<?> view) {
        Class<?> messageClass = view.getVesselMessage().getClass();
        for (int index = 0; index < messages.size(); index++) {
            /*
             * attempt to find the VesselMessage type already in the list and replace it
             */
            VesselMessage currentMessage = messages.get(index).getVesselMessage();
            if (messageClass.equals(currentMessage.getClass())) {
                messages.set(index, view);
                return;
            }
        }
        /*
         * if the message type was not found, add it
         */
        messages.add(view);
    }

    /**
     * Create initial messages to load up the display in the proper order.
     */
    private void initializeMessages() {
        VesselMessageSource source = VesselMessageSource.NMEA0183;
        Instant now = Instant.now();
        WaterDepth waterDepth = new WaterDepth(source, now, 0.0f, DistanceUnit.FEET);
        SpeedRelativeToGround sog = new SpeedRelativeToGround(source, now, 0.0f, SpeedUnit.KNOTS);
        GPSPosition position = new GPSPosition(source, now, "0000.00", LatitudinalHemisphere.NORTH, "00000.00",
                LongitudinalHemisphere.WEST);
        WindSpeed windSpeed = new WindSpeed(source, now, 0.0f, SpeedUnit.KNOTS, MeasurementBasis.RELATIVE);
        VesselHeading heading = new VesselHeading(source, now, 0.0f, AngleUnit.DEGREES_MAGNETIC);

        handleVesselMessages(waterDepth);
        handleVesselMessages(windSpeed);
        handleVesselMessages(position);
        handleVesselMessages(heading);
        handleVesselMessages(sog);
    }

    /**
     * Starting the parsing!
     */
    public void startSimulation() {
        eventBus.post(new StartNMEA0183SimulationRequest());
    }

    /**
     * Stop the parsing.
     */
    public void stopSimulation() {
        eventBus.post(new StopNMEA0183SimulationRequest());
    }

    /*
     * Accessor methods
     */

    public List<VesselMessageView<?>> getCurrentStateMessages() {
        return messages;
    }

    public ListProperty<VesselMessageView<?>> currentStateMessagesProperty() {
        return messagesProperty;
    }

}
