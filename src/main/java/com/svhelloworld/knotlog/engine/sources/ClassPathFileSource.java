package com.svhelloworld.knotlog.engine.sources;

import java.io.IOException;
import java.io.InputStream;

import com.svhelloworld.knotlog.util.IOUtil;

/**
 * Provides access to vessel message data stored in a file
 * in the classpath. Used primarily for testing.
 * 
 * @author Jason Andersen
 * @since Feb 23, 2010
 *
 */
public class ClassPathFileSource implements StreamedSource {
    
    /**
     * Class path of file to read from.
     */
    private String classPath;
    /**
     * Input stream from file in class path.
     */
    private InputStream stream;
    
    /**
     * Constructor.
     * @param classPath class path of file to read from.
     */
    public ClassPathFileSource(String classPath) {
        this.classPath = classPath;
    }
    
    /**
     * @see com.svhelloworld.knotlog.engine.sources.StreamedSource#open()
     */
    @Override
    public InputStream open() {
        try {
            stream = IOUtil.loadResourceFromClassPath(classPath);
            if (stream == null) {
                throw new SourceInputStreamInvalidException("input stream is null");
            }
            return stream;
        } catch (Exception e) {
            throw new SourceInputStreamInvalidException(e);
        }
    }

    /**
     * @see com.svhelloworld.knotlog.engine.sources.StreamedSource#close()
     */
    @Override
    public void close() {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                throw new SourceClosingException(e);
            }
        }
    }
    
}
