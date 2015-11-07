package com.svhelloworld.knotlog.util;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.svhelloworld.knotlog.util.Now.NowProvider;

/**
 * Test the {@link Now} methods.
 */
public class NowTest {

    private static final Logger log = LoggerFactory.getLogger(NowTest.class);

    @After
    public void resetNow() {
        Now.resetNowProvider();
    }

    @Test
    public void testCurrentTimeMillisVsInstant() {
        long millis = System.currentTimeMillis();
        Instant instant = Now.getInstant();
        assertEquals(millis, instant.toEpochMilli(), 10);
    }

    @Test
    public void testCurrentTimeMillisVsZonedDateTime() {
        long millis = System.currentTimeMillis();
        ZonedDateTime dateTime = Now.getZonedDateTime();

        log.info("dateTime {}", dateTime);
        log.info("millis {}", millis);
        log.info("millis since epoch {}", dateTime.toEpochSecond() * 1000);

        assertEquals(millis, dateTime.toEpochSecond() * 1000, 1000);
    }

    @Test
    public void testTestNowProvider() {
        String testNow = "2011-05-19T10:15:30-08:00";
        NowProvider provider = new NowTestingProvider(testNow);
        Now.setNowProvider(provider);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        ZonedDateTime dateTime = Now.getZonedDateTimeGMT();

        assertEquals("2011-05-19T18:15:30Z", formatter.format(dateTime));
    }
}
