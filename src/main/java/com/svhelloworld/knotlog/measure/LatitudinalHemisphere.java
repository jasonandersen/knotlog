package com.svhelloworld.knotlog.measure;

import java.util.List;

import com.svhelloworld.knotlog.i18n.BabelFish;

/**
 * Indicates a hemisphere of the earth as divided along the equator.
 * 
 * @author Jason Andersen
 * @since Mar 6, 2010
 *
 */
public enum LatitudinalHemisphere implements Hemisphere {
    
    /**
     * Hemisphere north of the equator.
     */
    NORTH("hemisphere.north", "hemisphere.north.suffix", "N", "n", "NORTH", "north"),
    /**
     * Hemisphere south of the equator.
     */
    SOUTH("hemisphere.south", "hemisphere.south.suffix", "S", "s", "SOUTH", "south");
    
    /**
     * key used to localize description
     */
    private String descKey;
    /**
     * key used to localize suffix
     */
    private String suffixKey;
    /**
     * abbreviations for this enum.
     */
    private String[] abbreviations;
    
    /**
     * Constructor.
     * @param abbreviations enum abbreviations.
     */
    LatitudinalHemisphere(String descKey, String suffixKey, String... abbreviations) {
        this.descKey = descKey;
        this.suffixKey = suffixKey;
        this.abbreviations = abbreviations;
    }

    /**
     * @see com.svhelloworld.knotlog.measure.MeasurementUnit#getAbbreviations()
     */
    @Override
    public String[] getAbbreviations() {
        return abbreviations;
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
