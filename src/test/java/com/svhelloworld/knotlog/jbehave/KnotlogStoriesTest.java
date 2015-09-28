package com.svhelloworld.knotlog.jbehave;

import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.TXT;

import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

/**
 * Sets up the JBehave environment in order to run acceptance tests.
 */
public class KnotlogStoriesTest extends JUnitStories {

    /**
     * @see org.jbehave.core.ConfigurableEmbedder#configuration()
     */
    @Override
    public Configuration configuration() {
        StoryReporterBuilder reporterBuilder = new StoryReporterBuilder();
        reporterBuilder.withDefaultFormats().withFormats(CONSOLE, TXT);
        Configuration configuration = super.hasConfiguration() ? super.configuration() : new MostUsefulConfiguration();
        configuration.useStoryReporterBuilder(reporterBuilder);
        return configuration;
    }

    /**
     * @see org.jbehave.core.ConfigurableEmbedder#stepsFactory()
     */
    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), new NMEA0183ParseSteps());
    }

    @Override
    protected List<String> storyPaths() {
        return new StoryFinder().findPaths(CodeLocations.codeLocationFromPath("src/test/resources/bdd"),
                "**/*.story", "");
    }
}
