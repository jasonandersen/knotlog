package com.svhelloworld.knotlog.messages;

import java.time.Instant;
import java.util.List;

import com.svhelloworld.knotlog.i18n.BabelFish;
import com.svhelloworld.knotlog.util.MiscUtil;

/**
 * Marker messages mark a point of interest in an instrument
 * message stream. Generated by the application rather than a
 * vessel instrument. Typically user-triggered.
 * 
 * @author Jason Andersen
 * @since Feb 16, 2010
 *
 */
public class MarkerMessage implements VesselMessage {

    /**
     * Key used to retrieve the display message.
     */
    private final static String DISPLAY_KEY = "display.marker";
    /**
     * Key used to retrieve the name.
     */
    private final static String NAME_KEY = "name.marker";

    /**
     * message timestamp
     */
    private Instant timestamp;

    /**
     * text of marker message
     */
    private final String text;

    /**
     * Constructor.
     * @param timestamp
     * @param text text of this marker message
     */
    public MarkerMessage(final Instant timestamp, final String text) {
        this.timestamp = timestamp;
        this.text = text;
    }

    /**
     * @see com.svhelloworld.knotlog.messages.VesselMessage#getDisplayMessage()
     */
    @Override
    public String getDisplayMessage() {
        return text;
    }

    /**
     * @see com.svhelloworld.knotlog.messages.VesselMessage#getSource()
     */
    @Override
    public VesselMessageSource getSource() {
        return VesselMessageSource.APPLICATION;
    }

    /**
     * @see com.svhelloworld.knotlog.messages.VesselMessage#getTimestamp()
     */
    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    /**
     * @see com.svhelloworld.knotlog.messages.VesselMessage#getName()
     */
    @Override
    public String getName() {
        return BabelFish.localizeKey(NAME_KEY);
    }

    /**
     * @see com.svhelloworld.knotlog.i18n.Localizable#getLocalizeKey()
     */
    @Override
    public String getLocalizeKey() {
        return DISPLAY_KEY;
    }

    /**
     * @see com.svhelloworld.knotlog.i18n.Localizable#getLocalizeParams()
     */
    @Override
    public List<Object> getLocalizeParams() {
        return MiscUtil.varargsToList(text);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return BabelFish.localize(this);
    }

    /**
     * @see com.svhelloworld.knotlog.messages.VesselMessage#setTimestamp(java.util.Date)
     */
    @Override
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Defines the natural sorting order as chronological
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(VesselMessage o2) {
        return o2 == null ? 1 : timestamp.compareTo(o2.getTimestamp());
    }

}
