@NMEA0183
@Parse
@GGA

Feature:
    As a vessel operator using a GPS that transmits NMEA0183 sentences
    I want GGA (Global Positioning System Fix Data) sentences parsed 
    So that I get an accurate fix on my location

    Background:
        Given this NMEA0183 sentence from an instrument: "GPGGA,130004,2531.3369,N,11104.4274,W,2,09,0.9,1.7,M,-31.5,M,,"

    @GPSPosition
    @PositionPrecision
    @Altitude
    @TimeOfDayZulu
    Scenario: Parse a GGA sentence
        When the NMEA0183 sentence is parsed
        Then GPS position is returned
        And time of day is returned
        And position precision is returned
        And altitude is returned

    @GPSPosition
    Scenario: Parse GPS position from a GGA sentence
        When the NMEA0183 sentence is parsed
        Then this GPS position is returned:
            | position   | 25°31.337'N 111°04.427'W |
            | source     | NMEA0183                  |
    
    @PositionPrecision
    Scenario: Parse position precision from a GGA sentence
        When the NMEA0183 sentence is parsed
        Then this position precision is returned:
            | position precision | 1.7 meters |
            | source             | NMEA0183   |

    @Altitude
    Scenario: Parse altitude from a GGA sentence
        When the NMEA0183 sentence is parsed
        Then this altitude is returned:
            | altitude | -31.5 meters |
            | source   | NMEA0183     |

    @TimeOfDayZulu
    Scenario: Parse time of day from a GGA sentence
        When the NMEA0183 sentence is parsed
        Then this time of day is returned:
            | time of day (GMT) | 13:00:04 |
            | source            | NMEA0183 |
