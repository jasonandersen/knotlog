package com.svhelloworld.knotlog.measure;

import java.util.List;

import com.svhelloworld.knotlog.i18n.BabelFish;
import com.svhelloworld.knotlog.i18n.Localizable;

/**
 * Speed measurement units.
 * 
 * @author Jason Andersen
 * @since Feb 15, 2010
 *
 */
public enum SpeedUnit implements MeasurementUnit,Localizable {
    /**
     * 1 knot = 1 nautical mile per hour
     */
    /*
     * note: Garmin 4208 broadcasts wind speed measurement as "S". I have
     * no idea why but there it is and that explains why "S" is an abbreviation
     * for knots.
     */
    KNOTS("speed.knots", "speed.knots.suffix", "knots", "kts", "N", "KNOTS", "S"),
    /**
     * Meters per second
     */
    METERS_PER_SECOND("speed.mps", "speed.mps.suffix", "meters per second", "mps", "MPS"),
    /**
     * Statute miles per hour
     */
    MILES_PER_HOUR("speed.mph", "speed.mph.suffix", "miles per hour", "mph", "MPH"),
    /**
     * Kilometers per hour
     */
    KILOMETERS_PER_HOUR("speed.kph", "speed.kph.suffix", "kilometers per hour", "kph", "KPH");

    /**
     * Description key
     */
    private String descKey;
    /**
     * Suffix key
     */
    private String suffixKey;
    /**
     * Abbreviations for speed measurement unit.
     */
    private String[] abbreviations;

    /**
     * Constructor
     */
    private SpeedUnit(String descKey, String suffixKey, String... abbreviations) {
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
     * @return an array of abbreviations for this unit
     */
    @Override
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
