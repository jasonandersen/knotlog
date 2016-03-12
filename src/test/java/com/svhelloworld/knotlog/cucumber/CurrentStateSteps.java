package com.svhelloworld.knotlog.cucumber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.svhelloworld.knotlog.engine.parse.NMEA0183Sentence;
import com.svhelloworld.knotlog.ui.controller.CurrentStateController;
import com.svhelloworld.knotlog.ui.currentstate.VesselMessageView;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

/**
 * Cucumber steps to validate the current state UI.
 */
public class CurrentStateSteps extends BaseCucumberSteps {

    private static Logger log = LoggerFactory.getLogger(CurrentStateSteps.class);

    @Autowired
    private CurrentStateController controller;

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

    @Given("^these NMEA0183 sentences from instruments:$")
    public void theseNMEASentencesFromInstruments(List<String> sentences) throws Throwable {
        for (String sentence : sentences) {
            log.debug("posting sentence: {}", sentence);
            postEvent(new NMEA0183Sentence(sentence));
        }
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
