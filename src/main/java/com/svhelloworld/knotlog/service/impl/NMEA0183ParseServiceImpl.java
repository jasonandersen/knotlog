package com.svhelloworld.knotlog.service.impl;

import java.util.List;

import com.svhelloworld.knotlog.messages.VesselMessage;
import com.svhelloworld.knotlog.service.NMEA0183ParseService;

/**
 * 
 * @author Jason Andersen (andersen.jason@gmail.com)
 */
public class NMEA0183ParseServiceImpl implements NMEA0183ParseService {

    /**
     * @see com.svhelloworld.knotlog.service.NMEA0183ParseService#parseSentence(java.lang.String)
     */
    @Override
    public List<VesselMessage> parseSentence(String sentence) {
        return null;
    }

}
