package com.svhelloworld.knotlog.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * Cucumber JUnit runner class
 */
/**
 * Cucumber JUnit runner class
 */
@RunWith(Cucumber.class)
@CucumberOptions(format = { "pretty", "html:target/cucumber" }, features = "classpath:GGASentence.feature")
public class CucumberJUnitRunnerTest {
    //noop - just need the runner to execute the Cucumber classes
}
