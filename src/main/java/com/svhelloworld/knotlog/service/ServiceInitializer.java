package com.svhelloworld.knotlog.service;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.svhelloworld.knotlog.events.AppInitializationStarted;

/**
 * Listens for {@link AppInitializationStarted} events on the bus and will kick off
 * initialization for all {@link InitializableService}s.
 */
@Service
public class ServiceInitializer {

    private static Logger log = LoggerFactory.getLogger(ServiceInitializer.class);

    @Autowired
    private EventBus eventBus;

    @Autowired
    private Collection<InitializableService> services;

    @PostConstruct
    public void register() {
        eventBus.register(this);
    }

    /**
     * Run all initialization methods on any services that indicate that they have not been
     * initialized. This method can be called multiple times and will only call a service's
     * initialize() method if the service's isInitialize() method returns false.
     * @param event
     */
    @Subscribe
    public void kickoffInitialization(AppInitializationStarted event) {
        log.info("{} event received", event.getClass().getSimpleName());
        for (InitializableService service : services) {
            if (!service.isInitialized()) {
                log.info("kicking off initialization for {}", service.getClass().getSimpleName());
                initializeService(service);
            }
        }
    }

    /**
     * Initializes an individual service and handles error logging for any exceptions.
     * @param service
     */
    private void initializeService(InitializableService service) {
        try {
            /*
             * Eventually, we can throw an initialization progress event with total services and a count
             * of how many have been initialized to provide a progress bar, as well as the name of the
             * current service being initialized.
             */
            service.initialize();
        } catch (Exception e) {
            String message = "exception encountered while initializing " + service.getClass().getSimpleName();
            log.error(message, e);
            throw e;
        }
    }

}
