package com.svhelloworld.knotlog.measure;

/**
 * Defines a measurement of speed.
 * 
 * @author Jason Andersen
 * @since Feb 26, 2010
 *
 */
public interface Speed {
    /**
     * @return speed measurement.
     */
    public float getSpeed();
    /**
     * @return unit of speed measurement
     */
    public SpeedUnit getSpeedUnit();
}
