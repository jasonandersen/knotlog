package com.svhelloworld.knotlog.messages;

import java.time.Instant;
import java.util.List;

import com.svhelloworld.knotlog.measure.MeasurementUnit;
import com.svhelloworld.knotlog.util.MiscUtil;

/**
 * Base instrument message class for messages that are scalar
 * quantitative messages.
 * 
 * @author Jason Andersen
 * @param <M> type of measurement unit
 * @since Feb 25, 2010
 *
 */
public abstract class BaseQuantitativeMessage<M extends MeasurementUnit>
        extends BaseInstrumentMessage implements QuantitativeMessage {

    /**
     * Quantity communicated in this message.
     * Note: we're not marking this field as <tt>final</tt> because some
     * child class constructors will change it's value.
     */
    protected float quantity;
    /**
     * Unit of measure.
     */
    protected final M unit;

    /**
     * Constructor.
     * @param source message source
     * @param timestamp timestamp of message event
     * @param quantity quantity indicated by message
     * @param unit unit of measure
     * @throws NullPointerException when source is null
     * @throws NullPointerException when timestamp is null
     * @throws NullPointerException when measurement is null
     */
    public BaseQuantitativeMessage(
            final VesselMessageSource source,
            final Instant timestamp,
            final float quantity,
            final M unit) {

        super(source, timestamp);
        if (unit == null) {
            throw new NullPointerException("measurement cannot be null");
        }
        this.quantity = quantity;
        this.unit = unit;
    }

    /**
     * @see com.svhelloworld.knotlog.messages.QuantitativeMessage#getMeasurementUnit()
     */
    @Override
    public MeasurementUnit getMeasurementUnit() {
        return unit;
    }

    /**
     * @see com.svhelloworld.knotlog.messages.QuantitativeMessage#getQuantity()
     */
    @Override
    public Number getQuantity() {
        return quantity;
    }

    /**
     * @see com.svhelloworld.knotlog.i18n.Localizable#getLocalizeParams()
     */
    @Override
    public List<Object> getLocalizeParams() {
        return MiscUtil.varargsToList(quantity, unit.getSuffix());
    }

}
