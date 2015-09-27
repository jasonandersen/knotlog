package com.svhelloworld.knotlog.messages;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author Jason Andersen
 * @since Mar 10, 2010
 *
 */
public class MockVesselMessage implements VesselMessage {
    
    private Date timestamp;
    
    /**
     * Constructor.
     */
    public MockVesselMessage() {
        timestamp = new Date();
    }
    
    /**
     * @see com.svhelloworld.knotlog.messages.VesselMessage#getDisplayMessage()
     */
    @Override
    public String getDisplayMessage() {
        return "mock message " + getTimestamp();
    }

    /**
     * @see com.svhelloworld.knotlog.messages.VesselMessage#getName()
     */
    @Override
    public String getName() {
        return "mock message";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.VesselMessage#getTimestamp()
     */
    @Override
    public Date getTimestamp() {
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
     * @see com.svhelloworld.knotlog.messages.VesselMessage#setTimestamp(java.util.Date)
     */
    @Override
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
    /**
     * @see com.svhelloworld.knotlog.messages.VesselMessage#getSource()
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
