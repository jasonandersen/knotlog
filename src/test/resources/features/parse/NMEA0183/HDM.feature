@NMEA0183
@Parse
@HDM

Feature: NMEA0183 HDM sentence
    As a vessel operator using a compass that transmits NMEA0183
    I want HDM (Heading - Magnetic) sentences parsed 
    So that I can accurately plot my vessel heading to stay on course

    ################### HDM Sentence Structure ###################
    # 
    #         1  2 3
    #         |  | |
    # $--HDM,x.x,M*hh<CR><LF>
    # 
    # Field Number:
    # - Heading Degrees, magnetic
    # - M = magnetic
    # - Checksum
    # 
    ##############################################################

    Background:
        Given this NMEA0183 sentence from an instrument: "$AGHDM,293.1,M"

    @VesselHeading
    Scenario: Parse vessel heading from HDM sentence
        When the NMEA0183 sentence is parsed
        Then this vessel heading is returned:
            | vessel heading | heading 293°M |
            | source         | NMEA0183      |
