package com.svhelloworld.knotlog.service;

import com.svhelloworld.knotlog.messages.VesselMessage;

/**
 * Provides NMEA0183 parsing facilities.
 */
public interface NMEA0183ParseService {

    /**
     * Parses a single NMEA0183 sentence into a vessel message.
     * @param sentence
     * @return a properly formed vessel message
     */
    VesselMessage parseSentence(String sentence);

}
