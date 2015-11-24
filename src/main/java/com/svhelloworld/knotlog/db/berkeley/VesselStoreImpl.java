package com.svhelloworld.knotlog.db.berkeley;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Transaction;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.PrimaryIndex;
import com.svhelloworld.knotlog.db.VesselStore;
import com.svhelloworld.knotlog.domain.Vessel;
import com.svhelloworld.knotlog.events.LogDatabaseStats;
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

    /**
     * @see com.svhelloworld.knotlog.db.VesselStore#save(com.svhelloworld.knotlog.domain.Vessel)
     */
    @Override
    public void save(Vessel vessel) {
        Validate.notNull(vessel);
        Transaction txn = environment.startTransaction();
        try {
            getPrimaryIndex().put(vessel);
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
        Vessel out = getPrimaryIndex().get(id);
        return out;
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
        EntityCursor<Vessel> cursor = getPrimaryIndex().entities();
        try {
            for (Vessel vessel = cursor.first(); vessel != null; vessel = cursor.next()) {
                out.add(vessel);
            }
        } finally {
            cursor.close();
        }
        return out;
    }

    /**
     * @return retrieves the primary index from the entity store
     */
    private PrimaryIndex<Integer, Vessel> getPrimaryIndex() {
        /*
         * Make sure we don't store a reference to the primary index but instead retrieve it
         * each time we need it. This allows us to close the store and invalidate primary indices
         * without impacting each individual data access object.
         */
        return environment.getPrimaryIndex(Integer.class, Vessel.class);
    }

    /**
     * Initialize entity indices.
     */
    @PostConstruct
    private void initialize() {
        log.info("initializing");
        log.debug("registering in event bus");
        eventBus.register(this);
    }

    /**
     * Log environment statistics.
     * @param string
     */
    @SuppressWarnings("unused")
    private void logStats(String msg) {
        /*
         * this seems like a handy little diagnostic tool so I'm leaving it in here even though
         * we're not actively calling it right now
         */
        StringBuilder builder = new StringBuilder("[");
        builder.append(getClass().getSimpleName()).append("] ");
        builder.append(msg);
        eventBus.post(new LogDatabaseStats(builder.toString()));
    }

}
