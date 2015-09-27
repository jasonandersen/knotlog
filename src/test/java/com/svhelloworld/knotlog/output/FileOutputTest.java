package com.svhelloworld.knotlog.output;

import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Test;

import com.svhelloworld.knotlog.engine.parse.NMEA0183Parser;
import com.svhelloworld.knotlog.engine.parse.Parser;
import com.svhelloworld.knotlog.engine.sources.ClassPathFileSource;
import com.svhelloworld.knotlog.engine.sources.StreamedSource;

/**
 * Unit test for <tt>FileOutput</tt> class.
 * 
 * @author Jason Andersen
 * @since Apr 30, 2010
 *
 */
public class FileOutputTest {
    
    private final static String OUTPUT_PATH = 
        "c:\\dev\\projects\\knotlog\\src\\test\\com\\svhelloworld\\knotlog\\output\\FileOutputResult.txt";
    
    private final static String INPUT_PATH = 
        "com/svhelloworld/knotlog/engine/parsers/nmea0183/GarminDiagFeed.csv";
    
    private Parser parser;
    
    private FileOutput output;
    
    private OutputProtocol protocol;
    
    private StreamedSource source;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        source = new ClassPathFileSource(INPUT_PATH);
        parser = new NMEA0183Parser();
        parser.setSource(source);
        protocol = new PlainTextProtocol();
        output = new FileOutput(Executors.newSingleThreadExecutor(), OUTPUT_PATH, protocol);
        parser.addPreparseListener(output);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.output.FileOutput#vesselMessagesFound(com.svhelloworld.knotlog.messages.VesselMessage[])}.
     */
    @Test
    public void testMessageOutput() {
        //test default setup
        parser.run();
    }


}
