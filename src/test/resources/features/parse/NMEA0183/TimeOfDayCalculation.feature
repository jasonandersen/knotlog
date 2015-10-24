######################### TimeOfDay calculations #################################
# 
# Ensure that when we receive TimeOfDay messages with GMT times on them, that the
# day is calculated correctly. There are times when the GMT date will be different
# than the local date and we need to make sure we're calculating those correctly.
# 
###################################################################################
@NMEA0183
@Parse
@TimeOfDay
Feature: GMT time and date calculations
    As a vessel operator with NMEA0183 enabled instruments
    I want to have GMT date and time calculated properly from all messages at all times during the day
    So that my timestamps are correct and uniform

    Scenario Outline: Local date is the same date as GMT date
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
            | 2015-10-20T23:59:59+01:00 | 225959    | 2015/10/20   | 22:59:59     |
            | 2015-10-20T23:59:59+02:00 | 215959    | 2015/10/20   | 21:59:59     |
            | 2015-10-20T23:59:59+03:00 | 205959    | 2015/10/20   | 20:59:59     |

    Scenario Outline: Local date is different than GMT date
        Given the current local date and time is "<localDateTime>"
        And an NMEA0183 sentence that transmits GMT time of day with a time field of "<timeField>"
        When the NMEA0183 sentence is parsed
        Then this time of day is returned:
            | date        | <expectedDate> |
            | time of day | <expectedTime> |
            | time zone   | GMT            |
        Examples:
            | localDateTime             | timeField | expectedDate | expectedTime |
            | 2015-10-20T16:00:01-08:00 | 000001    | 2015/10/21   | 00:00:01     |
            | 2015-10-20T17:00:01-07:00 | 000001    | 2015/10/21   | 00:00:01     |
            | 2015-10-20T18:00:01-06:00 | 000001    | 2015/10/21   | 00:00:01     |
            | 2015-10-21T00:59:59+01:00 | 235959    | 2015/10/20   | 23:59:59     |
            