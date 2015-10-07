package com.svhelloworld.knotlog.cucumber;

import com.svhelloworld.knotlog.messages.Altitude;
import com.svhelloworld.knotlog.messages.GPSPosition;
import com.svhelloworld.knotlog.messages.PositionPrecision;
import com.svhelloworld.knotlog.messages.TimeOfDayZulu;
import com.svhelloworld.knotlog.messages.VesselMessage;
import com.svhelloworld.knotlog.messages.validate.AltitudeValidator;
import com.svhelloworld.knotlog.messages.validate.GPSPositionValidator;
import com.svhelloworld.knotlog.messages.validate.MessageAttributeValidator;
import com.svhelloworld.knotlog.messages.validate.PositionPrecisionValidator;
import com.svhelloworld.knotlog.messages.validate.TimeOfDayZuluValidator;

/**
 * Attributes specific to a {@link VesselMessage} used for testing.
 */
public enum MessageAttributes {
    ALTITUDE("altitude", Altitude.class, new AltitudeValidator()),
    GPS_POSITION("GPS position", GPSPosition.class, new GPSPositionValidator()),
    POSITION_PRECISION("position precision", PositionPrecision.class, new PositionPrecisionValidator()),
    TIME_OF_DAY("time of day", TimeOfDayZulu.class, new TimeOfDayZuluValidator());

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
