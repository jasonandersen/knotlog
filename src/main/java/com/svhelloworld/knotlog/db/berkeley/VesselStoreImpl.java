package com.svhelloworld.knotlog.db.berkeley;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.Transaction;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;
import com.svhelloworld.knotlog.db.VesselStore;
import com.svhelloworld.knotlog.domain.Vessel;
import com.svhelloworld.knotlog.events.NewVessel;
import com.svhelloworld.knotlog.events.RequestAllVessels;

/**
 * Berkeley DB implementation of {@link VesselStore}.
 */
@Repository
public class VesselStoreImpl implements VesselStore {

    private static Logger log = LoggerFactory.getLogger(VesselStoreImpl.class);

    @Autowired
    private KnotlogEnvironment environment;

    @Autowired
    private EventBus eventBus;

    private EntityStore store;

    private PrimaryIndex<Integer, Vessel> primaryIndex;

    /**
     * @see com.svhelloworld.knotlog.db.VesselStore#save(com.svhelloworld.knotlog.domain.Vessel)
     */
    @Override
    public void save(Vessel vessel) {
        Validate.notNull(vessel);
        Transaction txn = environment.startTransaction();
        try {
            primaryIndex.put(vessel);
            txn.commit();
        } catch (DatabaseException e) {
            txn.abort();
            throw new RuntimeException(e);
        }
    }

    /**
     * @see com.svhelloworld.knotlog.db.VesselStore#read(java.lang.Integer)
     */
    @Override
    public Vessel read(Integer id) {
        return primaryIndex.get(id);
    }

    /**
     * Handle events that indicate a new vessel has been added.
     * @param event
     */
    @Subscribe
    public void handleNewVesselEvent(NewVessel event) {
        Validate.notNull(event);
        save(event.getVessel());
    }

    /**
     * Handle request for all vessels.
     * @param event
     */
    @Subscribe
    public void handleRequestAllVesselsEvent(RequestAllVessels event) {
        Validate.notNull(event);
        event.setResponse(readAllVessels());
    }

    /**
     * @see com.svhelloworld.knotlog.db.VesselStore#readAllVessels()
     */
    @Override
    public List<Vessel> readAllVessels() {
        List<Vessel> out = new ArrayList<Vessel>();
        for (Vessel vessel : primaryIndex.entities()) {
            out.add(vessel);
        }
        return out;
    }

    /**
     * @see com.svhelloworld.knotlog.service.InitializableService#isInitialized()
     */
    @Override
    public boolean isInitialized() {
        return store != null && primaryIndex != null;
    }

    /**
     * Initialize entity store and indices.
     */
    @Override
    public void initialize() {
        log.info("initializing");
        Environment dbEnv = environment.getEnvironment();
        StoreConfig config = new StoreConfig();
        config.setAllowCreate(true);
        config.setTransactional(true);

        log.debug("initializing EntityStore");
        store = new EntityStore(dbEnv, getClass().getSimpleName(), config);

        log.debug("initializing PrimaryIndex");
        primaryIndex = store.getPrimaryIndex(Integer.class, Vessel.class);

        log.debug("registering in event bus");
        eventBus.register(this);
    }

    @PreDestroy
    private void closeStore() {
        log.info("closing EntityStore");
        store.close();
    }

}
