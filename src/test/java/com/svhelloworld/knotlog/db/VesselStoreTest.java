package com.svhelloworld.knotlog.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.eventbus.EventBus;
import com.sleepycat.je.DatabaseException;
import com.svhelloworld.knotlog.domain.Vessel;
import com.svhelloworld.knotlog.domain.VesselType;
import com.svhelloworld.knotlog.events.NewVessel;
import com.svhelloworld.knotlog.events.RequestAllVessels;
import com.svhelloworld.knotlog.test.BaseIntegrationTest;

/**
 * Test the {@link VesselStore} implementation.
 */
@Ignore
public class VesselStoreTest extends BaseIntegrationTest {

    @Autowired
    private EventBus eventBus;

    @Autowired
    private VesselStore store;

    private Vessel vessel;

    @Before
    public void setup() throws DatabaseException {
        vessel = new Vessel(1, "s/v hello world", VesselType.SAILBOAT);
        store.save(vessel);
    }

    @Test
    public void testRead() throws DatabaseException {
        Vessel storedVessel = store.read(1);
        assertNotNull(storedVessel);
        assertEquals(1, storedVessel.getId(), 0);
        assertEquals("s/v hello world", storedVessel.getName());
    }

    @Test
    public void testUpdate() {
        Vessel original = store.read(1);
        assertEquals("s/v hello world", vessel.getName());
        original.setName("s/v Monkey Nutz");
        store.save(original);

        Vessel updated = store.read(1);
        assertEquals("s/v Monkey Nutz", updated.getName());
    }

    @Test
    public void testVesselType() {
        Vessel original = store.read(1);
        assertEquals(VesselType.SAILBOAT, original.getType());
    }

    @Test
    public void testEventBusRegistry() {
        //make sure vessel doesn't exist prior to test
        assertNull(store.read(2));

        //post event to bus
        Vessel pisces = new Vessel(2, "s/v Pisces", VesselType.SAILBOAT);
        NewVessel event = new NewVessel(pisces);
        eventBus.post(event);

        //ensure event was picked up and vessel was stored
        Vessel pisces2 = store.read(2);
        assertEquals(pisces.getName(), pisces2.getName());
        assertEquals(pisces.getType(), pisces2.getType());

    }

    @Test
    public void testRequestAllVessels() {

        store.save(new Vessel(1, "s/v hello world", VesselType.SAILBOAT));
        store.save(new Vessel(2, "2005 BMW R1200GS", VesselType.MOTORCYCLE));
        store.save(new Vessel(3, "2000 Ford F-250", VesselType.AUTOMOBILE));

        RequestAllVessels request = new RequestAllVessels();
        eventBus.post(request);

        assertTrue(request.hasResponse());
        List<Vessel> vessels = request.getResponse();
        assertNotNull(vessels);
        assertEquals(3, vessels.size());
    }

}
