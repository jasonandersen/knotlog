package com.svhelloworld.knotlog.measure;

import java.util.List;

import com.svhelloworld.knotlog.i18n.BabelFish;
import com.svhelloworld.knotlog.i18n.Localizable;

/**
 * Measurement of angle.
 * 
 * @author Jason Andersen
 * @since Feb 15, 2010
 *
 */
public enum AngleUnit implements MeasurementUnit, Localizable {
    /**
     * An angle in a 360° circle.
     */
    DEGREES("angle.degrees", "angle.degrees.suffix", "degrees", "°", 
            "degrees", "deg", "DEGREES", "DEG"),
    /**
     * A compass bearing relative to magnetic north.
     */
    DEGREES_MAGNETIC("angle.degrees.magnetic", "angle.degrees.magnetic.suffix", 
            "degrees magnetic", "°M", "M", "m", "magnetic", "MAGNETIC"),
    /**
     * A compass bearing relative to true north.
     */
    DEGREES_TRUE("angle.degrees.true", "angle.degrees.true.suffix", 
            "degrees true", "°T", "T", "t", "true", "TRUE");

    /**
     * Resolves an abbreviation out to a AngleUnit.
     * @param abbr abbreviation to search for
     * @return AngleUnit matching abbreviation, if no abbreviation
     * is found, will return null.
     */
    public static AngleUnit resolveAbbreviation(String abbr) {
        for (AngleUnit unit : AngleUnit.values()) {
            for (String abbreviation : unit.getAbbreviations()) {
                if (abbreviation.equals(abbr)) {
                    return unit;
                }
            }
        }
        return null;
    }
    
    /**
     * Description key
     */
    private String descKey;
    /**
     * Suffix key
     */
    private String suffixKey;
    /**
     * Abbreviations for this measurement unit.
     */
    private String[] abbreviations;
    
    /**
     * Constructor
     */
    private AngleUnit(String key, String suffixKey, String... abbreviations) {
        this.descKey = key;
        this.suffixKey = suffixKey;
        this.abbreviations = abbreviations;
    }

    /**
     * @see com.svhelloworld.knotlog.measure.MeasurementUnit#getSuffix()
     */
    @Override
    public String getSuffix() {
        return BabelFish.localizeKey(suffixKey);
    }
    
    /**
     * @return an array of abbreviations for this measurement unit
     */
    public String[] getAbbreviations() {
        return this.abbreviations;
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
     * @see com.svhelloworld.knotlog.measure.MeasurementUnit#getDescription()
     */
    @Override
    public String getDescription() {
        return BabelFish.localizeKey(descKey);
    }
    
    /**
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return BabelFish.localize(this);
    }
}
