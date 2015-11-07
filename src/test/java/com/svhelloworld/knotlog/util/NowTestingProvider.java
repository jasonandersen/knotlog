package com.svhelloworld.knotlog.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.svhelloworld.knotlog.util.Now.NowProvider;

/**
 * Gives tests the ability to return a specific date/time to control
 * test behavior. 
 */
public class NowTestingProvider implements NowProvider {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    private static final Logger log = LoggerFactory.getLogger(NowTestingProvider.class);

    private final ZonedDateTime dateTime;

    /**
     * Constructor
     * @param newDateTime - the date/time to return as now, in ISO_OFFSET_DATE_TIME format
     */
    public NowTestingProvider(String newDateTime) {
        log.info("Creating new NowProvider from {}", newDateTime);
        TemporalAccessor parsed = FORMAT.parse(newDateTime);
        dateTime = ZonedDateTime.from(parsed);
        log.debug("dateTime {}", dateTime);
    }

    /**
     * @see com.svhelloworld.knotlog.util.Now.NowProvider#getInstant()
     */
    @Override
    public Instant getInstant() {
        return dateTime.toInstant();
    }

    /**
     * @see com.svhelloworld.knotlog.util.Now.NowProvider#getZonedDateTime()
     */
    @Override
    public ZonedDateTime getZonedDateTime() {
        return dateTime;
    }

    /**
     * @see com.svhelloworld.knotlog.util.Now.NowProvider#getZonedDateTimeUTC()
     */
    @Override
    public ZonedDateTime getZonedDateTimeUTC() {
        return dateTime.withZoneSameInstant(ZoneId.of("GMT"));
    }

}
