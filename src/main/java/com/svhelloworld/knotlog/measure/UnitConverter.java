package com.svhelloworld.knotlog.measure;

import java.util.Map;

/**
 * Singleton object that converts between like measurement units.
 * 
 * @author Jason Andersen
 * @since Feb 15, 2010
 *
 */
public class UnitConverter {
    
    /**
     * Singleton instance.
     */
    private static UnitConverter instance;
    
    /**
     * @return singleton instance.
     */
    public static UnitConverter getInstance() {
        if (instance == null) {
            instance = new UnitConverter();
        }
        return instance;
    }
    
    /**
     * Ratios to convert between distance measurements.
     */
    private Map<MeasurementUnit, Map<MeasurementUnit, Float>> distanceRatios;
    
    /**
     * Private constructor
     */
    private UnitConverter() {
        //initialize ratio maps
    }
    
    
    /**
     * Converts temperature values.
     * @param origValue original temperature value
     * @param origUnit original temperature measurement unit
     * @param newUnit measurement unit to convert to
     * @return converted temperature units.
     */
    public float convertTemperature(
            float origValue, 
            TemperatureUnit origUnit, 
            TemperatureUnit newUnit) {
        
        float out = 0;
        if (origUnit.equals(TemperatureUnit.CELSIUS)) {
            //perform celsius to fahrenheit conversion
        } else {
            //perform fahrenheit to celsius conversion
        }
        return out;
    }
    
    /**
     * Converts distance units.
     * @param value original distance value
     * @param from distance unit to convert from
     * @param to distance unit to convert to
     * @return converted distance unit
     * @throws ConversionException when a conversion ratio has not been defined
     * between two measurement units
     */
    public float convertDistance(
            float value, 
            DistanceUnit from, 
            DistanceUnit to) throws ConversionException {
        
        return convert(value, from, to, this.distanceRatios);
    }
    
    /**
     * Converts units with a linear relationship between measurement units.
     * @param value original value
     * @param from distance unit to convert from
     * @param to distance unit to convert to
     * @param ratioMap map containing conversion ratios 
     * @return converted value
     * @throws ConversionException when a conversion ratio has not been defined
     * between two measurement units
     */
    private float convert(float value, MeasurementUnit from, 
            MeasurementUnit to, Map<MeasurementUnit, Map<MeasurementUnit, Float>> ratioMap) 
            throws ConversionException {
        
        try {
            Float ratio = ratioMap.get(from).get(to);
            return ratio * value;
        } catch (Exception e) {
            throw new ConversionException("Cannot convert from " + from.getDescription() + 
                    " to " + to.getDescription());
        }
    }
}
