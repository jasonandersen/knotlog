package com.svhelloworld.knotlog.domain.messages;

import java.time.Instant;

import com.svhelloworld.knotlog.measure.LatitudinalHemisphere;
import com.svhelloworld.knotlog.measure.LongitudinalHemisphere;

/**
 * A position message received from a Global Positioning System.
 * 
 * @author Jason Andersen
 * @since Mar 7, 2010
 *
 */
public class GPSPosition extends PositionMessage {

    /**
     * Constructor.
     * @param source message source
     * @param timestamp timestamp of message event
     * @param latitude latitude of position in the form: <tt>ddmm.mmm</tt>
     *          where <tt>dd</tt> is degrees and <tt>mm.mmm</tt> is minutes.
     * @param latHemisphere latitude hemisphere
     * @param longitude longitude of position in the form: <tt>dddmm.mmm</tt>
     *          where <tt>ddd</tt> is degrees and <tt>mm.mmm</tt> is minutes.
     * @param lonHemisphere longitude hemisphere
     * @throws NullPointerException when timestamp is null
     * @throws NullPointerException when source is null
     * @throws NullPointerException if <tt>latHemisphere</tt> is null
     * @throws NullPointerException if <tt>lonHemisphere</tt> is null
     * @throws IllegalArgumentException if <tt>latitude</tt> and <tt>longitude</tt>
     *          are not in the pattern specified above.
     * @throws IllegalArgumentException if degrees portion of <tt>latitude</tt> is less
     *          than zero or greater than 90.
     * @throws IllegalArgumentException if degrees portion of <tt>longitude</tt> is less
     *          than zero or greater than 180. 
     */
    public GPSPosition(
            final VesselMessageSource source,
            final Instant timestamp,
            final String latitude,
            final LatitudinalHemisphere latHemisphere,
            final String longitude,
            final LongitudinalHemisphere lonHemisphere) {

        super(source, timestamp, latitude, latHemisphere, longitude, lonHemisphere);
    }

    /**
     * @see com.svhelloworld.knotlog.domain.messages.BaseInstrumentMessage#getDisplayKey()
     */
    @Override
    protected String getDisplayKey() {
        return "display.gps.position";
    }

    /**
     * @see com.svhelloworld.knotlog.domain.messages.BaseInstrumentMessage#getNameKey()
     */
    @Override
    protected String getNameKey() {
        return "name.gps.position";
    }

}
