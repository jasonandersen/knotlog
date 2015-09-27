package com.svhelloworld.knotlog.engine.parse;

import java.util.List;

/**
 * Message dictionaries define message types that can
 * be found from instrument sentences.
 * 
 * @author Jason Andersen
 * @since Feb 17, 2010
 *
 */
public interface MessageDictionary {
    /**
     * Initializes the message dictionary. Must be called first.
     * @param params initialization parameters
     * @throws MessageDictionaryException if an exception occurs during loading
     * the message dictionary. 
     */
    public void initialize(Object... params) throws MessageDictionaryException;
    /**
     * @param identifier sentence identifier
     * @return all the message definitions for a specified identifier.
     * Can return an empty list but must not return a null object.
     */
    public List<InstrumentMessageDefinition> getDefinitions(String identifier);
    /**
     * @return all message definitions defined in this dictionary
     */
    public List<InstrumentMessageDefinition> getAllDefinitions();
}
