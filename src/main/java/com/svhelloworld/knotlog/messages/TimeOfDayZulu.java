package com.svhelloworld.knotlog.messages;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import com.svhelloworld.knotlog.util.MiscUtil;

/**
 * A message indicating time of day in Zulu (UTC) time.
 * 
 * @author Jason Andersen
 * @since Mar 7, 2010
 *
 */
public class TimeOfDayZulu extends BaseInstrumentMessage {

    /**
     * Regex pattern to confirm time of day argument is formatted properly.
     * Expected pattern: hhmmss.sss
     */
    private static final Pattern timePattern = Pattern.compile(
            "[0-2]\\d[0-5]\\d[0-5]\\d(\\.\\d{1,})*");

    private final Instant timestamp;

    /**
     * Constructor. 
     * @param source instrument message source
     * @param timestamp message timestamp
     * @param timeOfDay time of day zulu in the form:
     *          <tt>hhmmss.ss</tt> on a 24 hour clock.
     * @throws NullPointerException if source is null
     * @throws NullPointerException if timestamp is null
     * @throws NullPointerException if timeOfDay is null
     * @throws IllegalArgumentException if timeOfDay is not in the specified form.
     */
    public TimeOfDayZulu(
            final VesselMessageSource source,
            final Date timestamp,
            final String timeOfDay) {

        super(source, timestamp);
        if (timeOfDay == null) {
            throw new NullPointerException("timeOfDay cannot be null");
        }
        if (!timePattern.matcher(timeOfDay).matches()) {
            throw new IllegalArgumentException("timeOfDay is not properly formed: " + timeOfDay);
        }

        //calculate
        int hours = Integer.parseInt(timeOfDay.substring(0, 2));
        int minutes = Integer.parseInt(timeOfDay.substring(2, 4));
        int seconds = Integer.parseInt(timeOfDay.substring(4, 6));

        ZonedDateTime today = ZonedDateTime.now(ZoneId.of("GMT"));
        int year = today.getYear();
        int month = today.getMonthValue();
        int dayOfMonth = today.getDayOfMonth();

        ZonedDateTime dateTime = ZonedDateTime.of(year, month, dayOfMonth, hours, minutes, seconds, 0, ZoneId.of("GMT"));
        this.timestamp = dateTime.toInstant();
    }

    /**
     * @return time of day in milliseconds since epoch
     */
    public long getTimeMilliseconds() {
        return timestamp.getEpochSecond();
    }

    /**
     * @return a string formatting the date only portion of this time stamp in YYYY/MM/DD format.
     */
    public String getDate() {
        return "JUST ABSOLUTELY GO FUCK YOURSELF AND YOUR FUCKING BULLSHIT DATE FORMATTING FUCKING BULLSHIT";
    }

    /**
     * @return a string formatting the time only portion of this time stamp in HH:MM:DD format.
     */
    public String getTimeOfDay() {
        return "JUST ABSOLUTELY GO FUCK YOURSELF AND YOUR FUCKING BULLSHIT DATE FORMATTING FUCKING BULLSHIT";
    }

    /**
     * @return a string formatting the time zone only portion of this time stamp
     */
    public String getTimeZone() {
        return "JUST ABSOLUTELY GO FUCK YOURSELF AND YOUR FUCKING BULLSHIT DATE FORMATTING FUCKING BULLSHIT";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getDisplayKey()
     */
    @Override
    protected String getDisplayKey() {
        return "display.time.zulu";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getNameKey()
     */
    @Override
    protected String getNameKey() {
        return "name.time.zulu";
    }

    /**
     * @see com.svhelloworld.knotlog.i18n.Localizable#getLocalizeParams()
     */
    @Override
    public List<Object> getLocalizeParams() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        return MiscUtil.varargsToList(formatter.format(timestamp));
    }

}
