@NMEA0183
@Parse
@TimeOfDay
Feature: GMT time and date calculations
    As a vessel operator with NMEA0183 enabled instruments
    I want to have GMT date and time calculated properly from all messages at all times during the day
    So that my timestamps are correct and uniform
    
    Scenario: Local date matches GMT date
        Given the current local date and time is "2015-10-20T15:59:59-08:00"
        And an NMEA0183 sentence that transmits GMT time of day with a time field of "235959"
        When the NMEA0183 sentence is parsed
        Then this time of day is returned:
            | date        | 2015/10/20 |
            | time of day | 23:59:59   |
            | time zone   | GMT        |

    Scenario: Local date is one day prior to GMT date
        Given the current local date and time is "2015-10-20T16:00:01-08:00"
        And an NMEA0183 sentence that transmits GMT time of day with a time field of "000001"
        When the NMEA0183 sentence is parsed
        Then this time of day is returned:
            | date        | 2015/10/21 |
            | time of day | 00:00:01   |
            | time zone   | GMT        |

    Scenario Outline: Local date is one day prior to GMT date
        Given the current local date and time is "<localDateTime>"
        And an NMEA0183 sentence that transmits GMT time of day with a time field of "<timeField>"
        When the NMEA0183 sentence is parsed
        Then this time of day is returned:
            | date        | <expectedDate> |
            | time of day | <expectedTime> |
            | time zone   | GMT            |
        Examples:
            | localDateTime             | timeField | expectedDate | expectedTime |
            | 2015-10-20T15:59:59-08:00 | 235959    | 2015/10/20   | 23:59:59     |
            | 2015-10-20T16:59:59-07:00 | 235959    | 2015/10/20   | 23:59:59     |
            | 2015-10-20T17:59:59-06:00 | 235959    | 2015/10/20   | 23:59:59     |
            | 2015-10-20T18:59:59-05:00 | 235959    | 2015/10/20   | 23:59:59     |
            | 2015-10-20T19:59:59-04:00 | 235959    | 2015/10/20   | 23:59:59     |
            | 2015-10-20T20:59:59-03:00 | 235959    | 2015/10/20   | 23:59:59     |
            | 2015-10-20T21:59:59-02:00 | 235959    | 2015/10/20   | 23:59:59     |
            | 2015-10-20T22:59:59-01:00 | 235959    | 2015/10/20   | 23:59:59     |
            | 2015-10-20T16:00:01-08:00 | 000001    | 2015/10/21   | 00:00:01     |
            | 2015-10-20T17:00:01-07:00 | 000001    | 2015/10/21   | 00:00:01     |
            | 2015-10-20T18:00:01-06:00 | 000001    | 2015/10/21   | 00:00:01     |
