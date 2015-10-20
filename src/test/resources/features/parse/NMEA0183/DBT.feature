######### DBT (Depth Below Transducer) Sentence Structure ######
#
#         1  2 3  4 5  6 7
#         |  | |  | |  | |
# $--DBT,x.x,f,x.x,M,x.x,F*hh<CR><LF>
#
# Field Number:
# - Depth, feet
# - f = feet
# - Depth, meters
# - M = meters
# - Depth, Fathoms
# - F = Fathoms
# - Checksum
# 
###############################################################
@NMEA0183
@Parse
@DBT
@WaterDepth
Feature: NMEA0183 DBT sentence
    As a vessel operator using a depth transducer that transmits NMEA0183 sentences 
    I want to have DBT (Depth Below Transducer) messages parsed correctly 
    So that my vessel doesn't hit bottom and cause damage or endanger my crew 

    Scenario: Parse a water depth message from a DBT sentence
        Given this NMEA0183 sentence from an instrument: "$IIDBT,020.8,f,006.3,M,003.4,F"
        When  the NMEA0183 sentence is parsed
        Then  this water depth is returned:
            | water depth | 20.8 feet |
            | source      | NMEA0183  |
