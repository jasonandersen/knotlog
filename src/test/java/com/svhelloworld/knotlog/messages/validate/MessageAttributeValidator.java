package com.svhelloworld.knotlog.messages.validate;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;

import com.svhelloworld.knotlog.messages.VesselMessage;

/**
 * Provides some basic functionality for validating common attributes.
 */
public abstract class MessageAttributeValidator<M extends VesselMessage> {

    private static final String SOURCE = "source";

    /**
     * Assert message attributes.
     * @param message
     * @param expectedAttributes - map keyed by attribute description, values are expected values
     */
    public void assertMessageAttributes(M message, Map<String, String> expectedAttributes) {
        //setup attributes map
        Map<String, Attribute> attributes = new HashMap<String, Attribute>();
        for (String key : expectedAttributes.keySet()) {
            attributes.put(key, new Attribute(expectedAttributes.get(key)));
        }
        //setup source actual value
        setActualAttributeValue(SOURCE, attributes, message.getSource().toString());
        //setup actual attributes
        buildActualAttributes(message, attributes);
        assertAttributes(attributes);
    }

    /**
     * Child classes will implement this to populate actual attributes.
     * @param message
     * @param attributes
     */
    protected abstract void buildActualAttributes(M message, Map<String, Attribute> attributes);

    /**
     * Sets an actual attribute value.
     * @param description
     * @param attributes
     * @param actualValue
     */
    protected void setActualAttributeValue(String description, Map<String, Attribute> attributes, String actualValue) {
        Attribute attribute = attributes.get(description);
        if (attribute != null) {
            attribute.setActualValue(actualValue);
        }
    }

    /**
     * Assert all attributes.
     * @param attributes
     */
    private void assertAttributes(Map<String, Attribute> attributes) {
        for (String description : attributes.keySet()) {
            Attribute attribute = attributes.get(description);
            if (!attribute.isValid()) {
                Assert.fail(String.format("\"%s\" expected to be \"%s\" but was \"%s\"",
                        description, attribute.getExpectedValue(), attribute.getActualValue()));
            }
        }
    }

    /**
     * A single attribute to test against.
     */
    public class Attribute {
        protected final String expectedValue;
        protected String actualValue;

        public Attribute(String expectedValue) {
            this.expectedValue = expectedValue;
        }

        public String getExpectedValue() {
            return expectedValue;
        }

        public void setActualValue(String actualValue) {
            this.actualValue = actualValue;
        }

        public String getActualValue() {
            return actualValue;
        }

        public boolean isValid() {
            return expectedValue.equals(actualValue);
        }
    }

}
