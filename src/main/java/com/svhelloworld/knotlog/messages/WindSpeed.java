package com.svhelloworld.knotlog.messages;

import java.time.Instant;
import java.util.List;

import com.svhelloworld.knotlog.measure.MeasurementBasis;
import com.svhelloworld.knotlog.measure.Speed;
import com.svhelloworld.knotlog.measure.SpeedUnit;
import com.svhelloworld.knotlog.util.MiscUtil;

/**
 * A message defining speed of the wind.
 * 
 * @author Jason Andersen
 * @since Mar 6, 2010
 *
 */
public class WindSpeed extends
        BaseQuantitativeMessage<SpeedUnit>implements Speed {

    private MeasurementBasis basis;

    /**
     * Constructor.
     * @param source message source
     * @param timestamp timestamp of message event
     * @param speed wind speed
     * @param unit unit of measure
     * @param basis determines whether measurement is relative or true
     * @throws NullPointerException when source is null
     * @throws NullPointerException when timestamp is null
     * @throws NullPointerException when measurement is null
     * @throws NullPointerException when basis is null
     */
    public WindSpeed(
            VesselMessageSource source,
            Instant timestamp,
            float speed,
            SpeedUnit unit,
            MeasurementBasis basis) {

        super(source, timestamp, speed, unit);
        if (basis == null) {
            throw new NullPointerException("measurement basis cannot be null");
        }
        this.basis = basis;
    }

    /**
     * @return wind speed measurement
     */
    public float getWindSpeed() {
        return quantity;
    }

    /**
     * @return basis for which wind speed measurement was taken
     */
    public MeasurementBasis getBasis() {
        return basis;
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getDisplayKey()
     */
    @Override
    protected String getDisplayKey() {
        return "display.wind.speed";
    }

    /**
     * @see com.svhelloworld.knotlog.messages.BaseInstrumentMessage#getNameKey()
     */
    @Override
    protected String getNameKey() {
        return "name.wind.speed";
    }

    /**
     * @see com.svhelloworld.knotlog.measure.Speed#getSpeed()
     */
    @Override
    public float getSpeed() {
        return getWindSpeed();
    }

    /**
     * @see com.svhelloworld.knotlog.measure.Speed#getSpeedUnit()
     */
    @Override
    public SpeedUnit getSpeedUnit() {
        return unit;
    }

    /**
     * @see com.svhelloworld.knotlog.i18n.Localizable#getLocalizeParams()
     */
    @Override
    public List<Object> getLocalizeParams() {
        return MiscUtil.varargsToList(basis, quantity, unit);
    }

}
