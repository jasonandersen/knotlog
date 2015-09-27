package com.svhelloworld.knotlog.messages;

import java.util.List;

import com.svhelloworld.knotlog.i18n.BabelFish;
import com.svhelloworld.knotlog.i18n.Localizable;

/**
 * Controls the display formatting of a position object.
 * 
 * @author Jason Andersen
 * @since Mar 6, 2010
 *
 */
public enum PositionFormat implements Localizable {
    /**
     * Formats position as degrees only. Example:
     * <tt>25.51569°N 111.06171°W</tt>
     */
    DEGREES(
            "position.format.deg",
            "%2.5f°%s %3.5f°%s") {
        @Override
        public String format(Position position) {
            return String.format(
                    this.pattern,
                    position.getLatitude(),
                    position.getLatitudinalHemisphere().getSuffix(),
                    position.getLongitude(),
                    position.getLongitudinalHemisphere().getSuffix());
        }
    },
    /**
     * Formats position as degrees minutes. Example:
     * <tt>25°30.941'N 111°03.702'W</tt>
     */
    DEGREES_MINUTES(
            "position.format.deg.min",
            "%02.0f°%06.3f'%s %03.0f°%06.3f'%s") {
        @Override
        public String format(Position position) {
            return String.format(
                    this.pattern,
                    Math.floor(position.getLatitude()),
                    getMinutes(position.getLatitude()),
                    position.getLatitudinalHemisphere().getSuffix(),
                    Math.floor(position.getLongitude()),
                    getMinutes(position.getLongitude()),
                    position.getLongitudinalHemisphere().getSuffix());
        }
    },
    /**
     * Formats position as degrees minutes seconds. Example:
     * <tt>25°30'56.6"N 111°03'42.3"W</tt>
     */
    DEGREES_MINUTES_SECONDS(
            "position.format.deg.min.sec",
            "%02.0f°%02.0f'%04.1f\"%s %03.0f°%02.0f'%04.1f\"%s") {
        @Override
        public String format(Position position) {
            return String.format(
                    this.pattern,
                    Math.floor(position.getLatitude()),
                    Math.floor(getMinutes(position.getLatitude())),
                    getSeconds(position.getLatitude()),
                    position.getLatitudinalHemisphere().getSuffix(),
                    Math.floor(position.getLongitude()),
                    Math.floor(getMinutes(position.getLongitude())),
                    getSeconds(position.getLongitude()),
                    position.getLongitudinalHemisphere().getSuffix());
        }
    };

    /**
     * The system default position format.
     */
    private static PositionFormat defaultFormat = PositionFormat.DEGREES_MINUTES;

    /**
     * @return the system default position format.
     */
    public static PositionFormat getDefaultFormat() {
        return defaultFormat;
    }

    /**
     * Sets a system wide default position format.
     * @param newFormat new default position format
     */
    public static void setDefaultFormat(final PositionFormat newFormat) {
        if (newFormat != null) {
            defaultFormat = newFormat;
        }
    }

    /**
     * key to localize description
     */
    protected final String descKey;
    /**
     * printf style pattern string
     */
    protected final String pattern;

    /**
     * Constructor.
     * @param descKey description key
     * @param pattern pattern string
     */
    PositionFormat(String descKey, String pattern) {
        this.descKey = descKey;
        this.pattern = pattern;
    }

    /**
     * Formats a <tt>Position</tt> object.
     * @param position
     * @return formatted string
     */
    public abstract String format(Position position);

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

    /**
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return BabelFish.localize(this);
    }

    /**
     * Calculates minutes from a given degrees.
     * @param degrees
     * @return minutes
     */
    protected float getMinutes(float degrees) {
        return (float) ((degrees - Math.floor(degrees)) * 60);
    }

    /**
     * Calculates the seconds from a given degrees.
     * @param degrees
     * @return
     */
    protected float getSeconds(float degrees) {
        float minutes = getMinutes(degrees);
        return (float) ((minutes - Math.floor(minutes)) * 60);
    }
}
