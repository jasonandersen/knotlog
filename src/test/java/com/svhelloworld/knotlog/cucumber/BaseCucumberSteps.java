package com.svhelloworld.knotlog.cucumber;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.eventbus.EventBus;
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
    protected static final String KEY_UNRECOGNIZED_MESSAGE = "unrecognized.message";

    private EventBus eventBus;

    @Autowired
    private TestContext textContext;

    /**
     * Set the event bus and register this instance to it.
     * @param eventBus
     */
    @Autowired
    private void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.register(this);
    }

    /**
     * Every child class needs to implement this method, add the @After annotation
     * and call the tearDownTestContext() method. I do it this way because
     * Cucumber doesn't allow an @After hook from an abstract class.
     */
    public abstract void tearDown();

    /**
     * Post event to the event bus.
     * @param event
     */
    protected void postEvent(Object event) {
        eventBus.post(event);
    }

    /**
     * Clean up after all tests. Reset test context so it's ready and clean for the next test.
     */
    protected void tearDownTestContext() {
        log.debug("tearing down");
        textContext.reset();
        Now.resetNowProvider();
    }

    /**
     * Retrieve a value out of the test context.
     * @param key
     * @return
     */
    protected <T> T get(String key) {
        return textContext.get(key);
    }

    /**
     * Set a value into test context.
     * @param key
     * @param value
     */
    protected void set(String key, Object value) {
        textContext.set(key, value);
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
     * @return unrecognized message returned from parsing
     */
    protected UnrecognizedMessage getUnrecognizedMessage() {
        return get(KEY_UNRECOGNIZED_MESSAGE);
    }

    /**
     * Asserts that an unrecognized message was returned with the proper failure type
     * @param failure
     */
    protected void assertMessageFailure(MessageFailure failure) {
        UnrecognizedMessage message = get(KEY_UNRECOGNIZED_MESSAGE);
        Assert.assertNotNull(message);
        Assert.assertEquals(failure, message.getFailureMode());
    }

}
