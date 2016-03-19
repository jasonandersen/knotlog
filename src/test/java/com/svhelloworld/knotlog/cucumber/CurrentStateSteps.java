package com.svhelloworld.knotlog.cucumber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.svhelloworld.knotlog.ui.currentstate.CurrentStatePresenter;
import com.svhelloworld.knotlog.ui.views.VesselMessageView;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Then;

/**
 * Cucumber steps to validate the current state UI.
 */
public class CurrentStateSteps extends BaseCucumberSteps {

    @Autowired
    private CurrentStatePresenter controller;

    /**
     * @see com.svhelloworld.knotlog.cucumber.BaseCucumberSteps#tearDown()
     */
    @After
    @Override
    public void tearDown() {
        tearDownTestContext();
    }

    @Before
    public void setup() {
        controller.reset();
    }

    @Then("^my current state display should read:$")
    public void myCurrentStateDisplayShouldRead(List<List<String>> currentState) throws Throwable {
        final int NAME_INDEX = 0;
        final int VALUE_INDEX = 1;
        final int SOURCE_INDEX = 2;
        assertFalse("no messages found", controller.getCurrentStateMessages().isEmpty());
        for (int expectedValuesRowIndex = 1; expectedValuesRowIndex < currentState.size(); expectedValuesRowIndex++) {
            VesselMessageView<?> actualValues = controller.getCurrentStateMessages().get(expectedValuesRowIndex - 1);
            List<String> expectedValues = currentState.get(expectedValuesRowIndex);
            assertEquals(expectedValues.get(NAME_INDEX), actualValues.getLabel());
            assertEquals(expectedValues.get(VALUE_INDEX), actualValues.getValue());
            assertEquals(expectedValues.get(SOURCE_INDEX), actualValues.getSource());
        }
    }

}
