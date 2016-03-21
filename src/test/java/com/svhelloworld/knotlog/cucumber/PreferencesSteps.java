package com.svhelloworld.knotlog.cucumber;

import org.springframework.beans.factory.annotation.Autowired;

import com.svhelloworld.knotlog.measure.DistanceUnit;
import com.svhelloworld.knotlog.preferences.Preferences;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;

/**
 * Cucumber steps for setting and validating preferences.
 */
public class PreferencesSteps extends BaseCucumberSteps {

    @Autowired
    private Preferences preferences;

    /**
     * @see com.svhelloworld.knotlog.cucumber.BaseCucumberSteps#tearDown()
     */
    @Override
    @After
    public void tearDown() {
        tearDownTestContext();
    }

    @Given("^I have selected \"([^\"]*)\" as my preferred water depth unit$")
    public void iHaveSelectedAsMyPreferredWaterDepthUnit(String unit) throws Throwable {
        DistanceUnit distance = DistanceUnit.valueOf(unit.toUpperCase());
        preferences.put(Preferences.KEY_DEPTH_UNIT, distance);
    }

}
