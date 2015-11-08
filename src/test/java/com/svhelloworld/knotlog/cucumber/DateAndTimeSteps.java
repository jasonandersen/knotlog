package com.svhelloworld.knotlog.cucumber;

import com.svhelloworld.knotlog.util.Now;
import com.svhelloworld.knotlog.util.NowTestingProvider;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;

/**
 * Cucumber steps to deal with time and date functions.
 */
public class DateAndTimeSteps extends BaseCucumberSteps {

    /**
     * @see com.svhelloworld.knotlog.cucumber.BaseCucumberSteps#tearDown()
     */
    @After
    @Override
    public void tearDown() {
        tearDownTestContext();
    }

    /**
     * Change the current date and time for the purposes of this test.
     * @param currentLocalDateTime in ISO_OFFSET_DATE_TIME format
     */
    @Given("^the current local date and time is \"([^\"]*)\"$")
    public void theCurrentLocalDateAndTimeIs(String currentLocalDateTime) throws Throwable {
        NowTestingProvider provider = new NowTestingProvider(currentLocalDateTime);
        Now.setNowProvider(provider);
    }

}
