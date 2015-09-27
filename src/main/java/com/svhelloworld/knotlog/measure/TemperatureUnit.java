package com.svhelloworld.knotlog.measure;

import java.util.List;

import com.svhelloworld.knotlog.i18n.BabelFish;
import com.svhelloworld.knotlog.i18n.Localizable;

/**
 * Units of temperature.
 * 
 * @author Jason Andersen
 * @since Feb 15, 2010
 *
 */
public enum TemperatureUnit implements MeasurementUnit, Localizable {
    /**
     * Temperature in celsius.
     */
    CELSIUS("temp.celsius", "temp.celsius.suffix", "celsius", "CELSIUS", "°C", "C", "c"),
    /**
     * Temperature in fahrenheit.
     */
    FAHRENHEIT("temp.fahrenheit", "temp.fahrenheit.suffix", "fahrenheit", "FAHRENHEIT", "°F", "F", "f");

    /**
     * Description key
     */
    private String descKey;
    /**
     * Suffix key
     */
    private String suffixKey;
    /**
     * temperature abbreviations
     */
    private String[] abbreviations;
    
    /**
     * Constructor
     */
    private TemperatureUnit(String desc, String suffix, String... abbreviations) {
        this.descKey = desc;
        this.suffixKey = suffix;
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
     * @return an array of abbreviations for this temperature unit
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
