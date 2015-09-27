package com.svhelloworld.knotlog.messages;

import com.svhelloworld.knotlog.measure.LatitudinalHemisphere;
import com.svhelloworld.knotlog.measure.LongitudinalHemisphere;

/**
 * Specifies an exact location on the earth.
 * 
 * @author Jason Andersen
 * @since Feb 13, 2010
 *
 */
public interface Position {
    /**
     * @return latitude of position in degrees. 
     * Will always be between 0 and 90 degrees.
     */
    public float getLatitude();
    /**
     * @return indicator of latitudinal hemisphere. 
     * Will never return null.
     */
    public LatitudinalHemisphere getLatitudinalHemisphere();
    /**
     * @return longitude of position in degrees. 
     * Will always be between 0 and 180 degrees.
     */
    public float getLongitude();
    /**
     * @return indicator of longitudinal hemisphere. 
     * Will never return null.
     */
    public LongitudinalHemisphere getLongitudinalHemisphere();
}
