package com.svhelloworld.knotlog.engine.parse;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.svhelloworld.knotlog.engine.VesselMessageListener;
import com.svhelloworld.knotlog.engine.parse.NMEA0183SourceParser;
import com.svhelloworld.knotlog.engine.parse.Parser;
import com.svhelloworld.knotlog.engine.sources.PortConfig;
import com.svhelloworld.knotlog.engine.sources.SerialPortSource;
import com.svhelloworld.knotlog.engine.sources.StreamedSource;
import com.svhelloworld.knotlog.output.ConsoleOutputMessageListener;
import com.svhelloworld.knotlog.output.FileOutput;
import com.svhelloworld.knotlog.output.OutputProtocol;
import com.svhelloworld.knotlog.output.TimestampedPlainTextProtocol;

/**
 * Test harness to wire up serial port NMEA0183 parsing.
 * 
 * @author Jason Andersen
 * @since Apr 26, 2010
 *
 */
public class SerialPortParser {
    
    /**
     * file output path
     */
    private final static String OUTPUT_PATH = 
        "c:\\dev\\projects\\knotlog\\2010.05.22.txt";
    
    /**
     * Entry point
     * @param args
     */
    public static void main(String[] args) {
        //parser
        Executor threadPool = Executors.newFixedThreadPool(5);
        PortConfig config = PortConfig.getDefaultGpsConfig("COM6");
        StreamedSource source = new SerialPortSource(config);
        Parser parser = new NMEA0183SourceParser();
        parser.setSource(source);
        
        //file output
        OutputProtocol protocol = new TimestampedPlainTextProtocol();
        FileOutput fileOutput = new FileOutput(threadPool, OUTPUT_PATH, protocol);
        parser.addPreparseListener(fileOutput);
        
        //console output
        VesselMessageListener consoleOutput = new ConsoleOutputMessageListener(threadPool);
        parser.addMessageListener(consoleOutput);
        
        //pull the trigger
        threadPool.execute(parser);
    }
}
