package com.svhelloworld.knotlog.util;

import java.io.InputStream;

/**
 * Some utility methods to deal with input/output.
 * 
 * @author Jason Andersen
 * @since Feb 17, 2010
 *
 */
public class IOUtil {
    
    /**
     * Private constructor.
     */
    private IOUtil() {
        //no need to instantiate this class.
    }
    
    /**
     * Loads a resource from a classpath.
     * @param classPath - the class path to the file
     * @return InputStream
     */
    public static InputStream loadResourceFromClassPath(String classPath) {
        //This was a pain in the ass to figure out.
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(classPath);
    }
    
}
