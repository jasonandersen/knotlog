package com.svhelloworld.knotlog.domain.messages;

import java.time.Instant;

import com.svhelloworld.knotlog.i18n.BabelFish;

/**
 * Base class for instrument messages to handle common
 * data.
 * 
 * @author Jason Andersen
 * @since Feb 24, 2010
 *
 */
public abstract class BaseInstrumentMessage implements ValidVesselMessage {

    /**
     * Source of message
     */
    private final VesselMessageSource source;
    /**
     * Timestamp in UTC. Timestamp is a muteable field.
     */
    private Instant timestamp;

    /**
     * Constructor. Takes care of validating and setting source
     * and timestamp member variables.
     * @param source instrument message source
     * @param timestamp message timestamp
     * @throws NullPointerException if source is null
     * @throws NullPointerException if timestamp is null
     */
    protected BaseInstrumentMessage(
            final VesselMessageSource source,
            final Instant timestamp) {

        if (source == null) {
            throw new NullPointerException("source cannot be null");
        }
        if (timestamp == null) {
            throw new NullPointerException("timestamp cannot be null");
        }
        this.source = source;
        this.timestamp = timestamp;
    }

    /**
     * @return key used to pull display string out of resource bundle
     */
    protected abstract String getDisplayKey();

    /**
     * @return key used to pull name out of resource bundle
     */
    protected abstract String getNameKey();

    /**
     * @see com.svhelloworld.knotlog.domain.messages.VesselMessage#getSource()
     */
    @Override
    public VesselMessageSource getSource() {
        return source;
    }

    /**
     * @see com.svhelloworld.knotlog.domain.messages.VesselMessage#getTimestamp()
     */
    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    /**
     * @see com.svhelloworld.knotlog.domain.messages.VesselMessage#setTimestamp(java.util.Date)
     */
    @Override
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @see com.svhelloworld.knotlog.domain.messages.VesselMessage#getDisplayMessage()
     */
    @Override
    public String getDisplayMessage() {
        return BabelFish.localize(this);
    }

    /**
     * @see com.svhelloworld.knotlog.domain.messages.VesselMessage#getName()
     */
    @Override
    public String getName() {
        return BabelFish.localizeKey(getNameKey());
    }

    /**
     * @see com.svhelloworld.knotlog.i18n.Localizable#getLocalizeKey()
     */
    @Override
    public String getLocalizeKey() {
        return getDisplayKey();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return BabelFish.localize(this);
    }

    /**
     * @param key message key
     * @param params parameters for message
     * @return a localized message string
     */
    protected String getMessage(String key, Object... params) {
        return BabelFish.localizeKey(key, params);
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
