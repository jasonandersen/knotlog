package com.svhelloworld.knotlog.service.impl;

import java.io.File;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * An implementation of {@link Preferences} that has some test-specific behavior around it.
 */
@Primary
@Service
public class PreferencesTestImpl extends PreferencesImpl {

    private File tempDirectory;

    @Override
    public File getKnotlogDirectory() {
        if (tempDirectory == null) {
            initTempDirectory();
        }
        return tempDirectory;
    }

    /**
     * Initialize temporary directory.
     */
    private void initTempDirectory() {
        /*
         * FIXME - hardcoding this for the time being but should change it to use a Java temp directory
         */
        tempDirectory = new File("/Users/jason/dev/workspace/knotlog/temp/");
    }
}
