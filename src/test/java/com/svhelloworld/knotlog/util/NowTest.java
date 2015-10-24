package com.svhelloworld.knotlog.util;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;

import com.svhelloworld.knotlog.util.Now.NowProvider;

/**
 * Test the {@link Now} methods.
 */
public class NowTest {

    private static final Logger log = Logger.getLogger(NowTest.class);

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

        log.info(dateTime);
        log.info(millis);
        log.info(dateTime.toEpochSecond() * 1000);

        assertEquals(millis, dateTime.toEpochSecond() * 1000, 1000);
    }

    @Test
    public void testTestNowProvider() {
        String testNow = "2011-05-19T10:15:30-08:00";
        NowProvider provider = new NowTestingProvider(testNow);
        Now.setNowProvider(provider);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        ZonedDateTime dateTime = Now.getZonedDateTimeUTC();

        assertEquals("2011-05-19T18:15:30Z", formatter.format(dateTime));
    }
}
