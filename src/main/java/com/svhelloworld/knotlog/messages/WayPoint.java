package com.svhelloworld.knotlog.messages;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import com.svhelloworld.knotlog.i18n.BabelFish;
import com.svhelloworld.knotlog.i18n.Localizable;
import com.svhelloworld.knotlog.measure.LatitudinalHemisphere;
import com.svhelloworld.knotlog.measure.LongitudinalHemisphere;
import com.svhelloworld.knotlog.util.MiscUtil;

/**
 * A pre-defined position designating a point of interest.
 * 
 * @author Jason Andersen
 * @since Feb 14, 2010
 *
 */
public class WayPoint implements ValidVesselMessage, Position, Localizable {

    /**
     * Determines the icon to be displayed with the waypoint.
     * 
     * @author Jason Andersen
     * @since Feb 14, 2010
     *
     */
    public enum Symbol implements Localizable {
        /**
         * Marks a navigation waypoint.
         */
        WAYPOINT("Waypoint", "waypoint.symbol.waypoint"),
        /**
         * Marks a dangerous area.
         */
        DANGER_AREA("Danger Area", "waypoint.symbol.danger"),
        /**
         * Marks an anchorage.
         */
        ANCHORAGE("Anchorage", "waypoint.symbol.anchorage");

        /**
         * Description of waypoint
         */
        private String indicator;
        /**
         * Key to pull description out of resource bundle.
         */
        private String descKey;

        /**
         * Constructor
         */
        private Symbol(String indicator, String descKey) {
            this.indicator = indicator;
            this.descKey = descKey;
        }

        /**
         * @return symbol description
         */
        public String getDescription() {
            return indicator;
        }

        /**
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return indicator;
        }

        /**
         * @see com.svhelloworld.knotlog.i18n.Localizable#getLocalizeKey()
         */
        @Override
        public String getLocalizeKey() {
            return descKey;
        }

        /**
         * @see com.svhelloworld.knotlog.i18n.Localizable#getLocalizeParams()
         */
        @Override
        public List<Object> getLocalizeParams() {
            return null;
        }
    }

    /**
     * Position of waypoint
     */
    private PositionImpl position;
    /**
     * Timestamp of waypoint
     */
    private Instant timestamp;
    /**
     * waypoint name
     */
    private String name;
    /**
     * comment about waypoint
     */
    private String comment;
    /**
     * description of waypoint (not sure how this is different
     * than comment)
     */
    private String description;
    /**
     * waypoint icon to be displayed
     */
    private Symbol symbol;
    /**
     * additional information about the waypoint
     */
    private Map<String, String> extensionAttribs;
    /**
     * message source
     */
    private VesselMessageSource source;

    /**
     * @return the name
     */
    public String getWaypointName() {
        return name;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the symbol
     */
    public Symbol getSymbol() {
        return symbol;
    }

    /**
     * @return the extensionAttribs
     */
    public Map<String, String> getExtensionAttribs() {
        return extensionAttribs;
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
     * @see com.svhelloworld.knotlog.i18n.Localizable#getLocalizeKey()
     */
    @Override
    public String getLocalizeKey() {
        return "display.waypoint";
    }

    /**
     * @see com.svhelloworld.knotlog.i18n.Localizable#getLocalizeParams()
     */
    @Override
    public List<Object> getLocalizeParams() {
        PositionFormat formatter = PositionFormat.getDefaultFormat();
        String positionFormatted = formatter.format(position);
        return MiscUtil.varargsToList(name, positionFormatted);
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
        return BabelFish.localizeKey("waypoint.name");
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
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return BabelFish.localize(this);
    }

    /**
     * Defines the natural sorting order as chronological
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(VesselMessage o2) {
        return o2 == null ? 1 : timestamp.compareTo(o2.getTimestamp());
    }

    /**
     * @see com.svhelloworld.knotlog.messages.VesselMessage#getSource()
     */
    @Override
    public VesselMessageSource getSource() {
        return source;
    }
}
