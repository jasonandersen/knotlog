package com.svhelloworld.knotlog.i18n;

/**
 *  <h3>**** EXPERIMENTAL - NOT CURRENTLY USED ***</h3>
 * 
 * Any methods marked with @Localize that return a string are 
 * considered to be localizable methods and the string will be
 * presumed to be a key to return a localized string out of a
 * resource bundle.<p />
 * 
 * Development note:
 * Can we use this annotation coupled with an AOP framework to
 * automatically localize UI strings? Aspects could also be used
 * to intercept toString() calls on <tt>Localizable</tt> objects
 * and return the localized string.
 * Problem: 
 * How do we handle parameterized strings?
 * 
 * @author Jason Andersen
 * @since Mar 6, 2010
 *
 */
public @interface Localize {/* experimental */}