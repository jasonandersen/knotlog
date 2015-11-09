package com.svhelloworld.knotlog.messages;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import com.svhelloworld.knotlog.i18n.BabelFish;
import com.svhelloworld.knotlog.measure.LatitudinalHemisphere;
import com.svhelloworld.knotlog.measure.LongitudinalHemisphere;
import com.svhelloworld.knotlog.util.MiscUtil;

/**
 * A position within a recorded vessel track.
 * 
 * @author Jason Andersen
 * @since Feb 14, 2010
 *
 */
public class TrackPoint implements Position, ValidVesselMessage {

    /*
     * TODO mark member variables as final once constructor is figured out
     */

    /**
     * Timestamp
     */
    private Instant timestamp;
    /**
     * Position
     */
    private PositionImpl position;
    /**
     * additional information about the track point
     */
    private Map<String, String> extensionAttribs;
    /**
     * source of message
     */
    private VesselMessageSource source;

    /**
     * @return the extensionAttribs
     */
    public Map<String, String> getExtensionAttribs() {
        return extensionAttribs;
    }

    /**
     * @param extensionAttribs the extensionAttribs to set
     */
    public void setExtensionAttribs(Map<String, String> extensionAttribs) {
        this.extensionAttribs = extensionAttribs;
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
     * @see com.svhelloworld.knotlog.messages.VesselMessage#getDisplayMessage()
     */
    @Override
    public String getDisplayMessage() {
        return BabelFish.localize(this);
    }

    /**
     * @see com.svhelloworld.knotlog.messages.VesselMessage#getName()
     */
    @Override
    public String getName() {
        return BabelFish.localizeKey("name.track.point");
    }

    /**
     * @see com.svhelloworld.knotlog.messages.VesselMessage#getTimestamp()
     */
    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    /**
     * @see com.svhelloworld.knotlog.messages.VesselMessage#setTimestamp(java.util.Date)
     */
    @Override
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @see com.svhelloworld.knotlog.i18n.Localizable#getLocalizeKey()
     */
    @Override
    public String getLocalizeKey() {
        return "display.track.point";
    }

    /**
     * @see com.svhelloworld.knotlog.i18n.Localizable#getLocalizeParams()
     */
    @Override
    public List<Object> getLocalizeParams() {
        final PositionFormat format = PositionFormat.getDefaultFormat();
        final String out = format.format(position);
        return MiscUtil.varargsToList(out);
    }

    /**
     * @see com.svhelloworld.knotlog.messages.VesselMessage#getSource()
     */
    @Override
    public VesselMessageSource getSource() {
        return source;
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
