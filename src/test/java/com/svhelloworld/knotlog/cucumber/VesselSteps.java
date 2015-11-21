package com.svhelloworld.knotlog.cucumber;

import com.svhelloworld.knotlog.domain.Vessel;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;

/**
 * Cucumber steps to deal with {@link Vessel}s.
 */
public class VesselSteps extends BaseCucumberSteps {

    @Given("^I have no vessels$")
    public void iHaveNoVessels() throws Throwable {

    }

    /**
     * @see com.svhelloworld.knotlog.cucumber.BaseCucumberSteps#tearDown()
     */
    @After
    @Override
    public void tearDown() {
        super.tearDownTestContext();
    }

}
