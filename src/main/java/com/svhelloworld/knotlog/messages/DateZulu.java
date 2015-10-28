package com.svhelloworld.knotlog.messages;

import java.text.DateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;

import com.svhelloworld.knotlog.util.MiscUtil;

/**
 * A message indicating date in Zulu (UTC) time zone.
 * 
 * @author Jason Andersen
 * @since Mar 8, 2010
 *
 */
public class DateZulu extends BaseInstrumentMessage {

    /**
     * Regex pattern to confirm date argument is formatted properly.
     * Expected pattern: ddmmyy
     */
    private static final Pattern datePattern = Pattern.compile(
            "[0-3]?\\d[0-1]\\d[9012]\\d");

    /*
     * The Java Date/Calendar/DateFormat triumverate of evil is proving
     * to be a righteous pain in the ass. I'm still not sure how I'm
     * going to store this time and keep the time zone set at UTC. For
     * now, I'm just going to deal in millisecond values and go back and
     * change this as needed.
     */

    /**
     * Date in UTC stored as milliseconds since the epoch. No time value
     * is being stored.
     */
    private final long date;

    /**
     * Constructor. 
     * @param source instrument message source
     * @param timestamp message timestamp
     * @param date date zulu in the form:
     *          <tt>ddmmyy</tt>.  <tt>yy</tt> &gt;= 90
     *          will be prefaced with <tt>19</tt>. Otherwise
     *          year will be prefaced with <tt>20</tt>.
     * @throws NullPointerException if source is null
     * @throws NullPointerException if timestamp is null
     * @throws NullPointerException if date is null
     * @throws IllegalArgumentException if date is not in the specified form.
     */
    public DateZulu(
            final VesselMessageSource source,
            final Instant timestamp,
            final String date) {

        super(source, timestamp);
        if (date == null) {
            throw new NullPointerException("date cannot be null");
        }
        if (!datePattern.matcher(date).matches()) {
            throw new IllegalArgumentException("date argument malformed: " + date);
        }
        int position = date.length();
        int year = Integer.parseInt(date.substring(position - 2, position));
        position -= 2;
        int month = Integer.parseInt(date.substring(position - 2, position));
        position -= 2;
        int day = Integer.parseInt(date.substring(0, position));

        //convert two-digit year into four-digit year
        year += year >= 90 ? 1900 : 2000;
        //convert month to a zero-based index
        month--;

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(year, month, day);
        this.date = calendar.getTimeInMillis();
    }

    /**
     * @return the date in zulu (UTC) timezone, no time component
     */
    public Date getDate() {
        return new Date(date);
    }

    /**
     * @return returns this date as the number of milliseconds since the epoch
     */
    public long getDateMilliseconds() {
        return date;
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getDisplayKey()
     */
    @Override
    protected String getDisplayKey() {
        return "display.date.zulu";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getNameKey()
     */
    @Override
    protected String getNameKey() {
        return "name.date.zulu";
    }

    /**
     * @see com.svhelloworld.knotlog.i18n.Localizable#getLocalizeParams()
     */
    @Override
    public List<Object> getLocalizeParams() {
        final DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
        return MiscUtil.varargsToList(format.format(date));
    }

}
