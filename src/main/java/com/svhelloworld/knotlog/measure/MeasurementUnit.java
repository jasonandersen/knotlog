package com.svhelloworld.knotlog.measure;

/**
 * A unit of measure.
 * 
 * @author Jason Andersen
 * @since Feb 15, 2010
 *
 */
public interface MeasurementUnit {
    /**
     * @return a description of the measurement unit
     */
    public String getDescription();
    /**
     * @return a measurement type indicator to append to measurement readings
     */
    public String getSuffix();
    /**
     * @return an array of abbreviations used to indicate this unit
     */
    public String[] getAbbreviations();
}
