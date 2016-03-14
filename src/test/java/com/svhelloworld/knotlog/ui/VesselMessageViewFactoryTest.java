package com.svhelloworld.knotlog.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.Instant;

import org.junit.Before;
import org.junit.Test;

import com.svhelloworld.knotlog.measure.DistanceUnit;
import com.svhelloworld.knotlog.measure.VesselArea;
import com.svhelloworld.knotlog.messages.RudderAngle;
import com.svhelloworld.knotlog.messages.VesselMessageSource;
import com.svhelloworld.knotlog.messages.WaterDepth;
import com.svhelloworld.knotlog.ui.view.WaterDepthView;

/**
 * Testing the {@link VesselMessageViewFactory} class.
 */
public class VesselMessageViewFactoryTest {

    private VesselMessageViewFactory factory;

    private VesselMessageSource source = VesselMessageSource.NMEA0183;

    private Instant now = Instant.now();

    @Before
    public void setup() {
        factory = new VesselMessageViewFactory();
    }

    @Test
    public void testWaterDepth() {
        WaterDepth depth = new WaterDepth(source, now, 99.0f, DistanceUnit.FEET);
        WaterDepthView view = factory.buildView(depth);
        assertEquals("99.0 feet", view.getValue());
    }

    @Test
    public void testRudderAngle() {
        RudderAngle angle = new RudderAngle(source, now, 12.0f, VesselArea.PORT);
        assertNull(factory.buildView(angle));
    }

}
