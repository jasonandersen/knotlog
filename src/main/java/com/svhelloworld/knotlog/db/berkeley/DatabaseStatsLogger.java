package com.svhelloworld.knotlog.db.berkeley;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentStats;
import com.sleepycat.je.StatsConfig;
import com.sleepycat.je.utilint.StatGroup;
import com.svhelloworld.knotlog.events.LogDatabaseStats;

/**
 * Logs database statistics when a {@link LogDatabaseStats} event is received.
 */
@Component
public class DatabaseStatsLogger {

    private static Logger log = LoggerFactory.getLogger(DatabaseStatsLogger.class);

    private static final String KEY_LOCKS = "Locks";

    @Autowired
    private KnotlogEnvironment knotlogEnvironment;

    @Autowired
    private EventBus eventBus;

    private Environment environment;

    @PostConstruct
    private void initialize() {
        eventBus.register(this);
        environment = knotlogEnvironment.getEnvironment();
    }

    /**
     * Handle events requesting database stats be logged.
     */
    @Subscribe
    public void logDatabaseStats(LogDatabaseStats event) {
        StatsConfig config = new StatsConfig();
        //config.setFast(true);
        EnvironmentStats stats = environment.getStats(config);
        /*
         * just pull out locks for now
         */
        Map<String, StatGroup> groupsMap = stats.getStatGroupsMap();
        StatGroup locksGroup = groupsMap.get(KEY_LOCKS);

        log.info(event.getMessage());
        log.info("Locks stats group\n{}", locksGroup.toString());
    }
}
