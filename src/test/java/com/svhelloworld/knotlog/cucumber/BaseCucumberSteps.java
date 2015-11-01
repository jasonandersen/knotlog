package com.svhelloworld.knotlog.cucumber;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.svhelloworld.knotlog.util.Now;

/**
 * Base class that all cucumber step definition classes should extend.
 */
@ContextConfiguration(locations = { "classpath:features/cucumber.xml" })
public abstract class BaseCucumberSteps {

    private static final Logger log = Logger.getLogger(BaseCucumberSteps.class);

    protected static final String KEY_SENTENCE = "nmea0183.sentence";
    protected static final String KEY_MESSAGES = "vessel.messages";

    @Autowired
    private TestContext context;

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
    public <T> T get(String key) {
        return context.get(key);
    }

    /**
     * Set a value into test context.
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        context.set(key, value);
    }
}
