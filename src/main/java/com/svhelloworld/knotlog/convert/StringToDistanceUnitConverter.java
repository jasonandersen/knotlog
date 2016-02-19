package com.svhelloworld.knotlog.convert;

import org.apache.commons.lang.Validate;
import org.springframework.core.convert.converter.Converter;

import com.svhelloworld.knotlog.measure.DistanceUnit;

/**
 * Converts {@link String}s to {@link DistanceUnit}s.
 */
public class StringToDistanceUnitConverter implements Converter<String, DistanceUnit> {

    /**
     * @see org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    @Override
    public DistanceUnit convert(String source) {
        Validate.notNull(source);

        DistanceUnit[] units = DistanceUnit.values();
        for (DistanceUnit unit : units) {
            if (source.equalsIgnoreCase(unit.name())) {
                return unit;
            }
            if (abbreviationMatches(source, unit)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("could not convert " + source + " to a DistanceUnit");
    }

    /**
     * Determines if the source string matches any of the abbreviations for this distance unit.
     * @param source
     * @param unit
     * @return true if the source string
     */
    private boolean abbreviationMatches(String source, DistanceUnit unit) {
        for (String abbreviation : unit.getAbbreviations()) {
            if (abbreviation.equalsIgnoreCase(source)) {
                return true;
            }
        }
        return false;
    }

}
