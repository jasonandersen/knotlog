@UnitConversion
@WaterDepth
Feature: Show water depth in the correct unit of measure based on preference
    As a vessel operator
    I want water depth displayed in my unit of choice
    So that I don't have to do calculations in my head
    
    Scenario Outline: Show water depth in different unit types
        Given I have selected "<unit>" as my preferred water depth unit
        And   this NMEA0183 sentence from an instrument: "<sentence>"
        When  the NMEA0183 sentence is parsed
        Then  I see this message: "<message>"

        Examples:
        | unit    | sentence                       | message                 |
        | feet    | $IIDBT,020.8,f,006.3,M,003.4,F | water depth 20.8 ft     |
        | meters  | $IIDBT,020.8,f,006.3,M,003.4,F | water depth 6.3 mtrs    |
        | fathoms | $IIDBT,020.8,f,006.3,M,003.4,F | water depth 3.4 ftms    |

    Scenario: Ensure conversion is calculated, not just pulled from the NMEA0183 sentence
        Given I have selected "fathoms" as my preferred water depth unit
        And   this NMEA0183 sentence from an instrument: "$IIDBT,020.8,f,006.3,M,999.9,F"
        When  the NMEA0183 sentence is parsed
        Then  I see this message: "water depth 3.4 fathoms" 
