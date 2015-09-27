package com.svhelloworld.knotlog.i18n;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class localizes strings for the user interface. Any object
 * that implements the <tt>Localizable</tt> interface can be
 * passed to the <tt>localize</tt> method to return a localized
 * string.<p />
 * 
 * Keys to pull messages out of a resource bundle can be passed to
 * the <tt>localizeKey</tt> method to retrieve the localized
 * string.<p />
 * 
 * Any <tt>Localizable</tt> object that returns a non-null
 * <tt>List</tt> from <tt>getLocalizeParams()</tt> or
 * any call to <tt>localizeKey</tt> that passes in a non-empty 
 * <tt>List</tt> will treat the resulting string as a <tt>printf</tt>
 * style pattern string and will be passed on to <tt>String.format()</tt>
 * for formatting.
 * 
 * @author Jason Andersen
 * @since Mar 3, 2010
 * @see java.lang.String#format(java.lang.String, java.lang.Object...)
 * @see java.util.Formatter
 * @see com.svhelloworld.knotlog.i18n.Localizable
 */
public class BabelFish {
    /**
     * Path to resource bundle.
     */
    private static final String BUNDLE = "com/svhelloworld/knotlog/i18n/KnotLog";
    
    /**
     * Constructor.
     */
    private BabelFish() {
        //no instantiation necessary
    }
    
    /**
     * Localizes an object based on the default user locale.
     * @param target localizable object
     * @throws NullPointerException if target is null
     * @throws IllegalArgumentException if target.getLocalizeKey() is null or empty string
     * @return localized string.
     */
    public static String localize(final Localizable target) {
        Locale defaultLocale = Locale.getDefault();
        return localize(target, defaultLocale);
    }
    
    /**
     * Localizes an object based on the locale supplied.
     * @param target localizable object
     * @param locale user locale
     * @throws NullPointerException if target is null
     * @throws IllegalArgumentException if <tt>target.getLocalizeKey()</tt>
     *          is null or empty string
     * @return localized string
     */
    public static String localize(final Localizable target, final Locale locale) {
        if (target == null) {
            throw new NullPointerException("Target cannot be null.");
        }
        final String key = target.getLocalizeKey();
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("Localize key cannot be null");
        }
        final List<Object> params = target.getLocalizeParams();
        
        return localizeKey(key, locale, params);
    }
    
    /**
     * Localizes a specific key
     * @param key message key
     * @return a localized string
     */
    public static String localizeKey(final String key) {
        return localizeKey(key, Locale.getDefault());
    }
    
    /**
     * Localizes a specific key using <tt>printf</tt> style formatting.
     * @param key 
     * @param params parameters to pattern string
     * @return a localized string
     */
    public static String localizeKey(final String key, final Object... params) {
        return localizeKey(key, Locale.getDefault(), params);
    }
    
    /**
     * Localizes a specific key.
     * @param key
     * @param locale user local
     * @param params parameters to pattern string, can be null
     * @return a localized string, if <tt>params</tt> are passed in, the
     * resulting localized string will formatted using <tt>printf</tt>
     * style formatting.
     */
    public static String localizeKey(
            final String key, 
            final Locale locale, 
            final Object... params) {
        
        return localizeKey(key, locale, Arrays.asList(params));
    }
    
    /**
     * Localizes a specific key.
     * @param key
     * @param locale user local
     * @param params parameters to pattern string, can be null
     * @return a localized string, if <tt>params</tt> are passed in, the
     * resulting localized string will formatted using <tt>printf</tt>
     * style formatting.
     */
    public static String localizeKey(
            final String key, 
            final Locale locale, 
            final List<Object> params) {
        
        String message = getBundle(locale).getString(key);
        if (params != null && !params.isEmpty()) {
            /*
             * if parameters are passed in, presume the message is a printf
             * style pattern string and pattern it as such.
             */
            final List<Object> localizedParams = localizeParameters(params, locale);
            message = String.format(locale, message, localizedParams.toArray(new Object[0]));
        }
        return message;
    }
    
    /**
     * @param locale user locale
     * @return a ResourceBundle containing messages pertinent to the
     * user interface.
     */
    private static ResourceBundle getBundle(final Locale locale) {
        return ResourceBundle.getBundle(BUNDLE, locale);
    }
    
    /**
     * Iterates through the parameters array. Any <tt>Localizable</tt>
     * objects found in the parameters array will in turn be localized.
     * @param params parameter list, can be null
     * @param locale user locale
     * @return parameter array with any <tt>Localizable</tt> objects
     * replaced with their localized string. Can return an empty list if the 
     * <tt>params</tt> argument is null. Will not return null.
     */
    private static List<Object> localizeParameters(
            final List<Object> params, 
            final Locale locale) {
        
        if (params == null) {
            return new ArrayList<Object>();
        }
        List<Object> out = new ArrayList<Object>(params.size());
        for (Object param : params) {
            if (param instanceof Localizable) {
                String localized = localize((Localizable)param, locale);
                out.add(localized);
            } else {
                out.add(param);
            }
        }
        return out;
    }
    
}
