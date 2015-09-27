package com.svhelloworld.knotlog.measure;


/**
 * Defines a measurement of distance.
 * 
 * @author Jason Andersen
 * @since Feb 16, 2010
 *
 */
public interface Distance {
    /**
     * @return distance measurement
     */
    public float getDistance();
    /**
     * @return unit of distance measurement
     */
    public DistanceUnit getDistanceUnit();
}
