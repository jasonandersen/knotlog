@NMEA0183
@Parse
@GGA

Feature: NMEA0183 GGA sentence
    As a vessel operator using a GPS that transmits NMEA0183
    I want GGA (Global Positioning System Fix Data) sentences parsed 
    So that I get an accurate fix on my location

    ################### GGA Sentence Structure ###################
    #
    #                                                       11
    #          1        2      3 4        5 6 7  8  9  10 |  12 13  14  15
    #          |        |      | |        | | |  |  |  | |  | |  |    |
    # $--GGA,hhmmss.ss,llll.ll,a,yyyyy.yy,a,x,xx,x.x,x.x,M,x.x,M,x.x,xxxx*hh<CR><LF>
    # 
    # Field Number:
    # - Universal Time Coordinated (UTC)
    # - Latitude
    # - N or S (North or South)
    # - Longitude
    # - E or W (East or West)
    # - GPS Quality Indicator,
    #     - 0 - fix not available,
    #     - 1 - GPS fix,
    #     - 2 - Differential GPS fix (values above 2 are 2.3 features)
    #     - 3 = PPS fix
    #     - 4 = Real Time Kinematic
    #     - 5 = Float RTK
    #     - 6 = estimated (dead reckoning)
    #     - 7 = Manual input mode
    #     - 8 = Simulation mode
    # - Number of satellites in view, 00 - 12
    # - Horizontal Dilution of precision (meters)
    # - Antenna Altitude above/below mean-sea-level (geoid) (in meters)
    # - Units of antenna altitude, meters
    # - Geoidal separation, the difference between the WGS-84 earth ellipsoid and mean-sea-level (geoid), "-" means mean-sea-level below ellipsoid
    # - Units of geoidal separation, meters
    # - Age of differential GPS data, time in seconds since last SC104 type 1 or 9 update, null field when DGPS is not used
    # - Differential reference station ID, 0000-1023
    # - Checksum
    #
    ###############################################################

    Background:
        Given this NMEA0183 sentence from an instrument: "GPGGA,130004,2531.3369,N,11104.4274,W,2,09,0.9,1.7,M,-31.5,M,,"

    @GPSPosition
    @PositionPrecision
    @Altitude
    @TimeOfDayZulu
    Scenario: Parse a GGA sentence
        When the NMEA0183 sentence is parsed
        Then GPS position is returned
        And time of day is returned
        And position precision is returned
        And altitude is returned

    @GPSPosition
    Scenario: Parse GPS position from a GGA sentence
        When the NMEA0183 sentence is parsed
        Then this GPS position is returned:
            | position   | 25°31.337'N 111°04.427'W |
            | source     | NMEA0183                 |
    
    @PositionPrecision
    Scenario: Parse position precision from a GGA sentence
        When the NMEA0183 sentence is parsed
        Then this position precision is returned:
            | position precision | 0.9 meters |
            | source             | NMEA0183   |

    @Altitude
    Scenario: Parse altitude from a GGA sentence
        When the NMEA0183 sentence is parsed
        Then this altitude is returned:
            | altitude | 1.7 meters |
            | source   | NMEA0183     |

    @TimeOfDayZulu
    Scenario: Parse time of day from a GGA sentence
        When the NMEA0183 sentence is parsed
        Then this time of day is returned:
            | date        | [today]  |
            | time of day | 13:00:04 |
            | time zone   | GMT      |
            | source      | NMEA0183 |
