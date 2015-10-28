package com.svhelloworld.knotlog.messages;

import java.time.Instant;
import java.util.List;

import com.svhelloworld.knotlog.i18n.BabelFish;
import com.svhelloworld.knotlog.util.MiscUtil;

/**
 * An instrument message in its raw form, prior to being parsed.
 * 
 * @author Jason Andersen
 * @since May 28, 2010
 *
 */
public class PreparseMessage implements VesselMessage {

    /**
     * source of the instrument message
     */
    private final VesselMessageSource source;
    /**
     * timestamp of instrument message
     */
    private Instant timestamp;
    /**
     * raw text of instrument message
     */
    private final String message;

    /**
     * Constructor
     * @param source
     * @param timestamp
     * @param message
     * @throws NullPointerException when source is null
     * @throws NullPointerException when timestamp is null
     * @throws NullPointerException when message is null
     */
    public PreparseMessage(
            VesselMessageSource source,
            Instant timestamp,
            String message) {

        if (source == null) {
            throw new NullPointerException("source cannot be null");
        }
        if (timestamp == null) {
            throw new NullPointerException("timestamp cannot be null");
        }
        if (message == null) {
            throw new NullPointerException("message cannot be null");
        }
        this.source = source;
        this.timestamp = timestamp;
        this.message = message;
    }

    /**
     * @see com.svhelloworld.knotlog.messages.VesselMessage#getSource()
     */
    @Override
    public VesselMessageSource getSource() {
        return source;
    }

    /**
     * @see com.svhelloworld.knotlog.messages.VesselMessage#getDisplayMessage()
     */
    @Override
    public String getDisplayMessage() {
        return message;
    }

    /**
     * @see com.svhelloworld.knotlog.messages.VesselMessage#getName()
     */
    @Override
    public String getName() {
        return BabelFish.localizeKey("name.preparse");
    }

    /**
     * @see com.svhelloworld.knotlog.messages.VesselMessage#getTimestamp()
     */
    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    /**
     * @see com.svhelloworld.knotlog.messages.VesselMessage#setTimestamp(java.util.Date)
     */
    @Override
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @see com.svhelloworld.knotlog.i18n.Localizable#getLocalizeKey()
     */
    @Override
    public String getLocalizeKey() {
        return "display.preparse";
    }

    /**
     * @see com.svhelloworld.knotlog.i18n.Localizable#getLocalizeParams()
     */
    @Override
    public List<Object> getLocalizeParams() {
        return MiscUtil.varargsToList(message);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return message;
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
