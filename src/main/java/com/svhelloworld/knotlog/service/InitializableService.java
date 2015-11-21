package com.svhelloworld.knotlog.service;

/**
 * Indicates a service that requires initialization.
 */
public interface InitializableService {

    /**
     * @return true if this service has been initialized, false if it has not been initialized
     */
    public boolean isInitialized();

    /**
     * Initializes the service. DO NOT CALL THIS METHOD. This method will be called during the
     * lifecycle of this service.
     * @throws IllegalStateException when this method is called and isInitialized() returns true
     */
    public void initialize();
}
