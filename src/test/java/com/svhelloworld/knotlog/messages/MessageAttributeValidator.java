package com.svhelloworld.knotlog.messages;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;

import com.svhelloworld.knotlog.messages.VesselMessage;

/**
 * Provides some basic functionality for validating common attributes.
 */
public abstract class MessageAttributeValidator<M extends VesselMessage> {

    /**
     * Date format that expected dates should be in when validating date values.
     */
    public static final String EXPECTED_DATE_FORMAT = "YYYY/MM/dd";

    private static final String SOURCE = "source";

    private static final String TODAY = "[today]";

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
        preprocessAttributes(message, attributes);
        //setup actual attributes
        buildActualAttributes(message, attributes);
        assertAttributes(attributes);
    }

    /**
     * Handle any common preprocessing for the attributes maps.
     * @param message
     * @param expectedAttributes
     */
    private void preprocessAttributes(M message, Map<String, Attribute> attributes) {
        //change [today] expected attribute to today's date in YYYY/MM/DD format
        for (Attribute attribute : attributes.values()) {
            if (attribute.expectedValue.equals(TODAY)) {
                SimpleDateFormat format = new SimpleDateFormat(EXPECTED_DATE_FORMAT);
                attribute.setExpectedValue(format.format(new Date()));
            }
        }
        //setup source actual value
        setActualAttributeValue(SOURCE, attributes, message.getSource().toString());

    }

    /**
     * Child classes will implement this to populate actual attribute values.
     * @param message
     * @param attributes
     */
    protected abstract void buildActualAttributes(M message, Map<String, Attribute> attributes);

    /**
     * Sets a single actual attribute value.
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
     * Assert all expected attribute values match actual attribute values.
     * @param attributes
     */
    private void assertAttributes(Map<String, Attribute> attributes) {
        for (String description : attributes.keySet()) {
            Attribute attribute = attributes.get(description);
            if (!attribute.isValid()) {
                Assert.fail(String.format("expected %s to be \"%s\" but was actually \"%s\"",
                        description, attribute.getExpectedValue(), attribute.getActualValue()));
            }
        }
    }

    /**
     * A single attribute to test against.
     */
    public class Attribute {
        protected String expectedValue;
        protected String actualValue;

        public Attribute(String expectedValue) {
            this.expectedValue = expectedValue;
        }

        public String getExpectedValue() {
            return expectedValue;
        }

        public void setExpectedValue(String expected) {
            this.expectedValue = expected;
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
