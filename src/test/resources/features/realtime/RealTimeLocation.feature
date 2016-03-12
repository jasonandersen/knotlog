@RealTime
Feature: Real time display
    As a vessel operator
    I want to see the current state of vessel instruments
    So I can make navigation decisions
    
    Scenario: Real time display
        Given these NMEA0183 sentences from instruments: 
            | "$IIDBT,020.8,f,006.3,M,003.4,F"                                      |
            | "$GPGGA,130004,2531.3369,N,11104.4274,W,2,09,0.9,1.7,M,-31.5,M,,"     |
            | "$AGHDG,288.6,18.0,E,20.1,E"                                          |
            | "$IIVWR,67.8,L,18.4,N,,,,"                                            |
        Then my current state display should read:
            | name                  | value                     | source        |
            | water depth           | 20.8 feet                 | NMEA0183      |
            | relative wind speed   | 18.4 knots                | NMEA0183      |
            | position              | 25°31.337'N 111°04.427'W  | NMEA0183      |
            | heading               | 289°M                     | NMEA0183      |
        
    Scenario: Multiple messages of the same type are only displayed once
        Given these NMEA0183 sentences from instruments: 
            | "$IIDBT,010.0,f,006.3,M,003.4,F" |
            | "$IIDBT,020.0,f,006.3,M,003.4,F" |
            | "$IIDBT,030.0,f,006.3,M,003.4,F" |
            | "$IIDBT,040.0,f,006.3,M,003.4,F" |
            | "$IIDBT,050.0,f,006.3,M,003.4,F" |
        Then my current state display should read:
            | name                  | value                     | source        |
            | water depth           | 50.0 feet                 | NMEA0183      |
        
