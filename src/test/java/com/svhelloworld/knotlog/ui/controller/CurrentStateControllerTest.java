package com.svhelloworld.knotlog.ui.controller;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.EventBus;
import com.svhelloworld.knotlog.events.LoggingEventBusExceptionHandler;
import com.svhelloworld.knotlog.measure.DistanceUnit;
import com.svhelloworld.knotlog.measure.LatitudinalHemisphere;
import com.svhelloworld.knotlog.measure.LongitudinalHemisphere;
import com.svhelloworld.knotlog.measure.MeasurementBasis;
import com.svhelloworld.knotlog.measure.SpeedUnit;
import com.svhelloworld.knotlog.messages.GPSPosition;
import com.svhelloworld.knotlog.messages.VesselMessageSource;
import com.svhelloworld.knotlog.messages.WaterDepth;
import com.svhelloworld.knotlog.messages.WindSpeed;

/**
 * Tests the {@link CurrentStateController} class.
 */
public class CurrentStateControllerTest {

    private EventBus eventBus;

    private CurrentStateController controller;

    private Locale defaultLocale;

    @Before
    public void setup() {
        eventBus = new EventBus(new LoggingEventBusExceptionHandler());
        controller = new CurrentStateController(eventBus);
        defaultLocale = Locale.getDefault();
    }

    @After
    public void tearDown() {
        Locale.setDefault(defaultLocale);
    }

    @Test
    public void testWaterDepthLabel() {
        assertEquals("water depth", controller.getWaterDepthLabel());
    }

    @Test
    public void testWaterDepth() {
        WaterDepth depth = new WaterDepth(VesselMessageSource.NMEA0183, Instant.now(), 99, DistanceUnit.FEET);
        eventBus.post(depth);
        assertEquals("99.0 feet", controller.getWaterDepth());
    }

    @Test
    public void testWindSpeedLabel() {
        assertEquals("wind speed", controller.getWindSpeedLabel());
    }

    @Test
    public void testWindSpeed() {
        WindSpeed speed = new WindSpeed(VesselMessageSource.NMEA0183, Instant.now(), 10, SpeedUnit.KNOTS,
                MeasurementBasis.RELATIVE);
        eventBus.post(speed);
        assertEquals("10.0 knots", controller.getWindSpeed());
    }

    @Test
    public void testPositionLabel() {
        assertEquals("GPS position", controller.getPositionLabel());
    }

    @Test
    public void testPosition() {
        GPSPosition pos = new GPSPosition(VesselMessageSource.NMEA0183, Instant.now(), "4322.5", LatitudinalHemisphere.NORTH,
                "12234.72", LongitudinalHemisphere.WEST);
        eventBus.post(pos);
        assertEquals("43°22.500'N 122°34.720'W", controller.getPosition());
    }

}
