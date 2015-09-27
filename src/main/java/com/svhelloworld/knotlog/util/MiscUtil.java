package com.svhelloworld.knotlog.util;

import java.util.Arrays;
import java.util.List;

/**
 * Miscellaneous static utility methods.
 * 
 * @author Jason Andersen
 * @since Mar 3, 2010
 *
 */
public class MiscUtil {
    
    /**
     * Constructor.
     */
    private MiscUtil() {
        //no instantiation necessary
    }
    
    /**
     * Converts variable arguments into a <code>List</code> of objects.
     * @param args varargs
     * @return a <code>List</code> of objects.
     */
    public static List<Object> varargsToList(Object... args) {
        return Arrays.asList(args);
    }
}
