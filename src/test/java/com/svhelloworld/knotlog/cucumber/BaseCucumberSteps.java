package com.svhelloworld.knotlog.cucumber;

import org.springframework.test.context.ContextConfiguration;

/**
 * Base class that all cucumber step definition classes should extend.
 */
@ContextConfiguration(locations = { "classpath:features/cucumber.xml" })
public abstract class BaseCucumberSteps {

}
