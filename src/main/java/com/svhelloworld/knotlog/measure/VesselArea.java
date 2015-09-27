package com.svhelloworld.knotlog.measure;

import java.util.List;

import com.svhelloworld.knotlog.i18n.BabelFish;
import com.svhelloworld.knotlog.i18n.Localizable;

/**
 * Specifies an area of the vessel.
 * 
 * @author Jason Andersen
 * @since Feb 15, 2010
 *
 */
public enum VesselArea implements MeasurementUnit, Localizable {
    /**
     * When looking forward, starboard is the right half of the vessel.
     */
    STARBOARD("vessel.starboard", "vessel.starboard.suffix", "S", "s", "stbd", "STBD", "R", "r"),
    /**
     * When looking forward, port is the left half of the vessel.
     */
    PORT("vessel.port", "vessel.port.suffix", "P", "p", "port", "PORT", "L", "l"),
    /**
     * The front half of the vessel.
     */
    FORWARD("vessel.forward", "vessel.forward.suffix", "F", "f", "FWD", "fwd", "FORWARD", "forward"),
    /**
     * The back half of the vessel.
     */
    AFT("vessel.aft", "vessel.aft.suffix", "A", "a", "AFT", "aft");
    
    /**
     * Description
     */
    private String descKey;
    /**
     * Suffix
     */
    private String suffixKey;
    /**
     * Abbreviations
     */
    private String[] abbreviations;
    
    /**
     * Constructor
     */
    private VesselArea(String descKey, String suffixKey, String... abbreviations) {
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
     * @see com.svhelloworld.knotlog.measure.MeasurementUnit#getAbbreviations()
     */
    @Override
    public String[] getAbbreviations() {
        return abbreviations;
    }
}
