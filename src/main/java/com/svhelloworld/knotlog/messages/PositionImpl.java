package com.svhelloworld.knotlog.messages;

import java.util.regex.Pattern;

import com.svhelloworld.knotlog.measure.LatitudinalHemisphere;
import com.svhelloworld.knotlog.measure.LongitudinalHemisphere;

/**
 * A standard implementation of the <tt>Position</tt> interface.
 * 
 * @author Jason Andersen
 * @since Mar 6, 2010
 * @see Position
 */
public class PositionImpl implements Position {
    
    /**
     * Pattern for latitude strings. Should be in the form:
     * <tt>ddmm.mmm</tt>
     */
    private static final Pattern latitudePattern = 
        Pattern.compile("\\d{4}(\\.\\d{1,})*");
    /**
     * Pattern for longitude strings. Should be in the form:
     * <tt>dddmm.mmm</tt>
     */
    private static final Pattern longitudePattern =
        Pattern.compile("[0-1]\\d{4}(\\.\\d{1,})*");
    
    /**
     * Latitude coordinate
     */
    private final float latitude;
    /**
     * Longitude coordinate
     */
    private final float longitude;
    /**
     * Latitude hemisphere
     */
    private final LatitudinalHemisphere latHemisphere;
    /**
     * Longitude hemisphere
     */
    private final LongitudinalHemisphere lonHemisphere;
    
    /**
     * Constructor.
     * @param latitude latitude of position in degrees. If positive, then
     *          latitudinal hemisphere will be set as <tt>NORTH</tt>. If negative,
     *          then latitudinal hemisphere will be set as <tt>SOUTH</tt>.
     *          Note: latitude will always read from this class as a
     *          non-negative number. <tt>getLatitudinalHemisphere()</tt> will 
     *          indicate which hemisphere this position resides in.
     * @param longitude longitude of position in degrees. If positive, then
     *          longitudinal hemisphere will be set as <tt>EAST</tt>. If negative,
     *          then longitudinal hemisphere will be set as <tt>WEST</tt>.
     *          Note: longitude will always read from this class as a
     *          non-negative number. <tt>getLongitudinalHemisphere()</tt> will 
     *          indicate which hemisphere this position resides in.
     * @throws IllegalArgumentException if <tt>latitude</tt> is less than -90
     *          or greater than 90
     * @throws IllegalArgumentException if <tt>longitude</tt> is less than -180
     *          or greater than 180
     */
    public PositionImpl(final float latitude, final float longitude) {
        //chain this constructor
        this(
            Math.abs(latitude),
            latitude < 0 ? LatitudinalHemisphere.SOUTH : LatitudinalHemisphere.NORTH,
            Math.abs(longitude),
            longitude < 0 ? LongitudinalHemisphere.WEST : LongitudinalHemisphere.EAST);
    }
    
    /**
     * Constructor.
     * @param latitude latitude of position in degrees
     * @param latHemisphere latitudinal hemisphere
     * @param longitude longitude of position in degrees
     * @param lonHemisphere longitudinal hemisphere
     * @throws NullPointerException if <tt>latHemisphere</tt> is null
     * @throws NullPointerException if <tt>lonHemisphere</tt> is null
     * @throws IllegalArgumentException if <tt>latitude</tt> is less than zero
     *          or greater than 90
     * @throws IllegalArgumentException if <tt>longitude</tt> is less than zero
     *          or greater than 180
     */
    public PositionImpl(
            final float latitude, 
            final LatitudinalHemisphere latHemisphere, 
            final float longitude, 
            final LongitudinalHemisphere lonHemisphere) {
        
        //validate arguments
        if (latitude < 0 || latitude > 90) {
            throw new IllegalArgumentException("illegal latitude: " + latitude);
        }
        if (longitude < 0 || longitude > 180) {
            throw new IllegalArgumentException("illegal longitude: " + longitude);
        }
        if (latHemisphere == null) {
            throw new NullPointerException("latitudinal hemisphere cannot be null");
        }
        if (lonHemisphere == null) {
            throw new NullPointerException("longitudinal hemisphere cannot be null");
        }
        
        this.latitude = latitude;
        this.longitude = longitude;
        this.latHemisphere = latHemisphere;
        this.lonHemisphere = lonHemisphere;
    }
    
    /**
     * Constructor.
     * @param latitude latitude of position in the form: <tt>ddmm.mmm</tt>
     *          where <tt>dd</tt> is degrees and <tt>mm.mmm</tt> is minutes.
     * @param latHemisphere latitude hemisphere
     * @param longitude longitude of position in the form: <tt>dddmm.mmm</tt>
     *          where <tt>ddd</tt> is degrees and <tt>mm.mmm</tt> is minutes.
     * @param lonHemisphere longitude hemisphere
     * @throws NullPointerException if <tt>latHemisphere</tt> is null
     * @throws NullPointerException if <tt>lonHemisphere</tt> is null
     * @throws IllegalArgumentException if <tt>latitude</tt> and <tt>longitude</tt>
     *          are not in the pattern specified above.
     * @throws IllegalArgumentException if degrees portion of <tt>latitude</tt> is less
     *          than zero or greater than 90.
     * @throws IllegalArgumentException if degrees portion of <tt>longitude</tt> is less
     *          than zero or greater than 180.
     */
    public PositionImpl(
            final String latitude,
            final LatitudinalHemisphere latHemisphere,
            final String longitude,
            final LongitudinalHemisphere lonHemisphere) {
        
        //validate arguments
        if (latitude == null || latitude.length() == 0) {
            throw new NullPointerException("latitude cannot be null");
        }
        if (longitude == null || longitude.length() == 0) {
            throw new NullPointerException("longitude cannot be null");
        }
        if (latHemisphere == null || lonHemisphere == null) {
            throw new NullPointerException("hemispheres cannot be null");
        }
        if (!latitudePattern.matcher(latitude).matches()) {
            throw new IllegalArgumentException(
                    "latitude: [" + latitude + "] is not properly formed.");
        }
        if (!longitudePattern.matcher(longitude).matches()) {
            throw new IllegalArgumentException(
                    "longitude: [" + longitude + "] is not properly formed.");
        }
        
        //convert coordinate strings into numeric coordinates
        float latDegrees = Float.parseFloat(latitude.substring(0, 2));
        float latMinutes = Float.parseFloat(latitude.substring(2));
        latDegrees += latMinutes / 60;
        
        float lonDegrees = Float.parseFloat(longitude.substring(0, 3));
        float lonMinutes = Float.parseFloat(longitude.substring(3));
        lonDegrees += lonMinutes / 60;
        
        this.latitude = latDegrees;
        this.longitude = lonDegrees;
        this.latHemisphere = latHemisphere;
        this.lonHemisphere = lonHemisphere;
    }
    
    /**
     * @see com.svhelloworld.knotlog.messages.Position#getLatitude()
     */
    @Override
    public float getLatitude() {
        return latitude;
    }

    /**
     * @see com.svhelloworld.knotlog.messages.Position#getLatitudinalHemisphere()
     */
    @Override
    public LatitudinalHemisphere getLatitudinalHemisphere() {
        return latHemisphere;
    }

    /**
     * @see com.svhelloworld.knotlog.messages.Position#getLongitude()
     */
    @Override
    public float getLongitude() {
        return longitude;
    }

    /**
     * @see com.svhelloworld.knotlog.messages.Position#getLongitudinalHemisphere()
     */
    @Override
    public LongitudinalHemisphere getLongitudinalHemisphere() {
        return lonHemisphere;
    }

}
