package com.svhelloworld.knotlog.cucumber;

import static org.junit.Assert.fail;

import org.springframework.beans.factory.annotation.Autowired;

import com.svhelloworld.knotlog.measure.DistanceUnit;
import com.svhelloworld.knotlog.messages.VesselMessage;
import com.svhelloworld.knotlog.messages.VesselMessages;
import com.svhelloworld.knotlog.service.Preferences;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

/**
 * Cucumber steps for acceptance tests for distance unit preferences.
 */
public class DistanceUnitPreferencesSteps extends BaseCucumberSteps {

    @Autowired
    private Preferences preferences;

    @Given("^I have selected \"([^\"]*)\" as my preferred water depth unit$")
    public void iHaveSelectedAsMyPreferredWaterDepthUnit(String unit) throws Throwable {
        DistanceUnit distance = DistanceUnit.valueOf(unit.toUpperCase());
        preferences.put(Preferences.KEY_DEPTH_UNIT, distance);
    }

    @Then("^I see this message: \"([^\"]*)\"$")
    public void iSeeThisMessage(String messageDescription) throws Throwable {
        VesselMessages messages = get(KEY_MESSAGES);
        for (VesselMessage message : messages) {
            if (message.getDisplayMessage().equals(messageDescription)) {
                return;
            }
        }
        fail(String.format("Could not find message: %s", messageDescription));
    }

    /**
     * @see com.svhelloworld.knotlog.cucumber.BaseCucumberSteps#tearDown()
     */
    @Override
    @After
    public void tearDown() {
        tearDownTestContext();
    }

}
