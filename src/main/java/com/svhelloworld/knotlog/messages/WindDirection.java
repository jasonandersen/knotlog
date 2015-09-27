package com.svhelloworld.knotlog.messages;

import java.util.Date;
import java.util.List;

import com.svhelloworld.knotlog.measure.AngleUnit;
import com.svhelloworld.knotlog.measure.MeasurementBasis;
import com.svhelloworld.knotlog.measure.VesselArea;
import com.svhelloworld.knotlog.util.MiscUtil;

/**
 * A message defining direction of wind relative to the vessel's bow.
 * 
 * @author Jason Andersen
 * @since Feb 24, 2010
 *
 */
public class WindDirection extends BaseQuantitativeMessage<AngleUnit> {

    /**
     * Basis for wind direction measurement.
     */
    private final MeasurementBasis basis;
    /**
     * Determines which side of the vessel the wind is on.
     */
    private final VesselArea vesselSide;

    /**
     * Constructor.
     * @param source message source
     * @param timestamp timestamp of message event
     * @param direction wind direction, can be passed as circular degrees (0° - 360°) 
     *          with the bow of the vessel at 0° or can be passed as semi-circular
     *          degrees (0° - 180°) and coupled with the <tt>vesselSide</tt> 
     *          argument to determine which side of the vessel the wind is coming from
     * @param basis describes whether the wind direction measurement is relative
     *          or true
     * @param vesselSide determines which side of the vessel the wind is coming over.
     *          If this value is null, then <tt>direction</tt> will be treated as 
     *          circular degrees. If this value is not null and <tt>direction</tt> is
     *          less than or equal to 180, then <tt>direction</tt>  will be treated as 
     *          semi-circular degrees.
     * @throws NullPointerException when source is null
     * @throws NullPointerException when timestamp is null
     * @throws NullPointerException when basis is null
     * @throws IllegalArgumentException when direction is less than zero or greater
     *          than 360.
     */
    public WindDirection(
            final VesselMessageSource source,
            final Date timestamp,
            final float direction,
            final MeasurementBasis basis,
            final VesselArea vesselSide) {

        super(source, timestamp, direction, AngleUnit.DEGREES);
        if (basis == null) {
            throw new NullPointerException("measurement basis cannot be null");
        }
        if (direction < 0 || direction > 360) {
            throw new IllegalArgumentException("wind direction not a valid value: " + direction);
        }
        /*
         * if the wind direction > 180, then it's coming from the port side.
         * We need to convert that to semi-circular degrees and indicate
         * vessel side.
         */
        float windAngle = direction;
        if (windAngle > 180) {
            windAngle = 360 - direction;
            this.vesselSide = VesselArea.PORT;
        } else {
            this.vesselSide = vesselSide == null ? VesselArea.STARBOARD : vesselSide;
        }
        super.quantity = windAngle;
        this.basis = basis;
    }

    /**
     * @return wind direction in degrees relative to the bow of the vessel
     */
    public float getWindDirection() {
        return quantity;
    }

    /**
     * @return determines whether wind direction is measured
     * as relative wind or true wind
     */
    public MeasurementBasis getBasis() {
        return basis;
    }

    /**
     * @return determines which side of the vessel the wind 
     * is passing over
     */
    public VesselArea getVesselSide() {
        return vesselSide;
    }

    /**
     * @see com.svhelloworld.knotlog.i18n.Localizable#getLocalizeParams()
     */
    @Override
    public List<Object> getLocalizeParams() {
        return MiscUtil.varargsToList(basis, quantity, unit.getSuffix(), vesselSide);
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getDisplayKey()
     */
    @Override
    protected String getDisplayKey() {
        return "display.wind.direction";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getNameKey()
     */
    @Override
    protected String getNameKey() {
        return "name.wind.direction";
    }

}
