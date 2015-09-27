package com.svhelloworld.knotlog.engine.sources;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

/**
 * Unit test for ClassPathFileSource class.
 * 
 * @author Jason Andersen
 * @since Feb 23, 2010
 *
 */
public class ClassPathFileSourceTest {
    
    private final static String VALID_PATH = 
        "com/svhelloworld/knotlog/engine/parsers/nmea0183/NMEA0183TestFeed.csv";
    
    private final static String INVALID_PATH = "com/svhelloworld/knotlog/DoesNotExist.txt";

    /**
     * Make sure it can load a valid class path.
     * Test method for {@link com.svhelloworld.knotlog.engine.sources.ClassPathFileSource#open()}.
     * @throws IOException 
     */
    @Test
    public void testGetInputStreamValid() throws IOException {
        InputStream is = null;
        try {
            ClassPathFileSource target = new ClassPathFileSource(VALID_PATH);
            is = target.open();
            assertNotNull(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
    
    /**
     * Ensure an invalid class path throws the right exception.
     * Test method for {@link com.svhelloworld.knotlog.engine.sources.ClassPathFileSource#open()}.
     * @throws IOException 
     */
    @Test
    public void testGetInputStreamInvalid() throws IOException {
        InputStream is = null;
        try {
            ClassPathFileSource target = new ClassPathFileSource(INVALID_PATH);
            is = target.open();
            fail("expected exception");
        } catch (SourceInputStreamInvalidException e) {
            //expected
        } catch (Exception e) {
            fail("expected SourceInputStreamInvalidException: " + e.getMessage());
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
    
    /**
     * Make sure we can close the source.
     * Test method for {@link com.svhelloworld.knotlog.engine.sources.ClassPathFileSource#close()}.
     * @throws IOException 
     */
    @Test
    public void testClose() throws IOException {
        InputStream is = null;
        try {
            ClassPathFileSource target = new ClassPathFileSource(VALID_PATH);
            is = target.open();
            target.close();
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
    
    /**
     * Make sure closing the source without calling open 
     * causes no problems.
     * Test method for {@link com.svhelloworld.knotlog.engine.sources.ClassPathFileSource#close()}.
     * @throws IOException 
     */
    @Test
    public void testCloseWithoutOpen() throws IOException {
        ClassPathFileSource target = new ClassPathFileSource(VALID_PATH);
        target.close();
    }
}
