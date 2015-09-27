package com.svhelloworld.knotlog.messages;

import java.util.Date;
import java.util.List;

import com.svhelloworld.knotlog.measure.AngleUnit;
import com.svhelloworld.knotlog.measure.VesselArea;
import com.svhelloworld.knotlog.util.MiscUtil;

/**
 * A message indicating the angle of the vessel's rudder. A 
 * rudder angle &lt; zero indicates a turn to port. A rudder
 * angle &gt; zero indicates a turn to starboard.
 * 
 * @author Jason Andersen
 * @since Mar 6, 2010
 *
 */
public class RudderAngle extends BaseQuantitativeMessage<AngleUnit> {
    
    /**
     * Indicates which side of the vessel the rudder is on. Single
     * rudder vessels will show as STARBOARD.
     */
    private final VesselArea rudderSide;
    
    /**
     * Constructor.
     * @param source message source
     * @param timestamp timestamp of message event
     * @param angle rudder angle
     * @param rudderSide side of the vessel the rudder is on, if single
     *          rudder vessel assume STARBOARD, can be null
     * @throws NullPointerException when source is null
     * @throws NullPointerException when timestamp is null
     * @throws IllegalArgumentException when angle &lt; -180 or &gt; 180
     */
    public RudderAngle(
            final VesselMessageSource source, 
            final Date timestamp, 
            final float angle, 
            final VesselArea rudderSide) {
        
        super(source, timestamp, angle, AngleUnit.DEGREES);
        if (angle < -180 || angle > 180) {
            throw new IllegalArgumentException("illegal rudder angle: " + angle);
        }
        //default vessel side to STARBOARD
        this.rudderSide = rudderSide == null ? VesselArea.STARBOARD : rudderSide;
    }
    
    /**
     * @return angle of the vessel's rudder
     */
    public float getRudderAngle() {
        return quantity;
    }
    
    /**
     * @return the side of the vessel the rudder is located on. Single
     * rudder vessels will be displayed as STARBOARD.
     */
    public VesselArea getRudderVesselSide() {
        return rudderSide;
    }

    /**
     * @see com.svhelloworld.knotlog.i18n.Localizable#getLocalizeParams()
     */
    @Override
    public List<Object> getLocalizeParams() {
        return MiscUtil.varargsToList(rudderSide, quantity, unit.getSuffix());
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getDisplayKey()
     */
    @Override
    protected String getDisplayKey() {
        return "display.rudder.angle";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getNameKey()
     */
    @Override
    protected String getNameKey() {
        return "name.rudder.angle";
    }

}
