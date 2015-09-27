package com.svhelloworld.knotlog.i18n;

import java.util.List;

/**
 * Defines an object as one that can be displayed in the 
 * user interface and therefore localizable. The value
 * from <code>getLocalizeKey()</code> will be used to pull
 * a localized string message from a <code>ResourceBundle</code>.
 * If <code>getLocalizeParams()</code> returns a non-empty
 * <code>List</code>, the value from <code>getLocalizeKey()</code>
 * will be presumed to be a <code>printf</code> style statement
 * and will be passed on to <code>String.format()</code> for
 * formatting.<p />
 * 
 * @author Jason Andersen
 * @see java.lang.String#format(java.lang.String, java.lang.Object...)
 * @see java.util.Formatter
 * @see com.svhelloworld.knotlog.i18n.BabelFish
 * @since Mar 3, 2010
 */
public interface Localizable {
    /**
     * @return a key that can be used to pull a string 
     * out of a resource bundle. Can not be null.
     */
    public String getLocalizeKey();
    /**
     * @return a set of parameters to pass to a resource bundle 
     * along with the key to localize a string. Not required, 
     * can return null if not necessary. If <code>getLocalizeParams()</code>
     * is not null, the localized message will be passed to
     * <code>String.format()</code> along with the return value
     * from <code>getLocalizeParams()</code> to pattern the result.
     */
    public List<Object> getLocalizeParams();
}
