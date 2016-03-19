Feature: Display raw NMEA0183
    As a vessel operator
    I want to see raw NMEA0183 sentences as they come in from the instruments
    So that I can diagnose NMEA0183 instruments
    
    Scenario: Display raw NMEA0183
        Given these NMEA0183 sentences from instruments: 
            | $IIDBT,020.8,f,006.3,M,003.4,F                                      |
            | $GPGGA,130004,2531.3369,N,11104.4274,W,2,09,0.9,1.7,M,-31.5,M,,     |
            | $AGHDG,288.6,18.0,E,20.1,E                                          |
            | $IIVWR,67.8,L,18.4,N,,,,                                            |
        Then I see these raw NMEA0183 sentences:
            | $IIDBT,020.8,f,006.3,M,003.4,F                                      |
            | $GPGGA,130004,2531.3369,N,11104.4274,W,2,09,0.9,1.7,M,-31.5,M,,     |
            | $AGHDG,288.6,18.0,E,20.1,E                                          |
            | $IIVWR,67.8,L,18.4,N,,,,                                            |
        
    Scenario: Cap the display at 100 sentences
        Given I receive 300 NMEA0183 sentences from instruments
        Then I see 100 raw NMEA0183 sentences: