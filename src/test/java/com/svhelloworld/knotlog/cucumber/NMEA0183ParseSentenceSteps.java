package com.svhelloworld.knotlog.cucumber;

import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.svhelloworld.knotlog.engine.parse.MessageFailure;
import com.svhelloworld.knotlog.messages.MessageAttributeValidator;
import com.svhelloworld.knotlog.messages.UnrecognizedMessage;
import com.svhelloworld.knotlog.messages.VesselMessage;
import com.svhelloworld.knotlog.messages.VesselMessages;
import com.svhelloworld.knotlog.service.NMEA0183ParseService;
import com.svhelloworld.knotlog.util.Now;
import com.svhelloworld.knotlog.util.NowTestingProvider;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
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

    @Autowired
    private NMEA0183ParseService parseService;

    private String candidateSentence;

    private VesselMessages messages;

    @Before
    public void setup() {
        //parseService = new NMEA0183SentenceParser();
    }

    @After
    public void tearDown() {
        Now.resetNowProvider();
    }

    @Given("^this NMEA0183 sentence from an instrument: \"([^\"]*)\"$")
    public void thisNMEA0183SentenceFromAnInstrument(String sentence) throws Throwable {
        candidateSentence = sentence;
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

    @Given("^an NMEA0183 sentence that transmits GMT time of day with a time field of \"([^\"]*)\"$")
    public void anNMEASentenceThatTransmitsGMTTimeOfDayWithATimeFieldOf(String timeField) throws Throwable {
        String ggaSentence = String.format(TIME_OF_DAY_SENTENCE, timeField);
        thisNMEA0183SentenceFromAnInstrument(ggaSentence);
    }

    @When("^the NMEA0183 sentence is parsed$")
    public void theNMEA0183SentenceIsParsed() throws Throwable {
        messages = parseService.parseSentence(candidateSentence);
    }

    @Then("^(.+) is returned$")
    public void aMessageIsReturned(String messageDesc) throws Throwable {
        MessageAttributes attribs = MessageAttributes.findByDescription(messageDesc);
        Class<? extends VesselMessage> messageType = attribs.getType();
        assertMessageTypeWasReturned(messageType, messageDesc);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Then("^this (.+) is returned:$")
    public void thisMessageIsReturned(String messageDesc, DataTable attributesTable) throws Throwable {
        MessageAttributes attribs = MessageAttributes.findByDescription(messageDesc);
        assertMessageTypeWasReturned(attribs.getType(), messageDesc);
        Map<String, String> expectedAttributes = attributesTable.asMap(String.class, String.class);
        MessageAttributeValidator validator = attribs.getValidator();
        VesselMessage message = getMessage(attribs.getType());
        validator.assertMessageAttributes(message, expectedAttributes);
    }

    @Then("^a proper vessel message was found$")
    public void aProperVesselMessageWasFound() {
        Assert.assertFalse(messages.isEmpty());
    }

    @Then("^there are no unrecognized messages$")
    public void thereAreNoUnrecognizedMessages() throws Throwable {
        Assert.assertTrue(messages.getUnrecognizedMessages().isEmpty());
    }

    @Then("^the sentence is not recognized$")
    public void aMessageIsReturnThatIndicatesThatSentenceWasNotRecognized() throws Throwable {
        assertMessageFailure(MessageFailure.UNRECOGNIZED_SENTENCE);
    }

    @Then("^the sentence is malformed$")
    public void aMessageIsReturnThatIndicatesThatSentenceWasMalformed() throws Throwable {
        assertMessageFailure(MessageFailure.MALFORMED_SENTENCE);
    }

    @Then("^the sentence has invalid sentence fields$")
    public void theSentenceHasInvalidSentenceFields() throws Throwable {
        assertMessageFailure(MessageFailure.INVALID_SENTENCE_FIELDS);
    }

    /**
     * Asserts that an unrecognized message was returned with the proper failure type
     * @param failure
     */
    private void assertMessageFailure(MessageFailure failure) {
        Assert.assertFalse("No unrecognized messages were found", messages.getUnrecognizedMessages().isEmpty());
        UnrecognizedMessage message = messages.getUnrecognizedMessages().get(0);
        Assert.assertEquals(failure, message.getFailureMode());
    }

    /**
     * @param type
     * @return a {@link VesselMessage} with a matching type
     */
    private VesselMessage getMessage(Class<? extends VesselMessage> type) {
        for (VesselMessage message : messages) {
            if (message.getClass().equals(type)) {
                return message;
            }
        }
        return null;
    }

    /**
     * Asserts that a specific type of vessel message was returned. Will throw an assertion failure if
     * the message type wasn't found.
     * @param messageType
     */
    private void assertMessageTypeWasReturned(Class<? extends VesselMessage> messageType, String messageDesc) {
        if (getMessage(messageType) != null) {
            return;
        }
        fail(String.format("Could not find a %s message", messageDesc));
    }

}
