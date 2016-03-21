package com.svhelloworld.knotlog.domain.messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A defined collection of waypoints.
 * 
 * @author Jason Andersen
 * @since Feb 14, 2010
 *
 */
public class WayPointSet {
    /**
     * name of the waypoint set
     */
    private String name;
    /**
     * the waypoints defining the waypoint set
     */
    private List<WayPoint> points;
    
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
     * @return the points
     */
    public List<WayPoint> getPoints() {
        return points;
    }
    
    /**
     * @param points the points to set
     */
    public void setPoints(List<WayPoint> points) {
        this.points = points;
    }
    
    /**
     * add waypoints to the collection
     * @param wayPoints waypoints to add to the collection
     */
    public void addPoints(WayPoint...wayPoints) {
        if (this.points == null) {
            initCollection();
        }
        this.points.addAll(Arrays.asList(wayPoints));
    }
    
    /**
     * initializes the collection object
     */
    private void initCollection() {
        this.points = new ArrayList<WayPoint>();
    }
}
