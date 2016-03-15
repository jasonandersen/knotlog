package com.svhelloworld.knotlog.domain.messages;


/**
 * Base class for point objects.
 * 
 * @author Jason Andersen
 * @since Feb 14, 2010
 * @deprecated Not going to use this class. Composition seems to be
 *              more flexible than inheritance. Use <tt>PositionImpl</tt>
 *              class instead.
 * @see PositionImpl
 */
@Deprecated
public abstract class BasePoint implements Position {
    
    /**
     * latitude of position
     */
    private float latitude;
    /**
     * longitude of position
     */
    private float longitude;

    /**
     * @see com.svhelloworld.knotlog.domain.messages.Position#getTimestamp()
     */
    @Override
    public float getLatitude() {
        return this.latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
    
    /**
     * @see com.svhelloworld.knotlog.domain.messages.Position#getTimestamp()
     */
    @Override
    public float getLongitude() {
        return this.longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
    
}
