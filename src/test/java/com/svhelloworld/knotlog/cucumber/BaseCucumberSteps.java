package com.svhelloworld.knotlog.cucumber;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.svhelloworld.knotlog.engine.parse.MessageFailure;
import com.svhelloworld.knotlog.messages.UnrecognizedMessage;
import com.svhelloworld.knotlog.messages.VesselMessage;
import com.svhelloworld.knotlog.messages.VesselMessages;
import com.svhelloworld.knotlog.util.Now;

/**
 * Base class that all cucumber step definition classes should extend.
 */
@ContextConfiguration(locations = { "classpath:features/cucumber.xml" })
public abstract class BaseCucumberSteps {

    private static final Logger log = LoggerFactory.getLogger(BaseCucumberSteps.class);

    protected static final String KEY_SENTENCE = "nmea0183.sentence";
    protected static final String KEY_MESSAGES = "vessel.messages";

    @Autowired
    private TestContext context;

    /**
     * Every child class needs to implement this method, add the @After annotation
     * and call the tearDownTestContext() method. I do it this way because
     * Cucumber doesn't allow an @After hook from an abstract class.
     */
    public abstract void tearDown();

    /**
     * Clean up after all tests.
     */
    protected void tearDownTestContext() {
        log.debug("tearing down");
        context.reset();
        Now.resetNowProvider();
    }

    /**
     * Retrieve a value out of the test context.
     * @param key
     * @return
     */
    protected <T> T get(String key) {
        return context.get(key);
    }

    /**
     * Set a value into test context.
     * @param key
     * @param value
     */
    protected void set(String key, Object value) {
        context.set(key, value);
    }

    /**
     * Asserts that a specific type of vessel message was returned. Will throw an assertion failure if
     * the message type wasn't found.
     * @param messageType
     */
    protected void assertMessageTypeWasReturned(Class<? extends VesselMessage> messageType, String messageDesc) {
        if (getMessage(messageType) != null) {
            return;
        }
        fail(String.format("Could not find a %s message", messageDesc));
    }

    /**
     * @param type
     * @return a {@link VesselMessage} with a matching type
     */
    protected VesselMessage getMessage(Class<? extends VesselMessage> type) {
        for (VesselMessage message : getVesselMessages()) {
            if (message.getClass().equals(type)) {
                return message;
            }
        }
        return null;
    }

    /**
     * @return the candidate NMEA0183 sentence
     */
    protected String getCandidateSentence() {
        return get(KEY_SENTENCE);
    }

    /**
     * @return vessel messages returned from parsing
     */
    protected VesselMessages getVesselMessages() {
        return get(KEY_MESSAGES);
    }

    /**
     * Asserts that an unrecognized message was returned with the proper failure type
     * @param failure
     */
    protected void assertMessageFailure(MessageFailure failure) {
        VesselMessages messages = getVesselMessages();
        Assert.assertFalse("No unrecognized messages were found", messages.getUnrecognizedMessages().isEmpty());
        UnrecognizedMessage message = messages.getUnrecognizedMessages().get(0);
        Assert.assertEquals(failure, message.getFailureMode());
    }

}
