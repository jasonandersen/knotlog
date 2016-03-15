package com.svhelloworld.knotlog.cucumber;

import com.google.common.eventbus.Subscribe;
import com.svhelloworld.knotlog.domain.messages.UnrecognizedMessage;
import com.svhelloworld.knotlog.domain.messages.ValidVesselMessage;
import com.svhelloworld.knotlog.domain.messages.VesselMessages;
import com.svhelloworld.knotlog.engine.parse.MessageFailure;
import com.svhelloworld.knotlog.engine.parse.NMEA0183Sentence;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Cucumber steps to parse NMEA0183 sentences.
 */
public class NMEA0183ParseSentenceSteps extends BaseCucumberSteps {

    /**
     * An NMEA0183 sentence that transmits a time of day formatted so we can pass in the time of day field
     */
    private static final String TIME_OF_DAY_SENTENCE = "$GPGGA,%s,2531.3369,N,11104.4274,W,2,09,0.9,1.7,M,-31.5,M,,";

    @Override
    @After
    public void tearDown() {
        tearDownTestContext();
    }

    @Given("^this NMEA0183 sentence from an instrument: \"([^\"]*)\"$")
    public void thisNMEA0183SentenceFromAnInstrument(String sentence) throws Throwable {
        set(KEY_SENTENCE, sentence);
    }

    @Given("^an NMEA0183 sentence that transmits GMT time of day with a time field of \"([^\"]*)\"$")
    public void anNMEASentenceThatTransmitsGMTTimeOfDayWithATimeFieldOf(String timeField) throws Throwable {
        String ggaSentence = String.format(TIME_OF_DAY_SENTENCE, timeField);
        thisNMEA0183SentenceFromAnInstrument(ggaSentence);
    }

    @When("^the NMEA0183 sentence is parsed$")
    public void theNMEA0183SentenceIsParsed() throws Throwable {
        NMEA0183Sentence sentence = new NMEA0183Sentence(getCandidateSentence());
        postEvent(sentence);
    }

    @Then("^the sentence has invalid sentence fields$")
    public void theSentenceHasInvalidSentenceFields() throws Throwable {
        assertMessageFailure(MessageFailure.INVALID_SENTENCE_FIELDS);
    }

    /*
     * Event bus handler methods
     */

    /**
     * Store any vessel messages event in the test context.
     * @param event
     */
    @Subscribe
    public void vesselMessageDiscovered(ValidVesselMessage message) {
        VesselMessages messages = get(KEY_MESSAGES);
        if (messages == null) {
            messages = new VesselMessages();
        }
        messages.add(message);
        set(KEY_MESSAGES, messages);
    }

    /**
     * Store any unrecognized message in the test context.
     * @param event
     */
    @Subscribe
    public void unrecognizedMessageDiscovered(UnrecognizedMessage message) {
        set(KEY_UNRECOGNIZED_MESSAGE, message);
    }

}
