package com.svhelloworld.knotlog.cucumber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.svhelloworld.knotlog.LocalFiles;
import com.svhelloworld.knotlog.preferences.Preferences;
import com.svhelloworld.knotlog.service.impl.PreferencesHashMapImpl;
import com.svhelloworld.knotlog.util.TestUtils;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

/**
 * Cucumber steps to support tests on directories.
 */
public class DirectoriesSteps {

    private static Logger log = LoggerFactory.getLogger(DirectoriesSteps.class);

    /*
     * Storing test targets as member variables of the class rather than in the test
     * context like the rest of the test data. These tests require a specific configuration
     * that was really hard to inject into the Spring application context lifecycle so
     * instead of autowiring managed beans, we're going to create them from scratch in here.
     * 
     * The downside is that these steps won't work when mixed with steps from another step
     * class. That seems like that might be a bad choice so I may have to reconsider.
     */

    private LocalFiles localFiles;

    private PreferencesHashMapImpl preferences;

    private File tempDirectory;

    @Before
    public void setup() throws IOException {
        preferences = new PreferencesHashMapImpl();
        localFiles = new LocalFiles(preferences);
        localFiles.initialize();
        tempDirectory = TestUtils.buildTestDirectory(this.getClass());
        log.debug("using temporary directory {}", tempDirectory.getAbsolutePath());
    }

    /*
     * GIVEN
     */

    @Given("^this directory exists: \"([^\"]*)\"$")
    public void thisDirectoryExists(String path) throws IOException {
        createThisDirectory(path);
    }

    @Given("^my preferences have this directory \"([^\"]*)\" as my Knotlog directory$")
    public void myPreferencesHaveThisDirectoryAsMyKnotlogDirectory(String path) throws IOException {
        putPathInPreferences(path, Preferences.KEY_ROOT_DIRECTORY);
    }

    @Given("^my preferences have this directory \"([^\"]*)\" as my data directory$")
    public void myPreferencesHaveThisDirectoryAsMyDataDirectory(String path) throws IOException {
        putPathInPreferences(path, Preferences.KEY_DATA_DIRECTORY);
    }

    @Given("^my preferences have no entry for my .+ directory$")
    public void myPreferencesHaveNoEntryForMyDirectory() {
        preferences.clearAllPreferences();
    }

    /*
     * THEN
     */

    @Then("^my Knotlog directory is \"([^\"]*)\"$")
    public void myKnotlogDirectoryIs(String path) {
        assertKnotlogDirectory(path);
    }

    @Then("^my data directory is \"([^\"]*)\"$")
    public void thenMyDatabaseDirectoryIs(String path) {
        assertDataDirectory(path);
    }

    @Then("my Knotlog directory is a directory called \"knotlog\" in my user home directory")
    public void myKnotlogDirectoryIsADirectoryCalledKnotlogInMyUserHomeDirectory() {
        assertKnotlogHomeDirectory();
    }

    /*
     * Helper methods
     */

    /**
     * Creates the directory off the temp directory
     * @param path
     * @throws IOException 
     */
    private void createThisDirectory(String path) throws IOException {
        String normalizedPath = normalizePath(path);
        File newDirectory = new File(normalizedPath);
        FileUtils.forceMkdir(newDirectory);
        assertTrue(newDirectory.exists());
        assertTrue(newDirectory.isDirectory());
    }

    /**
     * Alters the path to be in the temp directory and then stores the altered path in preferences
     * @param path
     * @param preferencesKey
     * @throws IOException 
     */
    private void putPathInPreferences(String path, String preferencesKey) throws IOException {
        preferences.put(preferencesKey, normalizePath(path));
        localFiles.initialize();
    }

    /**
     * @param path
     */
    private void assertDataDirectory(String path) {
        String normalizedPath = normalizePath(path);
        String dataDirectory = localFiles.getDataDirectory().getAbsolutePath();
        assertEquals(normalizedPath, dataDirectory);
    }

    /**
     * @param path
     */
    private void assertKnotlogDirectory(String path) {
        String normalizedPath = normalizePath(path);
        String knotlogDirectory = localFiles.getRootDirectory().getAbsolutePath();
        assertEquals(normalizedPath, knotlogDirectory);
    }

    /**
     * @param path
     * @return a normalized path that will hang off the temporary directory
     */
    private String normalizePath(String path) {
        log.debug("normalizing {}", path);
        StringBuilder builder = new StringBuilder(tempDirectory.getAbsolutePath());
        if (builder.toString().endsWith(File.separator)) {
            builder.deleteCharAt(builder.length() - 1);
        }
        if (!path.startsWith(File.separator)) {
            builder.append(File.separator);
        }
        builder.append(path);
        String out = builder.toString();
        log.debug("path normalized {}", out);
        return out;
    }

    /**
     * Assert that the Knotlog directory is off the user's home directory
     */
    private void assertKnotlogHomeDirectory() {
        String userDirectory = FileUtils.getUserDirectoryPath();
        String expectedDirectory = userDirectory + "/knotlog";
        assertEquals(expectedDirectory, localFiles.getRootDirectory().getAbsolutePath());
    }

}
