package com.svhelloworld.knotlog.engine.parse;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.svhelloworld.knotlog.engine.sources.ClassPathFileSource;
import com.svhelloworld.knotlog.engine.sources.StreamedSource;

/**
 * Unit test for <tt>GPXParser</tt> class.
 * 
 * @author Jason Andersen
 * @since Apr 11, 2010
 *
 */
@Ignore //FIXME not finished
public class GPXParserTest {

    private static final String GPX_PATH = "com/svhelloworld/knotlog/engine/parse/Tracks.gpx";

    private StreamedSource source;

    private GPXParser target;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setup() throws Exception {
        source = new ClassPathFileSource(GPX_PATH);
        target = new GPXParser();
        target.setSource(source);
    }

    /**
     * Test method for {@link com.svhelloworld.knotlog.engine.parse.GPXParser#parse()}.
     */
    @Test
    public void testParse() {
        target.parse();
    }

}
