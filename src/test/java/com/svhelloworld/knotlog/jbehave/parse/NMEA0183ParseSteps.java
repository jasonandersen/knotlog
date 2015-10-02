package com.svhelloworld.knotlog.jbehave.parse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Date;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.svhelloworld.knotlog.measure.DistanceUnit;
import com.svhelloworld.knotlog.messages.VesselMessage;
import com.svhelloworld.knotlog.messages.VesselMessageSource;
import com.svhelloworld.knotlog.messages.WaterDepth;
import com.svhelloworld.knotlog.service.NMEA0183ParseService;

/**
 * JBehave steps to run acceptance tests for NMEA0183 parsing.
 */
public class NMEA0183ParseSteps {

    //FIXME - this should be injected from Spring!
    private NMEA0183ParseService parseService = new MockParser();

    //FIXME - this is a hack right now around not being able to do a test context
    private String candidateSentence;
    private VesselMessage message;

    @Given("this NMEA0183 sentence from an instrument: \"$message")
    public void givenThisMessageFromAnInstrument(String sentence) {
        this.candidateSentence = sentence;
    }

    @When("the NMEA0183 sentence is parsed")
    public void whenTheSentenceIsParsed() {
        if (candidateSentence == null) {
            fail("couldn't find the sentence to parse");
        }
        parseSentence(candidateSentence);
    }

    @Then("the sentence is interpreted as a water depth message")
    public void thenTheMessageIsInterpretedAsAWaterDepthMessage() {
        assertNotNull(message);
        assertEquals(WaterDepth.class, message.getClass());
    }

    @Then("the water depth is $waterDepth $unit")
    public void thenTheWaterDepthIsFeet(float waterDepth, String unit) {
        WaterDepth depth = (WaterDepth) message;
        assertEquals(waterDepth, depth.getWaterDepth(), 0.01);
        assertEquals(unit, depth.getDistanceUnit().toString());
    }

    private void parseSentence(String sentence) {
        message = parseService.parseSentence(candidateSentence);
    }

    private class MockParser implements NMEA0183ParseService {

        /**
         * @see com.svhelloworld.knotlog.service.NMEA0183ParseService#parseSentence(java.lang.String)
         */
        @Override
        public VesselMessage parseSentence(String sentence) {
            return new WaterDepth(VesselMessageSource.NMEA0183, new Date(), 20.8f, DistanceUnit.FEET);
        }

    }

}
