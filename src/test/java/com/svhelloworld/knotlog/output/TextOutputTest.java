package com.svhelloworld.knotlog.output;

import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.svhelloworld.knotlog.engine.parse.NMEA0183SourceParser;
import com.svhelloworld.knotlog.engine.parse.Parser;
import com.svhelloworld.knotlog.engine.sources.ClassPathFileSource;
import com.svhelloworld.knotlog.engine.sources.StreamedSource;

/**
 * Unit test for <tt>TextOutput</tt> class.
 * 
 * @author Jason Andersen
 * @since Mar 16, 2010
 *
 */
@Ignore //FIXME marking this as @Ignore because it takes a really long time to run
public class TextOutputTest {

    private final static String INPUT_PATH = "com/svhelloworld/knotlog/engine/parse/GarminDiagFeed.csv";

    private Parser parser;

    private TextOutput output;

    private OutputProtocol protocol;

    private StreamedSource source;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        source = new ClassPathFileSource(INPUT_PATH);
        parser = new NMEA0183SourceParser();
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
    public void testMessageOutput() {
        //test default setup
        parser.run();
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.output.TextOutput#vesselMessagesFound(com.svhelloworld.knotlog.messages.VesselMessage[])}.
     */
    @Test
    public void testUnrecognizedMessageOutput() {
        protocol = new PlainTextProtocol();
        parser = new NMEA0183SourceParser();
        parser.setSource(source);
        output = new TextOutput(Executors.newSingleThreadExecutor(), System.out, protocol);
        //setup listeners
        parser.addMessageListener(output);
        parser.addUnrecognizedMessageListener(output);
        parser.addPreparseListener(output);

        parser.run();
    }

}
