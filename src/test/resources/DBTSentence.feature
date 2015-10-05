@NMEA0183
@Parse
@DBT
@WaterDepth

Feature: 
    As a vessel operator using a depth transducer that transmits NMEA0183 sentences 
    I want to have DBT (Depth Below Transducer) messages parsed correctly 
    So that my vessel doesn't hit bottom and cause damage or endanger my crew 
    
    # DBT messages
    # Fields
    # 1. Depth, feet
    # 2. f = feet
    # 3. Depth, meters
    # 4. m = meters
    # 5. Depth, fathoms
    # 6. F = fathoms
    # 7. Checksum

Scenario: Parse a water depth message from a DBT sentence
    Given this NMEA0183 sentence from an instrument: "IIDBT,020.8,f,006.3,M,003.4,F"
    When  the NMEA0183 sentence is parsed
    Then  this water depth message is returned:
        | water depth | source   |
        | 20.8 feet   | NMEA0183 |









