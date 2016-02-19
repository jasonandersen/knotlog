package com.svhelloworld.knotlog.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import com.svhelloworld.knotlog.measure.DistanceUnit;
import com.svhelloworld.knotlog.test.BaseIntegrationTest;

/**
 * Tests that the {@link ConversionService} is setup with default converters properly.
 */
public class ConversionServiceTest extends BaseIntegrationTest {

    @Autowired
    private ConversionService service;

    @Test
    public void testDI() {
        assertNotNull(service);
    }

    @Test
    public void testStringToInteger() {
        assertEquals(1, service.convert("1", Integer.class));
    }

    @Test
    public void testStringToDouble() {
        assertEquals(3.14159, service.convert("3.14159", Double.class), 0.00001);
    }

    @Test
    public void testStringFeetToFeet() {
        assertEquals(DistanceUnit.FEET, service.convert("feet", DistanceUnit.class));
    }

    @Test
    public void testStringMetersToMeters() {
        assertEquals(DistanceUnit.METERS, service.convert("meters", DistanceUnit.class));
    }

}
