package com.svhelloworld.knotlog.util;

/**
 * Any string utilities to make our lives easier.
 * 
 * @author Jason Andersen
 * @since Feb 23, 2010
 *
 */
public class StringUtil {
    
    /**
     * Private constructor.
     */
    private StringUtil() {
        //no need to instantiate
    }
    
    /**
     * I'm sure this exists somewhere in the Java API but I can't
     * seem to find it so I'm just going to write it here.
     * @param string
     * @return true if string represents an integer, otherwise false
     */
    public static boolean isInteger(final String string) {
        try {
            @SuppressWarnings("unused")
            int integer = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * I'm sure this exists somewhere in the Java API but I can't
     * seem to find it so I'm just going to write it here.
     * @param string
     * @return true if string represents a float, otherwise false
     */
    public static boolean isFloat(final String string) {
        try {
            @SuppressWarnings("unused")
            float number = Float.parseFloat(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    
}
