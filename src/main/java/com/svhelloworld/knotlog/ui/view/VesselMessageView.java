package com.svhelloworld.knotlog.ui.view;

import org.apache.commons.lang.Validate;

import com.svhelloworld.knotlog.i18n.BabelFish;
import com.svhelloworld.knotlog.messages.VesselMessage;
import com.svhelloworld.knotlog.messages.WaterDepth;
import com.svhelloworld.knotlog.messages.WindSpeed;

/**
 * @see com.svhelloworld.knotlog.ui.currentstate.WaterDepthView
 */
@Deprecated
public enum VesselMessageView {

    WATER_DEPTH(WaterDepth.class) {
        @Override
        protected String getValue(VesselMessage message) {
            Validate.notNull(message);
            WaterDepth depth = (WaterDepth) message;
            return String.format("%.1f %s", depth.getDistance(), BabelFish.localize(depth.getDistanceUnit()));
        }
    },
    WIND_SPEED(WindSpeed.class) {
        @Override
        protected String getValue(VesselMessage message) {
            Validate.notNull(message);
            WindSpeed speed = (WindSpeed) message;
            return String.format("%.1f %s", speed.getSpeed(), BabelFish.localize(speed.getSpeedUnit()));
        }
    };

    private Class<? extends VesselMessage> type;

    VesselMessageView(Class<? extends VesselMessage> type) {
        this.type = type;
    }

    protected abstract String getValue(VesselMessage message);

    public static String getMessageValue(VesselMessage message) {
        return null;
    }

}
