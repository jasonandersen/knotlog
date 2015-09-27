package com.svhelloworld.knotlog.messages;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    
    /*
     * The Java Date/Calendar/DateFormat triumverate of evil is proving
     * to be a righteous pain in the ass. I'm still not sure how I'm
     * going to store this time and keep the time zone set at UTC. For
     * now, I'm just going to deal in millisecond values and go back and
     * change this as needed.
     */
    
    /**
     * Time stored in number of milliseconds since midnight.
     */
    private final long time;
    
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
        //calculate milliseconds since midnight
        float hours = Float.parseFloat(timeOfDay.substring(0,2));
        float minutes = Float.parseFloat(timeOfDay.substring(2,4));
        float seconds = Float.parseFloat(timeOfDay.substring(4));
        
        time = (long)((100 * 60 * 60 * hours) + (100 * 60 * minutes) + (100 * seconds));
    }
    
    /**
     * @return time of day in milliseconds since midnight
     */
    public long getTimeMilliseconds() {
        return time;
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
        /*
         * We're going to hack this up because I can't figure out how
         * to get a Date object to hang on to the timezone I set.
         */
        final String pattern = "HH:mm:ss";
        final SimpleDateFormat format = (SimpleDateFormat)DateFormat.getTimeInstance();
        format.applyPattern(pattern);
        String out = format.format(time);
        out += "UTC";
        return MiscUtil.varargsToList(out);
    }

}
