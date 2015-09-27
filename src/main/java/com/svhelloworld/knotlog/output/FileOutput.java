package com.svhelloworld.knotlog.output;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Executor;

/**
 * Sends text output to a local file.
 * 
 * @author Jason Andersen
 * @since Mar 16, 2010
 *
 */
public class FileOutput extends TextOutput {
    
    /**
     * File to write to
     */
    private File file;
    
    /**
     * Constructor.
     * @param threadPool thread pool
     * @param path path to output file
     * @param protocol protocol to format text output
     * @throws NullPointerException when path is null
     * @throws NullPointerException when protocol is null
     * @throws IllegalArgumentException when resulting file cannot 
     *          be written to
     */
    public FileOutput(
            Executor threadPool, 
            String path, 
            OutputProtocol protocol) {
        
        super(threadPool);
        //validate
        if (protocol == null) {
            throw new NullPointerException("protocol cannot be null");
        }
        if (path == null) {
            throw new NullPointerException("path cannot be null");
        }
        File file = new File(path);
        try {
            //make sure the file is valid and writeable
            if (!file.exists()) {
                file.createNewFile();
            }
            if (!file.canWrite()) {
                throw new IllegalArgumentException("file cannot be written to: " + path);
            }
            this.file = file;
            FileWriter writer = new FileWriter(file);
            super.writer = writer;
            super.protocol = protocol;
        } catch (IOException e) {
            throw new OutputException(e.getLocalizedMessage(), e);
        }
    }

    /**
     * @return file to output to
     */
    public File getFile() {
        return file;
    }
}
