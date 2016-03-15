package com.svhelloworld.knotlog.cucumber;

import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Assert;

import com.svhelloworld.knotlog.domain.messages.VesselMessage;
import com.svhelloworld.knotlog.domain.messages.VesselMessages;
import com.svhelloworld.knotlog.engine.parse.MessageFailure;
import com.svhelloworld.knotlog.messages.MessageAttributeValidator;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.Then;

/**
 * Cucumber steps for reading and validating vessel messages.
 */
public class VesselMessagesSteps extends BaseCucumberSteps {

    /**
     * @see com.svhelloworld.knotlog.cucumber.BaseCucumberSteps#tearDown()
     */
    @Override
    @After
    public void tearDown() {
        tearDownTestContext();
    }

    @Then("^I see this message: \"([^\"]*)\"$")
    public void iSeeThisMessage(String messageDescription) throws Throwable {
        VesselMessages messages = getVesselMessages();
        for (VesselMessage message : messages) {
            if (message.getDisplayMessage().equals(messageDescription)) {
                return;
            }
        }
        fail(String.format("Could not find message: %s", messageDescription));
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
        Assert.assertFalse(getVesselMessages().isEmpty());
    }

    @Then("^there are no unrecognized messages$")
    public void thereAreNoUnrecognizedMessages() throws Throwable {
        Assert.assertNull(getUnrecognizedMessage());
    }

    @Then("^the sentence is not recognized$")
    public void aMessageIsReturnThatIndicatesThatSentenceWasNotRecognized() throws Throwable {
        assertMessageFailure(MessageFailure.UNRECOGNIZED_SENTENCE);
    }

    @Then("^the sentence is malformed$")
    public void aMessageIsReturnThatIndicatesThatSentenceWasMalformed() throws Throwable {
        assertMessageFailure(MessageFailure.MALFORMED_SENTENCE);
    }

}
