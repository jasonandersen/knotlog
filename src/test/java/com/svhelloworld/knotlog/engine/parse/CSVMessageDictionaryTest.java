package com.svhelloworld.knotlog.engine.parse;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.svhelloworld.knotlog.engine.parse.CSVMessageDictionary;
import com.svhelloworld.knotlog.engine.parse.InstrumentMessageDefinition;
import com.svhelloworld.knotlog.engine.parse.MessageDictionaryException;

/**
 * Unit test for CSVMessageDictionary.
 * 
 * @author Jason Andersen
 * @since Feb 17, 2010
 *
 */
public class CSVMessageDictionaryTest {
    
    private static final String TEST_CLASS_PATH = "com/svhelloworld/knotlog/engine/parsers/TestMessageDictionary.csv";
    
    private static final String PROD_CLASS_PATH = "com/svhelloworld/knotlog/engine/parsers/nmea0183/MessageDictionary.csv";
    
    private CSVMessageDictionary target;
    
    /**
     * setup
     */
    @Before
    public void startUp() {
        target = new CSVMessageDictionary();
    }
    
    /**
     * Ensure dictionary can be loaded from a file in the class path w/o exception.
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.CSVMessageDictionary#initialize(java.lang.Object[])}.
     * @throws MessageDictionaryException 
     */
    @Test
    public void testInitializeClassPath() throws MessageDictionaryException {
        target.initialize(TEST_CLASS_PATH);
    }
    
    /**
     * Ensure dictionary gets loaded and a single entry can be pulled out correctly.
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.CSVMessageDictionary#getDefinitions(java.lang.String)}.
     * @throws MessageDictionaryException 
     */
    @Test
    public void testGetDefinitionsSingleEntry() throws MessageDictionaryException {
        target.initialize(TEST_CLASS_PATH);
        List<InstrumentMessageDefinition> definitions = target.getDefinitions("DBT");
        InstrumentMessageDefinition definition = definitions.get(0);
        assertEquals("DBT", definition.getIdentifier());
        assertEquals("WaterDepth", definition.getMessageClassName());
        List<String> params = definition.getParameters();
        assertEquals(2, params.size());
        assertEquals("1", params.get(0));
        assertEquals("FEET", params.get(1));
    }

    
    /**
     * Ensure the production dictionary gets loaded correctly.
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.CSVMessageDictionary#getDefinitions(java.lang.String)}.
     * @throws MessageDictionaryException 
     */
    @Test
    public void testProductionDictionaryFile() throws MessageDictionaryException {
        testDictionaryFile(PROD_CLASS_PATH);
    }
    
    /**
     * Ensure the testing dictionary gets loaded correctly.
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.CSVMessageDictionary#getDefinitions(java.lang.String)}.
     * @throws MessageDictionaryException 
     */
    @Test
    public void testTestingDictionaryFile() throws MessageDictionaryException {
        testDictionaryFile(TEST_CLASS_PATH);
    }
    
    /**
     * Checks out a CSV dictionary file to make it sure it loads correctly.
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.CSVMessageDictionary#getDefinitions(java.lang.String)}.
     * @throws MessageDictionaryException 
     */
    private void testDictionaryFile(String path) throws MessageDictionaryException {
        target.initialize(path);
        List<InstrumentMessageDefinition> definitions = target.getAllDefinitions();
        assertTrue(definitions.size() > 0);
        for (InstrumentMessageDefinition definition : definitions) {
            assertNotNull(definition);
            assertNotNull(definition.getIdentifier());
            assertTrue(definition.getIdentifier().length() > 0);
            assertNotNull(definition.getMessageClassName());
            assertTrue(definition.getMessageClassName().length() > 0);
            assertNotNull(definition.getParameters());
        }
    }
    
    
    /**
     * Ensure multiple entries can be pulled out correctly
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.CSVMessageDictionary#getDefinitions(java.lang.String)}.
     * @throws MessageDictionaryException 
     */
    @Test
    public void testGetDefinitionsMultipleEntries() throws MessageDictionaryException {
        target.initialize(TEST_CLASS_PATH);
        List<InstrumentMessageDefinition> definitions = target.getDefinitions("RMC");
        assertEquals(5, definitions.size());
        
        InstrumentMessageDefinition definition;
        List<String> params;

        definition = definitions.get(0);
        assertEquals("RMC", definition.getIdentifier());
        assertEquals("TimeOfDayZulu", definition.getMessageClassName());
        params = definition.getParameters();
        assertEquals(1, params.size());
        assertEquals("1", params.get(0));
        
        definition = definitions.get(1);
        assertEquals("RMC", definition.getIdentifier());
        assertEquals("PositionMessage", definition.getMessageClassName());
        params = definition.getParameters();
        assertEquals(4, params.size());
        assertEquals("2", params.get(0));
        assertEquals("3", params.get(1));
        assertEquals("4", params.get(2));
        assertEquals("5", params.get(3));
        
        definition = definitions.get(2);
        assertEquals("RMC", definition.getIdentifier());
        assertEquals("SpeedRelativeToGround", definition.getMessageClassName());
        params = definition.getParameters();
        assertEquals(2, params.size());
        assertEquals("7", params.get(0));
        assertEquals("KNOTS", params.get(1));
        
        definition = definitions.get(3);
        assertEquals("RMC", definition.getIdentifier());
        assertEquals("DateZulu", definition.getMessageClassName());
        params = definition.getParameters();
        assertEquals(1, params.size());
        assertEquals("9", params.get(0));
        
        definition = definitions.get(4);
        assertEquals("RMC", definition.getIdentifier());
        assertEquals("MagneticVariation", definition.getMessageClassName());
        params = definition.getParameters();
        assertEquals(2, params.size());
        assertEquals("10", params.get(0));
        assertEquals("11", params.get(1));
        
    }
    
    /**
     * Ensure definitions with no parameters can be loaded correctly.
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.CSVMessageDictionary#getDefinitions(java.lang.String)}.
     * @throws MessageDictionaryException 
     */
    @Test
    public void testGetDefinitionsNoParameters() throws MessageDictionaryException {
        target.initialize(TEST_CLASS_PATH);
        List<InstrumentMessageDefinition> definitions = target.getDefinitions("XXX");
        InstrumentMessageDefinition definition = definitions.get(0);
        List<String> params = definition.getParameters();
        assertNotNull(params);
        assertEquals(0, params.size());
    }

    /**
     * Ensure definitions with many parameters can be loaded correctly.
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.CSVMessageDictionary#getDefinitions(java.lang.String)}.
     * @throws MessageDictionaryException 
     */
    @Test
    public void testGetDefinitionsManyParameters() throws MessageDictionaryException {
        target.initialize(TEST_CLASS_PATH);
        List<InstrumentMessageDefinition> definitions = target.getDefinitions("XXX");
        InstrumentMessageDefinition definition = definitions.get(1);
        List<String> params = definition.getParameters();
        assertEquals(13, params.size());
    }
    

}
