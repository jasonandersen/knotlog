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
public class TimeOfDayZuluValidator extends BaseValidator implements MessageValidator<TimeOfDayZulu> {

    private static final String TIME_OF_DAY = "time of day (GMT)";

    /**
     * @see com.svhelloworld.knotlog.messages.validate.MessageValidator#assertValid(com.svhelloworld.knotlog.messages.VesselMessage, java.util.Map)
     */
    @Override
    public void assertValid(TimeOfDayZulu message, Map<String, String> expectedAttributes) {
        assertTimeOfDayValid(message, expectedAttributes);
        assertSource(message, expectedAttributes);
    }

    /**
     * @param message
     * @param expectedAttributes
     */
    private void assertTimeOfDayValid(TimeOfDayZulu message, Map<String, String> expectedAttributes) {
        Instant instant = Instant.ofEpochMilli(message.getTimeMilliseconds());
        Date date = Date.from(instant);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String actualTimeOfDay = format.format(date);
        assertAttribute(message, expectedAttributes, TIME_OF_DAY, actualTimeOfDay);
    }

}
