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
@NMEA0183
@Parse
@HDG
Feature: NMEA0183 HDG sentence
    As a vessel operator using a GPS that transmits NMEA0183
    I want HDG (Heading Deviation and Variation) sentences parsed 
    So that I can accurately plot my vessel heading to stay on course and understand magnetic variation so I can accurately calculate true heading from magnetic headings

    Background:
        Given this NMEA0183 sentence from an instrument: "$AGHDG,288.6,18.0,E,20.1,E"

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
            | vessel heading | heading 289°M |
            | source         | NMEA0183      |
    
    @MagneticVariation
    Scenario: Parse magnetic variation from HDG sentence
        When the NMEA0183 sentence is parsed
        Then this magnetic variation is returned:
            | magnetic variation | magnetic variation 20.1°E |
            | source             | NMEA0183                  |
