package com.svhelloworld.knotlog.engine.parse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.svhelloworld.knotlog.util.IOUtil;

/**
 * Loads a message dictionary from a CSV file. The comma seperated
 * values should be in the following pattern:
 * 
 * <ol>
 * <li>Sentence identifier (NMEA0183 = 3 alphabetic characters, NMEA2000 = 6 digit numeric)</li>
 * <li>Message class - can be a fully qualified class name or a simple
 *    class name in which a default message package will be assumed.</li>
 * <li>The rest of the comma seperated values are parameters to be passed
 *    into the message class. Integer values are presumed to be sentence
 *    field indexes (1-based index, <em>NOT</em> zero-based index). Non-integer
 *    values will be passed into the message class without interpretation.</li>
 * </ol>
 * 
 * 
 * Example:
 * <code>DBT,WaterDepth,1,FEET</code>
 * 
 * The NMEA0183 sentence identifier is DBT. The message class being created
 * is WaterDepth. The first field value is passed to the message class. The
 * string "FEET" is also passed to the message class. "FEET" will be translated
 * into the DistanceUnit.FEET object by the message class.
 * 
 * Comments in the message dictionary file must start with //
 * 
 * @author Jason Andersen
 * @since Feb 17, 2010
 *
 */
public class CSVMessageDictionary implements MessageDictionary {
    /**
     * Array index for sentence identifiers
     */
    private static final int IDENT_IDX = 0;
    /**
     * Array index for message class
     */
    private static final int CLASS_IDX = 1;
    /**
     * Indicates a comment line within the message dictionary file
     */
    private static final String COMMENT_START = "//";
    /**
     * Message definitions
     */
    private List<InstrumentMessageDefinition> definitions;
    
    /**
     * @param identifier sentence identifier
     * @return all message definitions for a given sentence identifier.
     *          Will return an empty list if no definitions exist for an 
     *          identifier.
     * @see com.svhelloworld.knotlog.engine.parse.MessageDictionary#getDefinitions(java.lang.String)
     */
    @Override
    public List<InstrumentMessageDefinition> getDefinitions(String identifier) {
        List<InstrumentMessageDefinition> out = new ArrayList<InstrumentMessageDefinition>();
        for (InstrumentMessageDefinition definition : definitions) {
            if (definition.getIdentifier().equals(identifier)) {
                out.add(definition);
            }
        }
        return out;
    }

    /**
     * The first object in the parameters array should be a class path 
     * to a CSV file containing message definitions.
     * @param params initialization parameters
     * @throws MessageDictionaryException if the dictionary file is malformed or
     *          there is an exception reading from the CSV file
     * @see com.svhelloworld.knotlog.engine.parse.MessageDictionary#initialize(java.lang.Object[])
     */
    @Override
    public void initialize(Object... params) {
        definitions = new ArrayList<InstrumentMessageDefinition>();
        String path = params[0].toString();
        loadCsvFile(path);
    }
    
    /**
     * @return all definitions, initialize() must be called before
     *          this method.
     */
    public List<InstrumentMessageDefinition> getAllDefinitions() {
        return definitions;
    }
    
    /**
     * Loads file from class path.
     * @param path class path to CSV file
     * @throws MessageDictionaryException if the dictionary file is malformed
     */
    private void loadCsvFile(String path) {
        //eventually we should support file system files but right now, 
        //just class path files will do.
        try {
            InputStream is = IOUtil.loadResourceFromClassPath(path);
            readStream(is);
        } catch (IOException e) {
            throw new MessageDictionaryException("Read message dictionary failed: " + path, e);
        }
    }
    
    /**
     * Reads the CSV file from the specified path.
     * @param is InputStream object to read from
     * @throws IOException can occurs while reading the file from disk
     * @throws MessageDictionaryException if the dictionary file is malformed
     */
    private void readStream(InputStream is) throws IOException {
        assert is != null;
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(isr);
        
        //read through the stream and parse each line
        String line;
        InstrumentMessageDefinition definition;
        while((line = reader.readLine()) != null) {
            definition = parseLine(line);
            if (definition != null) {
                definitions.add(definition);
            }
        }
    }
    
    /**
     * Parses a single line in the file.
     * @param line 
     * @return an instrument message, can return null if the line contains no text.
     * @throws MessageDictionaryException if the line is malformed.
     */
    private InstrumentMessageDefinition parseLine(String line) {
        //validate message line
        if (line.length() == 0) {
            return null;
        }
        if (line.startsWith(COMMENT_START)) {
            return null;
        }
        String[] tokens = line.split(",");
        
        //ensure line contains at least identifier and class name
        if (tokens.length < 2) {
            throw new MessageDictionaryException("Malformed message definition: " + line);
        }
        
        //pull out identifier and class name
        String ident = tokens[IDENT_IDX];
        String className = tokens[CLASS_IDX];
        
        //pull out definition parameters
        List<String> parameters = new ArrayList<String>();
        for (int idx = CLASS_IDX + 1; idx < tokens.length; idx++) {
            parameters.add(tokens[idx]);
        }
        
        InstrumentMessageDefinition out = new InstrumentMessageDefinition(
                ident, className, parameters);
        return out;
    }
}
