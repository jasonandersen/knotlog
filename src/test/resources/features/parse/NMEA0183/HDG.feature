@NMEA0183
@Parse
@HDG

Feature:
    As a vessel operator using a GPS that transmits NMEA0183
    I want HDG (Heading Deviation and Variation) sentences parsed 
    So that I can accurately plot my vessel heading to stay on course
    And understand magnetic variation so I can accurately calculate true heading from magnetic headings

    ################### HDG Sentence Structure ###################
    #
    #         1  2  3 4  5 6
    #         |  |  | |  | |
    # $--HDG,x.x,x.x,a,x.x,a*hh<CR><LF>
    # 
    # Field Number:
    # - Magnetic Sensor heading in degrees
    # - Magnetic Deviation, degrees
    # - Magnetic Deviation direction, E = Easterly, W = Westerly
    # - Magnetic Variation degrees
    # - Magnetic Variation direction, E = Easterly, W = Westerly
    # - Checksum
    # 
    ##############################################################

    Background:
        Given this NMEA0183 sentence from an instrument: "AGHDG,288.6,,,,"

    @VesselHeading
    @MagneticVariation
    Scenario: Parse a HDG sentence
        When the NMEA0183 sentence is parsed
        Then vessel heading is returned
        And magnetic variation is returned

    @VesselHeading
    Scenario: Parse vessel heading from HDG sentence
        When the NMEA0183 sentence is parsed
        Then this vessel heading is returned:
            | heading | 315.4°   |
            | source  | NMEA0183 |
    
    @MagneticVariation
    Scenario: Parse magnetic variation from HDG sentence
        When the NMEA0183 sentence is parsed
        Then this magnetic variation is returned:
            | magnetic variation | 18°      |
            | source             | NMEA0183 |
