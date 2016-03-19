package com.svhelloworld.knotlog.ui.currentstate;

import static org.junit.Assert.assertEquals;

import java.time.Instant;

import org.junit.Test;

import com.svhelloworld.knotlog.domain.messages.VesselMessageSource;
import com.svhelloworld.knotlog.domain.messages.WaterDepth;
import com.svhelloworld.knotlog.measure.DistanceUnit;
import com.svhelloworld.knotlog.ui.views.WaterDepthView;

/**
 * Testing the {@link WaterDepthView} class.
 */
public class WaterDepthViewTest {

    private WaterDepth depth;

    private WaterDepthView view;

    @Test
    public void testValue() {
        depth = new WaterDepth(VesselMessageSource.NMEA0183, Instant.now(), 99, DistanceUnit.FEET);
        view = new WaterDepthView(depth);
        assertEquals("99.0 feet", view.getValue());
    }

    @Test
    public void testLabel() {
        depth = new WaterDepth(VesselMessageSource.NMEA0183, Instant.now(), 99, DistanceUnit.FEET);
        view = new WaterDepthView(depth);
        assertEquals("water depth", view.getLabel());
    }

}
