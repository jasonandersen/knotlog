package com.svhelloworld.knotlog.cucumber;

import java.util.Map;

import com.svhelloworld.knotlog.messages.Altitude;
import com.svhelloworld.knotlog.messages.GPSPosition;
import com.svhelloworld.knotlog.messages.MagneticVariation;
import com.svhelloworld.knotlog.messages.MessageAttributeValidator;
import com.svhelloworld.knotlog.messages.PositionFormat;
import com.svhelloworld.knotlog.messages.PositionPrecision;
import com.svhelloworld.knotlog.messages.TimeOfDayZulu;
import com.svhelloworld.knotlog.messages.VesselHeading;
import com.svhelloworld.knotlog.messages.VesselMessage;
import com.svhelloworld.knotlog.messages.WaterDepth;
import com.svhelloworld.knotlog.messages.WindDirection;
import com.svhelloworld.knotlog.messages.WindSpeed;

/**
 * Attributes specific to a {@link VesselMessage} used for testing and validation.
 */
public enum MessageAttributes {

    /*
     * We track three data points for each VesselMessage subclass in this enum. These data points
     * are used to make acceptance testing smoother and easier to write using BDD.
     * 
     *  1. Description - this is the plain text description for this type of message used in BDD
     *     statements.
     *     
     *  2. Type - this is the actual class for the particular VesselMessage subclass.
     *  
     *  3. Validator - this is a subclass of MessageAttributeValidator<? extends VesselMessage>.
     *     This object will take a map of strings that specify the message attribute values that are
     *     expected to return and validate them against the actual values. I use anonymous classes
     *     (not my favorite) because the subclass of MessageAttributeValidator really only needs
     *     to implement one method and that one is usually just a couple lines of code.
     */

    ALTITUDE(
            "altitude",
            Altitude.class,
            new MessageAttributeValidator<Altitude>() {
                @Override
                protected void buildActualAttributes(Altitude message, Map<String, Attribute> attributes) {
                    String actualValue = String.format("%.1f %s", message.getDistance(), message.getDistanceUnit().toString());
                    setActualAttributeValue("altitude", attributes, actualValue);
                }
            }),

    GPS_POSITION(
            "GPS position",
            GPSPosition.class,
            new MessageAttributeValidator<GPSPosition>() {
                @Override
                protected void buildActualAttributes(GPSPosition message, Map<String, Attribute> attributes) {
                    String actualPosition = PositionFormat.DEGREES_MINUTES.format(message);
                    setActualAttributeValue("position", attributes, actualPosition);
                }
            }),

    MAGNETIC_VARIATION(
            "magnetic variation",
            MagneticVariation.class,
            new MessageAttributeValidator<MagneticVariation>() {
                @Override
                protected void buildActualAttributes(MagneticVariation message, Map<String, Attribute> attributes) {
                    setActualAttributeValue("magnetic variation", attributes, message.getDisplayMessage());
                }
            }),

    POSITION_PRECISION(
            "position precision",
            PositionPrecision.class,
            new MessageAttributeValidator<PositionPrecision>() {
                @Override
                protected void buildActualAttributes(PositionPrecision message, Map<String, Attribute> attributes) {
                    String actualValue = String.format("%.1f %s", message.getDistance(), message.getDistanceUnit().toString());
                    setActualAttributeValue("position precision", attributes, actualValue);
                }
            }),

    TIME_OF_DAY(
            "time of day",
            TimeOfDayZulu.class,
            new MessageAttributeValidator<TimeOfDayZulu>() {
                @Override
                protected void buildActualAttributes(TimeOfDayZulu message, Map<String, Attribute> attributes) {
                    setActualAttributeValue("time", attributes, message.getTimeOfDay());
                    setActualAttributeValue("date", attributes, message.getDate());
                    setActualAttributeValue("time zone", attributes, message.getTimeZone());
                }
            }),

    VESSEL_HEADING(
            "vessel heading",
            VesselHeading.class,
            new MessageAttributeValidator<VesselHeading>() {
                @Override
                protected void buildActualAttributes(VesselHeading message, Map<String, Attribute> attributes) {
                    setActualAttributeValue("vessel heading", attributes, message.toString());
                }
            }),

    WATER_DEPTH(
            "water depth",
            WaterDepth.class,
            new MessageAttributeValidator<WaterDepth>() {
                @Override
                protected void buildActualAttributes(WaterDepth message, Map<String, Attribute> attributes) {
                    String actualValue = String.format("%.1f %s", message.getDistance(), message.getDistanceUnit().toString());
                    setActualAttributeValue("water depth", attributes, actualValue);
                }
            }),

    WIND_DIRECTION(
            "wind direction",
            WindDirection.class,
            new MessageAttributeValidator<WindDirection>() {
                @Override
                protected void buildActualAttributes(WindDirection message, Map<String, Attribute> attributes) {
                    throw new UnsupportedOperationException("still haven't built this yet.");
                }
            }),

    WIND_SPEED(
            "wind speed",
            WindSpeed.class,
            new MessageAttributeValidator<WindSpeed>() {
                @Override
                protected void buildActualAttributes(WindSpeed message, Map<String, Attribute> attributes) {
                    throw new UnsupportedOperationException("still haven't built this yet.");
                }
            })

    ;

    /**
     * Finds an MessageAttributes by the description.
     * @param description
     * @return MessageAttributes with a matching description, will return null if none are found
     */
    public static MessageAttributes findByDescription(String description) {
        for (MessageAttributes attrib : MessageAttributes.values()) {
            if (attrib.getDescription().equals(description)) {
                return attrib;
            }
        }
        return null;
    }

    private final String description;

    private final Class<? extends VesselMessage> type;

    private final MessageAttributeValidator<? extends VesselMessage> validator;

    /**
     * Constructor.
     * @param description
     * @param type
     * @param validator
     */
    MessageAttributes(String description, Class<? extends VesselMessage> type,
            MessageAttributeValidator<? extends VesselMessage> validator) {
        this.description = description;
        this.type = type;
        this.validator = validator;
    }

    /**
     * @return the descriptive name of the message
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the class type of this vessel message
     */
    public Class<? extends VesselMessage> getType() {
        return type;
    }

    /**
     * @return the validator instance used to validate messages
     */
    public MessageAttributeValidator<? extends VesselMessage> getValidator() {
        return validator;
    }

}
