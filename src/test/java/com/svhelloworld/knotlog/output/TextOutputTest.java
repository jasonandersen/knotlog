package com.svhelloworld.knotlog.output;

import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import com.svhelloworld.knotlog.engine.parse.NMEA0183SourceParser;
import com.svhelloworld.knotlog.engine.sources.ClassPathFileSource;
import com.svhelloworld.knotlog.engine.sources.StreamedSource;
import com.svhelloworld.knotlog.test.BaseIntegrationTest;

/**
 * Unit test for <tt>TextOutput</tt> class.
 * 
 * @author Jason Andersen
 * @since Mar 16, 2010
 *
 */
public class TextOutputTest extends BaseIntegrationTest {

    private final static String INPUT_PATH = "com/svhelloworld/knotlog/engine/parse/GarminDiagFeed.csv";

    @Autowired
    private NMEA0183SourceParser parser;

    private TextOutput output;

    private OutputProtocol protocol;

    private StreamedSource source;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        source = new ClassPathFileSource(INPUT_PATH);
        parser.setSource(source);
        output = new ConsoleOutput(Executors.newSingleThreadExecutor());
        //setup listeners
        parser.addMessageListener(output);
        parser.addUnrecognizedMessageListener(output);
        parser.addPreparseListener(output);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.output.TextOutput#vesselMessagesFound(com.svhelloworld.knotlog.messages.VesselMessage[])}.
     */
    @Test
    @DirtiesContext
    public void testMessageOutput() {
        //test default setup
        parser.run();
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.output.TextOutput#vesselMessagesFound(com.svhelloworld.knotlog.messages.VesselMessage[])}.
     */
    @Test
    @DirtiesContext
    public void testUnrecognizedMessageOutput() {
        protocol = new PlainTextProtocol();
        output = new TextOutput(Executors.newSingleThreadExecutor(), System.out, protocol);
        parser.run();
    }

}
