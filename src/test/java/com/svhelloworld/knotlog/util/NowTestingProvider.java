package com.svhelloworld.knotlog.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import org.apache.log4j.Logger;

import com.svhelloworld.knotlog.util.Now.NowProvider;

/**
 * Gives tests the ability to return a specific date/time to control
 * test behavior. 
 */
public class NowTestingProvider implements NowProvider {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

    private static final Logger log = Logger.getLogger(NowTestingProvider.class);

    private final ZonedDateTime dateTime;

    /**
     * Constructor
     * @param newDateTime - the date/time to return as now, in ISO_OFFSET_DATE_TIME format
     */
    public NowTestingProvider(String newDateTime) {
        log.debug(String.format("Creating new NowProvider from %s", newDateTime));
        TemporalAccessor parsed = FORMAT.parse(newDateTime);
        dateTime = ZonedDateTime.from(parsed);
        log.debug(dateTime);
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
