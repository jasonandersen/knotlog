package com.svhelloworld.knotlog.messages;

import java.time.Instant;
import java.util.List;

import com.svhelloworld.knotlog.domain.messages.VesselMessage;
import com.svhelloworld.knotlog.domain.messages.VesselMessageSource;
import com.svhelloworld.knotlog.util.Now;

/**
 * 
 * @author Jason Andersen
 * @since Mar 10, 2010
 *
 */
public class MockVesselMessage implements VesselMessage {

    private Instant timestamp;

    /**
     * Constructor.
     */
    public MockVesselMessage() {
        timestamp = Now.getInstant();
    }

    /**
     * @see com.svhelloworld.knotlog.domain.messages.VesselMessage#getDisplayMessage()
     */
    @Override
    public String getDisplayMessage() {
        return "mock message " + getTimestamp();
    }

    /**
     * @see com.svhelloworld.knotlog.domain.messages.VesselMessage#getName()
     */
    @Override
    public String getName() {
        return "mock message";
    }

    /**
     * @see com.svhelloworld.knotlog.domain.messages.VesselMessage#getTimestamp()
     */
    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    /**
     * @see com.svhelloworld.knotlog.i18n.Localizable#getLocalizeKey()
     */
    @Override
    public String getLocalizeKey() {
        return "";
    }

    /**
     * @see com.svhelloworld.knotlog.i18n.Localizable#getLocalizeParams()
     */
    @Override
    public List<Object> getLocalizeParams() {
        return null;
    }

    /**
     * @see com.svhelloworld.knotlog.domain.messages.VesselMessage#setTimestamp(java.util.Date)
     */
    @Override
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @see com.svhelloworld.knotlog.domain.messages.VesselMessage#getSource()
     */
    @Override
    public VesselMessageSource getSource() {
        return VesselMessageSource.APPLICATION;
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
