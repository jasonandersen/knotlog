package com.svhelloworld.knotlog.messages.validate;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import com.svhelloworld.knotlog.messages.TimeOfDayZulu;

/**
 * Will validate {@link TimeOfDayZulu} messages against expected values.
 */
public class TimeOfDayZuluValidator extends MessageAttributeValidator<TimeOfDayZulu> {

    private static final String TIME_OF_DAY = "time of day (GMT)";

    /**
     * @see com.svhelloworld.knotlog.messages.validate.MessageAttributeValidator#buildActualAttributes(com.svhelloworld.knotlog.messages.VesselMessage, java.util.Map)
     */
    @Override
    protected void buildActualAttributes(TimeOfDayZulu message,
            Map<String, MessageAttributeValidator<TimeOfDayZulu>.Attribute> attributes) {

        Instant instant = Instant.ofEpochMilli(message.getTimeMilliseconds());
        Date date = Date.from(instant);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String actualTimeOfDay = format.format(date);
        setActualAttributeValue(TIME_OF_DAY, attributes, actualTimeOfDay);
    }

}
