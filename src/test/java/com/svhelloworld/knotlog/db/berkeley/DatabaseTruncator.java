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
    private KnotlogDatabase database;

    public void truncate() {
        log.warn("truncating Berkeley DB environment");
        database.getEnvironment().sync();
        database.close();
        database.initEnvironment();

        Environment env = database.getEnvironment();
        List<String> dbNames = env.getDatabaseNames();
        Transaction txn = env.beginTransaction(null, null);
        try {
            for (String dbName : dbNames) {

                long count = env.truncateDatabase(txn, dbName, true);
                log.debug("truncated {} rows from {}", count, dbName);
            }
            txn.commit();
        } catch (Throwable e) {
            txn.abort();
            throw e;
        }

        database.initStore();
    }

    /**
     * Truncates all of the databases within the environment.
     */
    public void truncateDONTUSE() {
        log.warn("truncating Berkeley DB environment");
        Environment env = database.getEnvironment();
        List<String> dbNames = env.getDatabaseNames();

        /*
         * We have to close the entity store prior to truncating the databases because the store
         * holds open a read lock and the truncateDatabase method will timeout waiting on the locks.
         */
        env.sync();
        database.closeStore();

        int lockCount = database.getTotalLockCount();
        if (lockCount > 0) {
            int readLocks = database.getReadLockCount();
            int writeLocks = database.getWriteLockCount();
            log.warn("{} open read locks and {} open write locks, truncate is gonna fail", readLocks, writeLocks);
        }

        Transaction txn = env.beginTransaction(null, null);
        try {
            for (String dbName : dbNames) {
                long count = env.truncateDatabase(txn, dbName, true);
                log.debug("truncated {} rows from {}", count, dbName);
            }
            txn.commit();
        } catch (Throwable e) {
            txn.abort();
            throw e;
        }

        database.initStore();
    }

}
