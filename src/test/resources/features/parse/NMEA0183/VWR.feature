@NMEA0183
@Parse

Feature: NMEA0183 VWR sentence
    As a vessel operator using an anomometer that transmits NMEA0183 sentences 
    I want to have VWR (Relative Wind Speed and Angle) messages parsed correctly 
    So that I can trim sails properly and maintain the correct wind angle 

    ############### VWR Sentence Structure #######################
    #         1  2  3  4  5  6  7  8 9
    #         |  |  |  |  |  |  |  | |
    # $--VWR,x.x,a,x.x,N,x.x,M,x.x,K*hh<CR><LF>
    # 
    # Field Number:
    # - Wind direction magnitude in degrees
    # - Wind direction Left/Right of bow
    # - Speed
    # - N = Knots
    # - Speed
    # - M = Meters Per Second
    # - Speed
    # - K = Kilometers Per Hour
    # - Checksum
    # 
    ##############################################################

    @WindDirection
    @WindSpeed
    Scenario: Parse a VWR sentence
        Given this NMEA0183 sentence from an instrument: "$IIVWR,67.8,L,18.4,N,,,,"
        When the NMEA0183 sentence is parsed
        Then wind direction is returned
        And wind speed is returned

    @WindDirection
    Scenario: Parse starboard wind direction from a VWR sentence
        Given this NMEA0183 sentence from an instrument: "$IIVWR,34.4,R,18.4,N,,,,"
        When the NMEA0183 sentence is parsed
        Then this wind direction is returned:
            | wind direction | relative wind direction 34° to starboard |
            | source         | NMEA0183                                 |

    @WindDirection
    Scenario: Parse port wind direction from a VWR sentence
        Given this NMEA0183 sentence from an instrument: "$IIVWR,67.9,L,18.4,N,,,,"
        When the NMEA0183 sentence is parsed
        Then this wind direction is returned:
            | wind direction | relative wind direction 68° to port |
            | source         | NMEA0183                            |

    @WindSpeed
    Scenario: Parse wind speed from a VWR sentence
        Given this NMEA0183 sentence from an instrument: "$IIVWR,34.4,R,18.4,N,,,,"
        When the NMEA0183 sentence is parsed
        Then this wind speed is returned:
            | wind speed | relative wind speed 18.4 knots |
            | source     | NMEA0183                       |
