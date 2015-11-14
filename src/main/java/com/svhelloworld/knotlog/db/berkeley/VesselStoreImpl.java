package com.svhelloworld.knotlog.db.berkeley;

import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Repository;

import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.svhelloworld.knotlog.db.VesselStore;
import com.svhelloworld.knotlog.domain.Vessel;

/**
 * Berkeley DB implementation of {@link VesselStore}.
 */
@Repository
public class VesselStoreImpl implements VesselStore {

    private Environment environment;

    private EntityStore store;

    private PrimaryIndex<Integer, Vessel> vesselPrimaryIndex;

    public VesselStoreImpl() {
        /*
         * FIXME this needs to be refactored into something else not awful
         */
        //        try {
        //            //setup environment 
        //            EnvironmentConfig config = new EnvironmentConfig();
        //            config.setAllowCreate(true);
        //            environment = new Environment(new File("/Users/jason/dev/workspace/knotlog/temp/db"), config);
        //
        //            //setup store
        //            StoreConfig storeConfig = new StoreConfig();
        //            store = new EntityStore(environment, "VesselStore", storeConfig);
        //
        //            //setup primary index
        //            vesselPrimaryIndex = store.getPrimaryIndex(Integer.class, Vessel.class);
        //        } catch (DatabaseException e) {
        //            throw new RuntimeException(e);
        //        }

    }

    /**
     * @see com.svhelloworld.knotlog.db.VesselStore#save(com.svhelloworld.knotlog.domain.Vessel)
     */
    @Override
    public void save(Vessel vessel) {
        Validate.notNull(vessel);
        try {
            vesselPrimaryIndex.put(vessel);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @see com.svhelloworld.knotlog.db.VesselStore#close()
     */
    @Override
    public void close() throws DatabaseException {
        store.close();
        environment.close();
    }

}
