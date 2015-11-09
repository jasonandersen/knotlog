package com.svhelloworld.knotlog.messages;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A vessel track.
 * 
 * @author Jason Andersen
 * @since Feb 14, 2010
 *
 */
public class Track extends BaseInstrumentMessage implements ValidVesselMessage {

    /**
     * The name of this track
     */
    private String trackName;
    /**
     * Description of this track
     */
    private String description;
    /**
     * Track points for this track
     */
    private List<TrackPoint> points; //TODO change this to a SortedSet?

    /**
     * @param source
     * @param timestamp
     * @param name
     * @param description
     */
    public Track(VesselMessageSource source, Instant timestamp, String name, String description) {
        this(source, timestamp);
        this.trackName = name;
        this.description = description;
    }

    /**
     * @param source
     * @param timestamp
     */
    public Track(VesselMessageSource source, Instant timestamp) {
        super(source, timestamp);
        points = new ArrayList<TrackPoint>();
    }

    /**
     * @return an unmodfiable list of track points
     */
    public List<TrackPoint> getTrackPoints() {
        return Collections.unmodifiableList(this.points);
    }

    /**
     * @return The name of this track
     */
    public String getTrackName() {
        return this.trackName;
    }

    /**
     * @return the description of this track
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Add track points to this track
     * @param points
     */
    public void addTrackPoints(TrackPoint... points) {
        this.points.addAll(Arrays.asList(points));
    }

    /**
     * Add track points to this track
     * @param points
     */
    public void addTrackPoints(Collection<TrackPoint> points) {
        this.points.addAll(points);
    }

    /**
     * @see com.svhelloworld.knotlog.i18n.Localizable#getLocalizeParams()
     */
    @Override
    public List<Object> getLocalizeParams() {
        return null;
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getDisplayKey()
     */
    @Override
    protected String getDisplayKey() {
        return "display.track";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getNameKey()
     */
    @Override
    protected String getNameKey() {
        return "name.track";
    }

}
