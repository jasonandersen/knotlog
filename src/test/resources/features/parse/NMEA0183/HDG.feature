@NMEA0183
@Parse
@HDG

Feature:
    As a vessel operator using a GPS that transmits NMEA0183
    I want HDG (Heading Deviation and Variation) sentences parsed 
    So that I can accurately plot my vessel heading to stay on course

    Background:
        Given this NMEA0183 sentence from an instrument: "HDG,315.4,,,,"

    @VesselHeading
    @MagneticVariation
    Scenario: Parse a HDG sentence
        When the NMEA0183 sentence is parsed
        Then vessel heading is returned
        And magnetic variation is returned

    @GPSPosition
    Scenario: Parse vessel heading from HDG sentence
        When the NMEA0183 sentence is parsed
        Then this vessel heading is returned:
            | heading | 315.4°   |
            | source  | NMEA0183 |
    
    @MagneticVariation
    Scenario: Parse magnetic variation from HDG sentence
        When the NMEA0183 sentence is parsed
        Then this magnetic variation is returned:
            | whut? | blah |
