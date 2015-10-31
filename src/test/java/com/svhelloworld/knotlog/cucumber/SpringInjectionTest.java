package com.svhelloworld.knotlog.cucumber;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.svhelloworld.knotlog.service.NMEA0183ParseService;

/**
 * Testing that we have Spring dependency injection hooked up correctly.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class SpringInjectionTest {

    @Autowired
    private NMEA0183ParseService parser;

    @Test
    public void testParserInjected() {
        assertNotNull(parser);
    }
}
