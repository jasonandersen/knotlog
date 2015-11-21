package com.svhelloworld.knotlog.db;

import java.util.List;

import com.svhelloworld.knotlog.domain.Vessel;
import com.svhelloworld.knotlog.service.InitializableService;

/**
 * Reads and writes {@link Vessel} objects.
 */
public interface VesselStore extends InitializableService {

    /**
     * Persists a vessel.
     * @param vessel
     */
    public void save(Vessel vessel);

    /**
     * Retrieves a {@link Vessel} based on the vessel's ID.
     * @param key
     * @return
     */
    public Vessel read(Integer key);

    /**
     * @return a list of all vessels, never returns null but can return empty list
     */
    public List<Vessel> readAllVessels();

}
