package com.svhelloworld.knotlog.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Provides the current date/time in different formats.
 */
public class Now {

    /**
     * Provides current date and time. Can be swapped out for a different provider
     * for use in testing.
     */
    protected static interface NowProvider {

        /**
         * @return the current time as an {@link Instant}
         */
        abstract Instant getInstant();

        /**
         * @return the current time as {@link ZonedDateTime} using the default time zone
         */
        abstract ZonedDateTime getZonedDateTime();

        /**
         * @return the current time as {@link ZonedDateTime} using GMT
         */
        abstract ZonedDateTime getZonedDateTimeUTC();
    }

    /**
     * Standard provider that gives us actual current date time values.
     */
    private static class InternalNowProvider implements NowProvider {

        @Override
        public Instant getInstant() {
            return Instant.now();
        }

        @Override
        public ZonedDateTime getZonedDateTime() {
            return ZonedDateTime.now(ZoneId.systemDefault());
        }

        @Override
        public ZonedDateTime getZonedDateTimeUTC() {
            return ZonedDateTime.now(ZoneId.of("GMT"));
        }

    }

    /**
     * Initialize now provider
     */
    static {
        provider = new InternalNowProvider();
    }

    /**
     * Singleton instance.
     */
    private static NowProvider provider;

    /**
     * @return the current time as an {@link Instant}
     */
    public static Instant getInstant() {
        return provider.getInstant();
    }

    /**
     * @return the current time as {@link ZonedDateTime} using the default time zone
     */
    public static ZonedDateTime getZonedDateTime() {
        return provider.getZonedDateTime();
    }

    /**
     * @return the current time as {@link ZonedDateTime} using GMT
     */
    public static ZonedDateTime getZonedDateTimeUTC() {
        return provider.getZonedDateTimeUTC();
    }

    /**
     * Other classes can set a new {@link NowProvider}. This gives tests the ability to change
     * what time is considered to be "now".
     * @param nowProvider
     */
    protected static void setNowProvider(NowProvider nowProvider) {
        provider = nowProvider;
    }

    /**
     * If the {@link NowProvider} has been changed, this method will change it back to the original
     * {@link InternalNowProvider}.
     */
    protected static void resetNowProvider() {
        provider = new InternalNowProvider();
    }

    private Now() {
        /*
         * private constructor - no instantiation for you
         */
    }
}
