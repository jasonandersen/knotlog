package com.svhelloworld.knotlog.engine.parse;

import java.util.List;

import com.svhelloworld.knotlog.i18n.BabelFish;
import com.svhelloworld.knotlog.i18n.Localizable;

/**
 * Indicates the cause of a failure to generate a valid
 * vessel message.
 * 
 * @author Jason Andersen
 * @since Mar 12, 2010
 *
 */
public enum MessageFailure implements Localizable {
    /**
     * Indicates an instrument sentence was not properly
     * formed.
     */
    MALFORMED_SENTENCE("msg.failure.malformed.sentence"),
    /**
     * Indicates an instrument sentence was properly formed
     * but of a type not recognized.
     */
    UNRECOGNIZED_SENTENCE("msg.failure.unrecognized.sentence"),
    /**
     * Indicates an instrument sentence did not contain 
     * expected data to create message.
     */
    INVALID_SENTENCE_FIELDS("msg.failure.invalid.fields");
    
    /**
     * Description key.
     */
    private String descKey;
    
    /**
     * Constructor.
     * @param descKey
     */
    MessageFailure(String descKey) {
        this.descKey = descKey;
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
}
