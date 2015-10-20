################### HDT Sentence Structure ###################
# 
# Actual vessel heading in degrees true produced by any device or system 
# producing true heading.
# 
#         1  2 3
#         |  | |
# $--HDT,x.x,M*hh<CR><LF>
# 
# Field Number:
# - Heading Degrees, magnetic
# - M = magnetic
# - Checksum
# 
##############################################################
@NMEA0183
@Parse
@HDT
Feature: NMEA0183 HDT sentence
    As a vessel operator using a compass that transmits NMEA0183
    I want HDT (Heading - Magnetic) sentences parsed 
    So that I can accurately plot my vessel heading to stay on course

    Background:
        Given this NMEA0183 sentence from an instrument: "$AGHDT,342.1,T"

    @VesselHeading
    Scenario: Parse vessel heading from HDT sentence
        When the NMEA0183 sentence is parsed
        Then this vessel heading is returned:
            | vessel heading | heading 342°T |
            | source         | NMEA0183      |
