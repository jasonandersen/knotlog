package com.svhelloworld.knotlog.measure;

import java.util.List;

import com.svhelloworld.knotlog.i18n.BabelFish;
import com.svhelloworld.knotlog.i18n.Localizable;

/**
 * The basis for which a measurement was taken. Measurements can
 * be taken relative to the vessel's speed and heading or measurements
 * can be taken as true, without regard for vessel's speed and heading.
 * 
 * @author Jason Andersen
 * @since Feb 24, 2010
 */
public enum MeasurementBasis implements MeasurementUnit, Localizable {
    /**
     * Measurement was calculated relative to the vessel's 
     * speed and direction.
     */
    RELATIVE("measurement.basis.relative", "measurement.basis.relative.suffix", 
            "R", "r", "relative", "RELATIVE"),
    /**
     * Measurement was calculated without factoring in vessel's
     * speed and direction.
     */
    TRUE("measurement.basis.true", "measurement.basis.true.suffix", 
            "T", "t", "true", "TRUE");

    /**
     * description key
     */
    private String descKey;
    /**
     * suffix key
     */
    private String suffixKey;
    /**
     * list of abbreviations
     */
    private String[] abbreviations;
    /**
     * Constructor.
     * @param descKey localization key
     */
    MeasurementBasis(String descKey, String suffixKey, String... abbreviations) {
        this.descKey = descKey;
        this.suffixKey = suffixKey;
        this.abbreviations = abbreviations;
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

    /**
     * @see com.svhelloworld.knotlog.measure.MeasurementUnit#getDescription()
     */
    @Override
    public String getDescription() {
        return BabelFish.localize(this);
    }

    /**
     * @see com.svhelloworld.knotlog.measure.MeasurementUnit#getSuffix()
     */
    @Override
    public String getSuffix() {
        return BabelFish.localizeKey(suffixKey);
    }

    /**
     * @see com.svhelloworld.knotlog.measure.MeasurementUnit#getAbbreviations()
     */
    @Override
    public String[] getAbbreviations() {
        return abbreviations;
    }

}