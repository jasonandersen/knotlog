######################### Message Timestamps ######################################
# 
# Ensure that timestamps on all vessel messages are stored in GMT.
# 
###################################################################################
@NMEA0183
@Parse
Feature: Vessel message timestamps stored in GMT
    As a vessel operator with NMEA0183 enabled instruments
    I want all vessel messages timestamped in GMT
    So that as I travel through time zones, my timestamps are independent of the time zone they were created in
    
    Scenario: TZ offset of -8 hours, local date and GMT date are the same
        Given the current local date and time is "2015-05-19T12:00:00-08:00"
        And this NMEA0183 sentence from an instrument: "$AGHDT,342.1,T"
        When the NMEA0183 sentence is parsed
        Then this vessel heading is returned:
            | timestamp | 2015-05-19T20:00:00Z |

    Scenario: TZ offset of -8 hours, GMT date is past local date
        Given the current local date and time is "2015-05-19T20:00:00-08:00"
        And this NMEA0183 sentence from an instrument: "$AGHDT,342.1,T"
        When the NMEA0183 sentence is parsed
        Then this vessel heading is returned:
            | timestamp | 2015-05-20T04:00:00Z |

    Scenario: TZ offset of +8 hours, local date and GMT date are the same
        Given the current local date and time is "2015-05-19T12:00:00+08:00"
        And this NMEA0183 sentence from an instrument: "$AGHDT,342.1,T"
        When the NMEA0183 sentence is parsed
        Then this vessel heading is returned:
            | timestamp | 2015-05-19T04:00:00Z |

    Scenario: TZ offset of +8 hours, local date is past GMT date
        Given the current local date and time is "2015-05-19T04:00:00+08:00"
        And this NMEA0183 sentence from an instrument: "$AGHDT,342.1,T"
        When the NMEA0183 sentence is parsed
        Then this vessel heading is returned:
            | timestamp | 2015-05-18T20:00:00Z |
            