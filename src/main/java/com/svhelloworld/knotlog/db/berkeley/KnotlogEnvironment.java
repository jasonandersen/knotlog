package com.svhelloworld.knotlog.db.berkeley;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.EnvironmentStats;
import com.sleepycat.je.StatsConfig;
import com.sleepycat.je.Transaction;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.StoreConfig;
import com.svhelloworld.knotlog.LocalFiles;

/**
 * Responsible for setting up a BerkeleyDB {@link Environment}.
 */
@Component
public class KnotlogEnvironment {

    private static Logger log = LoggerFactory.getLogger(KnotlogEnvironment.class);

    @Autowired
    private LocalFiles localFiles;

    private Environment environment;

    private EntityStore store;

    /**
     * Retrieve a primary index for an entity type. Primary indices should never be stored. Every time
     * a calling class needs access to a primary index, they should call this method. This will ensure
     * the index hasn't been retrieved from an {@link EntityStore} that was closed.
     * @param keyClass the class of the primary key
     * @param entityClass the class of the entity
     * @return a valid primary index for an entity, will not return null
     */
    public <PK, E> PrimaryIndex<PK, E> getPrimaryIndex(Class<PK> keyClass, Class<E> entityClass) {
        log.debug("fetching primary index for {}s using {} keys", entityClass.getSimpleName(), keyClass.getSimpleName());
        return store.getPrimaryIndex(keyClass, entityClass);
    }

    /**
     * Starts a new transaction 
     * @return the transaction handle
     */
    public Transaction startTransaction() {
        log.debug("starting transaction");
        return environment.beginTransaction(null, null);
    }

    /**
     * @param config can be null
     * @return database environment statistics
     */
    public EnvironmentStats getStats(StatsConfig config) {
        return environment.getStats(config);
    }

    /**
     * @return the total number of open locks against the database
     */
    public int getTotalLockCount() {
        return getReadLockCount() + getWriteLockCount();
    }

    /**
     * @return the number of open read locks
     */
    public int getReadLockCount() {
        EnvironmentStats stats = getStats(null);
        return stats.getNReadLocks();
    }

    /**
     * @return the number of open write locks
     */
    public int getWriteLockCount() {
        EnvironmentStats stats = getStats(null);
        return stats.getNWriteLocks();
    }

    /**
     * @return the Berkeley DB JE environment
     */
    protected Environment getEnvironment() {
        return environment;
    }

    /**
     * Closes the store only, not the environment.
     */
    protected void closeStore() {
        if (store != null) {
            log.info("closing entity store");
            store.close();
        }
    }

    /**
     * Initializes the entity store within the environment.
     */
    protected void initStore() {
        log.info("initializing entity store");
        StoreConfig config = new StoreConfig();
        config.setAllowCreate(true);
        config.setTransactional(true);
        store = new EntityStore(environment, getClass().getSimpleName(), config);
    }

    /**
     * Do not call directly. Will be called during the lifecycle of this managed bean.
     */
    @PostConstruct
    private void initialize() {
        log.info("initializing");
        initEnvironment();
        initStore();
    }

    /**
     * Do not call directly. Will be called during the lifecycle of this managed bean.
     */
    @PreDestroy
    private void close() {
        if (environment == null) {
            return;
        }
        try {
            closeStore();
        } finally {
            log.info("closing database environment");
            environment.close();
        }
    }

    /**
     * Initializes Berkeley DB JE environment.
     */
    private void initEnvironment() {
        log.info("initializing Berkeley DB environment");
        EnvironmentConfig envConf = new EnvironmentConfig();
        envConf.setAllowCreate(true);
        envConf.setTransactional(true);
        environment = new Environment(localFiles.getDataDirectory(), envConf);
    }

}
