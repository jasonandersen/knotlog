package com.svhelloworld.knotlog.events;

import org.apache.commons.lang.Validate;

import com.svhelloworld.knotlog.domain.Vessel;

/**
 * Indicates a new vessel has been added.
 */
public class NewVessel {

    private final Vessel vessel;

    /**
     * Constructor
     * @param vessel
     */
    public NewVessel(Vessel vessel) {
        Validate.notNull(vessel);
        this.vessel = vessel;
    }

    /**
     * @return the newly added vessel
     */
    public Vessel getVessel() {
        return vessel;
    }

}
