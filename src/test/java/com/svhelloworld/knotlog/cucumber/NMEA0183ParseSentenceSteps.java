package com.svhelloworld.knotlog.cucumber;

import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.svhelloworld.knotlog.messages.VesselMessage;
import com.svhelloworld.knotlog.messages.validate.MessageValidator;
import com.svhelloworld.knotlog.service.NMEA0183ParseService;
import com.svhelloworld.knotlog.service.impl.NMEA0183ParseServiceImpl;

import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Cucumber steps to parse NMEA0183 sentences.
 */
public class NMEA0183ParseSentenceSteps {

    //FIXME - this should be injected by Spring
    private NMEA0183ParseService parseService;

    private String candidateSentence;

    private List<VesselMessage> messages;

    @Before
    public void setup() {
        parseService = new NMEA0183ParseServiceImpl(); //FIXME - should be injected by Spring
        messages = new LinkedList<VesselMessage>();
    }

    @Given("^this NMEA0183 sentence from an instrument: \"([^\"]*)\"$")
    public void thisNMEA0183SentenceFromAnInstrument(String sentence) throws Throwable {
        candidateSentence = sentence;
    }

    @When("^the NMEA0183 sentence is parsed$")
    public void theNMEA0183SentenceIsParsed() throws Throwable {
        /*
         * FIXME
         * This is just test code to see if my test code (whut?) works.
         * 
         * 25°31.3369'N 111°4.4274'W
         */
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
        Map<String, String> expectedAttributes = attributesTable.asMap(String.class, String.class);
        MessageValidator validator = attribs.getValidator();
        validator.assertValid(getMessage(attribs.getType()), expectedAttributes);
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
