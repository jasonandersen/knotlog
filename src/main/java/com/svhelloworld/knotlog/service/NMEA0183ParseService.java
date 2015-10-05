package com.svhelloworld.knotlog.service;

import java.util.List;

import com.svhelloworld.knotlog.messages.VesselMessage;

/**
 * Provides NMEA0183 parsing facilities.
 */
public interface NMEA0183ParseService {

    /**
     * Parses a single NMEA0183 sentence into a list of vessel messages.
     * @param sentence
     * @return a properly formed vessel message
     */
    List<VesselMessage> parseSentence(String sentence);

}
