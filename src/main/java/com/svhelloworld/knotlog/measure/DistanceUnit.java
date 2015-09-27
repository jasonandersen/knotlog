package com.svhelloworld.knotlog.measure;

import java.util.List;

import com.svhelloworld.knotlog.i18n.BabelFish;
import com.svhelloworld.knotlog.i18n.Localizable;

/**
 * Measurement of distance.
 * 
 * @author Jason Andersen
 * @since Feb 15, 2010
 *
 */
public enum DistanceUnit implements MeasurementUnit, Localizable {
    /**
     * imperial feet
     */
    FEET("distance.feet", "distance.feet.suffix", "feet", "ft", "f", "FEET"),
    /**
     * metric meters
     */
    METERS("distance.meters", "distance.meters.suffix", "meters", "m", "M", "METERS"),
    /**
     * 1 fathom = 6 feet
     */
    FATHOMS("distance.fathoms", "distance.fathoms.suffix", "fathoms", "fm", "FATHOMS", "F"),
    /**
     * 1 nautical mile = 1 minute of latitude
     */
    NAUTICAL_MILES("distance.nautical.miles", "distance.nautical.miles.suffix", "nautical miles", "nm", "NM"),
    /**
     * 1 statute mile = 5,280 feet
     */
    STATUTE_MILES("distance.statute.miles", "distance.statute.miles.suffix", "miles", "miles", "MILES");
    
    /**
     * Description
     */
    private String descKey;
    /**
     * Suffix
     */
    private String suffixKey;
    /**
     * An array of abbreviations used to define this distance unit
     */
    private String[] abbreviations;
    
    /**
     * Constructor
     */
    private DistanceUnit(String descKey, String suffixKey, String... abbreviations) {
        this.descKey = descKey;
        this.suffixKey = suffixKey;
        this.abbreviations = abbreviations;
    }

    /**
     * @see com.svhelloworld.knotlog.measure.MeasurementUnit#getDescription()
     */
    @Override
    public String getDescription() {
        return BabelFish.localizeKey(descKey);
    }

    /**
     * @see com.svhelloworld.knotlog.measure.MeasurementUnit#getSuffix()
     */
    @Override
    public String getSuffix() {
        return BabelFish.localizeKey(suffixKey);
    }
    
    /**
     * @return returns an array of abbreviations used for this measurement
     */
    public String[] getAbbreviations() {
        return abbreviations;
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
    
    /**
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return BabelFish.localize(this);
    }
}
