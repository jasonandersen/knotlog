package com.svhelloworld.knotlog.engine;

import com.svhelloworld.knotlog.messages.VesselMessage;

/**
 * Sifts out position messages that are less than a specified
 * distance apart or a specified time apart.
 * 
 * @author Jason Andersen
 * @since Feb 14, 2010
 *
 */
public class PositionMessageSifter implements MessageSifter {
    /**
     * Distance resolution in feet.
     */
    private float distance;
    /**
     * Maximum time between messages in seconds.
     */
    private float time;
    
    /**
     * @return the minimum distance between position messages in feet
     */
    public float getDistanceInFeet() {
        return distance;
    }
    /**
     * @param distance the distance to set
     */
    public void setDistanceInFeet(float distance) {
        this.distance = distance;
    }
    /**
     * @return the maximum time in seconds between position messages
     */
    public float getTimeInSeconds() {
        return time;
    }
    /**
     * @param time the time to set
     */
    public void setTimeInSeconds(float time) {
        this.time = time;
    }
    /**
     * @see com.svhelloworld.knotlog.engine.MessageSifter#sift(com.svhelloworld.knotlog.messages.VesselMessage[])
     */
    @Override
    public VesselMessage[] sift(VesselMessage... messages) {
        return null;
    }


}
