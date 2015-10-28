package com.svhelloworld.knotlog.messages;

import java.time.Instant;
import java.util.List;

import com.svhelloworld.knotlog.measure.LatitudinalHemisphere;
import com.svhelloworld.knotlog.measure.LongitudinalHemisphere;
import com.svhelloworld.knotlog.util.MiscUtil;

/**
 * An instrument position message.
 * 
 * @author Jason Andersen
 * @since Feb 16, 2010
 * @see GPSPosition
 */
public abstract class PositionMessage extends BaseInstrumentMessage implements Position {

    /**
     * Position
     */
    private final Position position;

    /**
     * Position pattern
     */
    private final PositionFormat format;

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
    protected PositionMessage(
            final VesselMessageSource source,
            final Instant timestamp,
            final String latitude,
            final LatitudinalHemisphere latHemisphere,
            final String longitude,
            final LongitudinalHemisphere lonHemisphere) {

        super(source, timestamp);
        position = new PositionImpl(latitude, latHemisphere, longitude, lonHemisphere);
        //default position pattern
        format = PositionFormat.getDefaultFormat();
    }

    /**
     * @see com.svhelloworld.knotlog.messages.Position#getLatitude()
     */
    @Override
    public float getLatitude() {
        return position.getLatitude();
    }

    /**
     * @see com.svhelloworld.knotlog.messages.Position#getLatitudinalHemisphere()
     */
    @Override
    public LatitudinalHemisphere getLatitudinalHemisphere() {
        return position.getLatitudinalHemisphere();
    }

    /**
     * @see com.svhelloworld.knotlog.messages.Position#getLongitude()
     */
    @Override
    public float getLongitude() {
        return position.getLongitude();
    }

    /**
     * @see com.svhelloworld.knotlog.messages.Position#getLongitudinalHemisphere()
     */
    @Override
    public LongitudinalHemisphere getLongitudinalHemisphere() {
        return position.getLongitudinalHemisphere();
    }

    /**
     * @see com.svhelloworld.knotlog.i18n.Localizable#getLocalizeParams()
     */
    @Override
    public List<Object> getLocalizeParams() {
        return MiscUtil.varargsToList(format.format(position));
    }

}
