package com.svhelloworld.knotlog.domain;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

/**
 * Can represent any vessel that generates messages.
 */
@Entity
public class Vessel {

    @PrimaryKey
    private Integer id;

    private String name;

    private VesselType type;

    /**
     * Empty constructor
     */
    public Vessel() {
        //empty constructor
    }

    /**
     * Constructor
     * @param id
     * @param name
     * @param type
     */
    public Vessel(Integer id, String name, VesselType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the type of vessel
     */
    public VesselType getType() {
        return type;
    }

    /**
     * @param newType
     */
    public void setType(VesselType newType) {
        this.type = newType;
    }

}
