package com.svhelloworld.knotlog.db.berkeley;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sleepycat.je.Environment;
import com.sleepycat.je.Transaction;

/**
 * Truncates the database. Use with caution, there's no rolling this shit back.
 */
@Service
public class DatabaseTruncator {

    private static Logger log = LoggerFactory.getLogger(DatabaseTruncator.class);

    @Autowired
    private KnotlogEnvironment environment;

    /**
     * Truncates all of the databases within the environment.
     */
    public void truncate() {
        log.warn("truncating Berkeley DB environment");
        Environment berkeleyEnv = environment.getEnvironment();
        List<String> dbNames = berkeleyEnv.getDatabaseNames();

        /*
         * We have to close the entity store prior to truncating the databases because the store
         * holds open a read lock and the truncateDatabase method will timeout waiting on the locks.
         */
        berkeleyEnv.sync();
        environment.closeStore();

        int lockCount = environment.getTotalLockCount();
        if (lockCount > 0) {
            int readLocks = environment.getReadLockCount();
            int writeLocks = environment.getWriteLockCount();
            log.warn("{} open read locks and {} open write locks, truncate is gonna fail", readLocks, writeLocks);
        }

        Transaction txn = berkeleyEnv.beginTransaction(null, null);
        try {
            for (String dbName : dbNames) {
                long count = berkeleyEnv.truncateDatabase(txn, dbName, true);
                log.debug("truncated {} rows from {}", count, dbName);
            }
            txn.commit();
        } catch (Throwable e) {
            txn.abort();
            throw e;
        }

        environment.initStore();
    }

}
