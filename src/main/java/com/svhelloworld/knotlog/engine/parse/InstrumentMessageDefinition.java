package com.svhelloworld.knotlog.engine.parse;

import java.util.Arrays;
import java.util.List;

/**
 * Defines how a message is constructed from an instrument
 * sentence.
 * 
 * @author Jason Andersen
 * @since Feb 17, 2010
 *
 */
public class InstrumentMessageDefinition {
    /**
     * Instrument sentence identifier
     */
    private String identifier;
    /**
     * Message class name. If not a fully qualified class name, then
     * a default message package is presumed.
     */
    private String msgClassName;
    /**
     * Parameters define which sentence fields are passed to the message
     * class upon creation.
     */
    private List<String> parameters;
    
    /**
     * Constructor
     * @param identifier sentence type identifier
     * @param className name of instrument message to instantiate
     * @param parameters parameters to pass to the instrument message class
     */
    public InstrumentMessageDefinition(
            String identifier, 
            String className, 
            List<String> parameters) {
        this.identifier = identifier;
        this.msgClassName = className;
        this.parameters = parameters;
    }

    /**
     * @return instrument sentence identifier
     */
    public String getIdentifier() {
        return identifier;
    }
    
    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    
    /**
     * @return message class name. If not a fully qualified class name, then
     * a default message package is presumed.
     */
    public String getMessageClassName() {
        return msgClassName;
    }
    
    /**
     * @param className the className to set
     */
    public void setClassName(String className) {
        this.msgClassName = className;
    }
    
    /**
     * @return parameters define which sentence fields are passed to the message
     * class upon creation.
     */
    public List<String> getParameters() {
        return parameters;
    }
    
    /**
     * @param parameters the parameters to set
     */
    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String params = Arrays.toString(parameters.toArray(new String[0]));
        return String.format("%s,%s,%s", identifier, msgClassName, params);
    }
}
