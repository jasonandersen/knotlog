package com.svhelloworld.knotlog.ui.currentstate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.Instant;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import com.svhelloworld.knotlog.measure.DistanceUnit;
import com.svhelloworld.knotlog.measure.LatitudinalHemisphere;
import com.svhelloworld.knotlog.measure.LongitudinalHemisphere;
import com.svhelloworld.knotlog.measure.MeasurementBasis;
import com.svhelloworld.knotlog.measure.SpeedUnit;
import com.svhelloworld.knotlog.messages.Altitude;
import com.svhelloworld.knotlog.messages.GPSPosition;
import com.svhelloworld.knotlog.messages.SpeedRelativeToGround;
import com.svhelloworld.knotlog.messages.VesselMessage;
import com.svhelloworld.knotlog.messages.VesselMessageSource;
import com.svhelloworld.knotlog.messages.WaterDepth;
import com.svhelloworld.knotlog.messages.WindSpeed;
import com.svhelloworld.knotlog.test.BaseIntegrationTest;
import com.svhelloworld.knotlog.ui.view.DistanceView;
import com.svhelloworld.knotlog.ui.view.PositionView;
import com.svhelloworld.knotlog.ui.view.SpeedView;
import com.svhelloworld.knotlog.ui.view.VesselMessageView;
import com.svhelloworld.knotlog.ui.view.VesselMessageViewConverter;
import com.svhelloworld.knotlog.ui.view.WindSpeedView;

/**
 * Test the {@link VesselMessageViewConverter} class.
 */
public class VesselMessageViewConverterTest extends BaseIntegrationTest {

    private static final VesselMessageSource SOURCE = VesselMessageSource.NMEA0183;

    @Autowired
    private ConversionService conversionService;

    private VesselMessageView<?> view;

    @Test
    public void testDI() {
        assertNotNull(conversionService);
    }

    @Test
    public void testCanConvert() {
        assertTrue(conversionService.canConvert(VesselMessage.class, VesselMessageView.class));
    }

    @Test
    public void testConvertWaterDepth() {
        WaterDepth depth = new WaterDepth(SOURCE, Instant.now(), 99.0f, DistanceUnit.FATHOMS);
        convert(depth);
        assertEquals(depth, view.getVesselMessage());
    }

    @Test
    public void testConvertWindSpeed() {
        WindSpeed windSpeed = new WindSpeed(SOURCE, Instant.now(), 20.0f, SpeedUnit.KNOTS, MeasurementBasis.RELATIVE);
        convert(windSpeed);
        assertNotNull(view);
        assertTrue(view instanceof WindSpeedView);
        assertEquals("wind speed", view.getLabel());
        assertEquals("20.0 knots", view.getValue());
        assertEquals("NMEA0183", view.getSource());
    }

    @Test
    public void testPositionMessageView() {
        GPSPosition position = new GPSPosition(SOURCE, Instant.now(), "1122.33", LatitudinalHemisphere.NORTH, "04455.66",
                LongitudinalHemisphere.WEST);
        convert(position);
        assertTrue(view instanceof PositionView);
    }

    @Test
    public void testConvertSpeed() {
        SpeedRelativeToGround speed = new SpeedRelativeToGround(SOURCE, Instant.now(), 10.0f, SpeedUnit.KNOTS);
        convert(speed);
        assertTrue(view instanceof SpeedView);
    }

    @Test
    public void testConvertDistance() {
        Altitude altitude = new Altitude(SOURCE, Instant.now(), 999.9f, DistanceUnit.FEET);
        convert(altitude);
        assertTrue(view instanceof DistanceView);
    }

    private void convert(VesselMessage message) {
        view = conversionService.convert(message, VesselMessageView.class);
        assertNotNull(view);
        assertEquals(message, view.getVesselMessage());
    }

}
