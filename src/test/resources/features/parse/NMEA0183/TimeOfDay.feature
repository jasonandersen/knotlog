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
        When the sentence is parsed
        Then this time of day is returned:
            | date        | 2015/10/21 |
            | time of day | 00:00:01   |
            | time zone   | GMT        |
