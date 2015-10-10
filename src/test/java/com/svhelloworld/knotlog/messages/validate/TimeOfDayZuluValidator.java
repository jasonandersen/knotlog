package com.svhelloworld.knotlog.messages.validate;

import java.util.Map;

import com.svhelloworld.knotlog.messages.TimeOfDayZulu;

/**
 * Will validate {@link TimeOfDayZulu} messages against expected values.
 */
public class TimeOfDayZuluValidator extends MessageAttributeValidator<TimeOfDayZulu> {

    /**
     * This is a placeholder in the BDD tests to indicate that the expected date is today's date (in YYYY/MM/DD format)
     */
    private static final String TODAY = "[today]";

    private static final String TIME_OF_DAY = "time of day";

    private static final String DATE = "date";

    private static final String TIME_ZONE = "time zone";

    /**
     * @see com.svhelloworld.knotlog.messages.validate.MessageAttributeValidator#buildActualAttributes(com.svhelloworld.knotlog.messages.VesselMessage, java.util.Map)
     */
    @Override
    protected void buildActualAttributes(TimeOfDayZulu message, Map<String, Attribute> attributes) {
        setActualAttributeValue(TIME_OF_DAY, attributes, message.getTimeOfDay());
        setActualAttributeValue(DATE, attributes, message.getDate());
        setActualAttributeValue(TIME_ZONE, attributes, message.getTimeZone());
    }

}
