package com.svhelloworld.knotlog.db;

import com.sleepycat.je.DatabaseException;
import com.svhelloworld.knotlog.domain.Vessel;

/**
 * Reads and writes {@link Vessel} objects.
 */
public interface VesselStore {

    /**
     * Persists a vessel.
     * @param vessel
     */
    public void save(Vessel vessel);

    /**
     * Shutdown the entity store.
     * @throws DatabaseException 
     */
    public void close() throws DatabaseException;

}
