package com.svhelloworld.knotlog.engine.parse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.svhelloworld.knotlog.engine.MessageStreamProcessor;
import com.svhelloworld.knotlog.engine.TimeStamper;
import com.svhelloworld.knotlog.engine.sources.StreamedSource;
import com.svhelloworld.knotlog.messages.VesselMessageSource;
import com.svhelloworld.knotlog.messages.PreparseMessage;
import com.svhelloworld.knotlog.messages.UnrecognizedMessage;
import com.svhelloworld.knotlog.messages.VesselMessage;

/**
 * Parses NMEA0183 sentences into vessel messages.
 * 
 * @author Jason Andersen
 * @since Feb 16, 2010
 *
 */
public class NMEA0183Parser extends BaseThreadedParser {
    
    /*
     * TODO create InstrumentMessageTimestamper class
     * runs in a seperate thread only on real time
     * message sources (need to add that to the source
     * hierarchy either as an interface type or an
     * isRealTime() method). 
     * The parser will hand off messages to the timestamper
     * along with a reference currentTimeMillis(). When 
     * a TimeZulu message comes through, calculate the
     * delta between currentTimeMillis() and the TimeZulu
     * message. Then apply that delta to incoming messages
     * and work it backwards to messages prior to the
     * TimeZulu message. Needs some work, have to sleep on it.
     */
    
    /**
     * Initializes any external message stream processors.
     */
    private static List<MessageStreamProcessor> initExternalProcessors() {
        List<MessageStreamProcessor> out = new ArrayList<MessageStreamProcessor>();
        //TODO - change this to be injected from Spring
        /*
         * add a TimeStamper to update vessel message timestamps to the correct
         * time UTC as time messages come through the message stream.
         */
        out.add(new TimeStamper());
        
        return out;
    }
    
    /**
     * Path to CSV message dictionary
     */
    private static final String DICTIONARY_PATH = 
        "com/svhelloworld/knotlog/engine/parse/NMEA0183MessageDictionary.csv";
    /**
     * Message source
     */
    private static final VesselMessageSource SOURCE = VesselMessageSource.NMEA0183;
    
    /**
     * Message dictionary defining what messages are associated 
     * with individual sentences.
     */
    private MessageDictionary dictionary;
    
    /**
     * Constructor.
     */
    public NMEA0183Parser() {
        super(initExternalProcessors());
    }
    
    /**
     * Parses an NMEA0183 data source.
     */
    @Override
    protected void parse() {
        initializeDictionary();
        BufferedReader reader = openSource();
        String line;
        
        try {
            while((line = reader.readLine()) != null) {
                parseLine(line);
            }
        } catch (IOException e) {
            throw new ParseException(e);
        } finally {
            closeSource(reader);
        }
    }
    
    /**
     * Processes a single line from an NMEA0183 data feed.
     * @param line a single line
     */
    private void parseLine(final String line) {
        //throw the original line to any preparse listeners
        preparse(line);
        
        //construct sentence
        NMEA0183Sentence sentence = new NMEA0183Sentence(line);
        if (sentence.isValid()) {
            List<VesselMessage> messages = interpretSentence(sentence);
            if (!messages.isEmpty()) {
                //allow external processes to clean up message stream
                externalProcessing(messages.toArray(new VesselMessage[0]));
            }
        } else if (hasUnrecognizedMessageListeners()){
            //throw unrecognized message
            Date timestamp = sentence.getTimestamp() > 0 ? 
                    new Date(sentence.getTimestamp()) : new Date();
            MessageFailure failureMode = MessageFailure.MALFORMED_SENTENCE;
            List<String> fields = sentence.getFields();
            UnrecognizedMessage message = InstrumentMessageFactory.createUnrecognizedMessage(
                    SOURCE, timestamp, failureMode, fields, sentence.getOriginalSentence());
            throwUnrecognizedMessageEvent(message);
        }
    }
    
    /**
     * Interprets a single NMEA0183 sentence into vessel messages.
     * @param sentence NMEA0183 sentence
     * @return a list of vessel messages interpreted from the sentence.
     *          Can return an empty list if no vessel messages can be
     *          interpreted from this sentence. Will never return null.
     */
    private List<VesselMessage> interpretSentence(final NMEA0183Sentence sentence) {
        assert sentence != null;
        assert sentence.isValid();
        assert dictionary != null;
         
        List<VesselMessage> messages = new ArrayList<VesselMessage>();
        String tag = sentence.getTag();
        List<String> fields = sentence.getFields();
        List<InstrumentMessageDefinition> definitions = dictionary.getDefinitions(tag);
        if (!definitions.isEmpty()) {
            //create vessel messages for the definitions found
            for (InstrumentMessageDefinition definition : definitions) {
                Date timestamp = sentence.getTimestamp() >= 0 ? 
                        new Date(sentence.getTimestamp()) : new Date();
                VesselMessage message = InstrumentMessageFactory.createInstrumentMessage(
                        SOURCE, timestamp, definition, fields);
                //check if the message returned is an unrecognized message
                if (message instanceof UnrecognizedMessage) {
                    throwUnrecognizedMessageEvent((UnrecognizedMessage)message);
                } else {
                    messages.add(message);
                }
            }
        } else if (hasUnrecognizedMessageListeners()) {
            /*
             * sentence tag has not been defined so create an unrecognized message
             * event and return an empty message list
             */
            Date timestamp = sentence.getTimestamp() > 0 ? 
                    new Date(sentence.getTimestamp()) : new Date();
            MessageFailure failureMode = MessageFailure.UNRECOGNIZED_SENTENCE;
            String identifier = sentence.getTalkerId() + sentence.getTag();
            UnrecognizedMessage message = InstrumentMessageFactory.createUnrecognizedMessage(
                    SOURCE, timestamp, failureMode, identifier, fields, 
                    sentence.getOriginalSentence());
            throwUnrecognizedMessageEvent(message);
        }
        return messages;
    }
    
    /**
     * if there are any preparse listeners, throw a preparse event to them
     * @param line
     */
    private void preparse(String line) {
        if (!hasPreparseListeners()) {
            return;
        }
        PreparseMessage message = new PreparseMessage(SOURCE, new Date(), line);
        throwPreparseEvent(message);
    }
    
    /**
     * @return a BufferedReader object from the source.
     * @throws NullPointerException if the underlying source is null.
     */
    private BufferedReader openSource() {
        StreamedSource source = getSource();
        if (source == null) {
            throw new NullPointerException("source cannot be null");
        }
        InputStream stream = source.open();
        InputStreamReader isr = new InputStreamReader(stream);
        return new BufferedReader(isr);
    }
    
    /**
     * Close source stream
     * @param stream
     */
    private void closeSource(BufferedReader stream) {
        try {
            stream.close();
        } catch (IOException e) {
            //ignore
        }
    }
    
    /**
     * Initializes NMEA 0183 message dictionary 
     */
    private void initializeDictionary() {
        dictionary = new CSVMessageDictionary();
        dictionary.initialize(DICTIONARY_PATH);
    }

    /**
     * @see com.svhelloworld.knotlog.engine.parse.BaseThreadedParser#externalProcessingComplete(com.svhelloworld.knotlog.messages.VesselMessage[])
     */
    @Override
    protected void externalProcessingComplete(VesselMessage... messages) {
        throwMessageEvent(messages);
    }
}
