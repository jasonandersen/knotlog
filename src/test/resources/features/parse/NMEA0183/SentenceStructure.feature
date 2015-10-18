@NMEA0183
@Parse

Feature: NMEA0183 sentence structure
    As a vessel operator using instruments that transmit NMEA0183
    I want to ensure common NMEA0183 sentence structures as parsed correctly
    So I can see that instrument data from this application
    
    Scenario: Handle two character talker ID and three character tag as first field without dollar sign
        Given this NMEA0183 sentence from an instrument: "IIDBT,021.8,f,006.6,M,003.6,F"
        When the NMEA0183 sentence is parsed
        Then a proper vessel message was found
        And there are no unrecognized messages
        
    Scenario: Handle sentences that start with a dollar sign
        Given this NMEA0183 sentence from an instrument: "$IIDBT,021.8,f,006.6,M,003.6,F"
        When the NMEA0183 sentence is parsed
        Then a proper vessel message was found
        And there are no unrecognized messages
    
    Scenario: Handle Garmin fields in front of the tag field
        Given this NMEA0183 sentence from an instrument: "29015947,V,SRL,$IIDBT,021.8,f,006.6,M,003.6,F"
        When the NMEA0183 sentence is parsed
        Then a proper vessel message was found
        And there are no unrecognized messages
    
    Scenario: Handle trailing space
        Given this NMEA0183 sentence from an instrument: "$IIDBT,021.8,f,006.6,M,003.6,F "
        When the NMEA0183 sentence is parsed
        Then a proper vessel message was found
        And there are no unrecognized messages
    
    Scenario: Handle trailing empty field
        Given this NMEA0183 sentence from an instrument: "$IIDBT,021.8,f,006.6,M,003.6,"
        When the NMEA0183 sentence is parsed
        Then a proper vessel message was found
        And there are no unrecognized messages
    
    Scenario: Handle properly formatted but unrecognized sentences
        Given this NMEA0183 sentence from an instrument: "XXXXX,I,LIKE,MONKEYS"
        When the NMEA0183 sentence is parsed
        Then the sentence is not recognized
    
    Scenario: Handle improperly formatted sentences
        Given this NMEA0183 sentence from an instrument: "I like monkeys."
        When the NMEA0183 sentence is parsed
        Then the sentence is malformed
        
    Scenario: Handle properly formatted sentence with insufficient data in fields
        Given this NMEA0183 sentence from an instrument: "$IIDBT, , , , "
        When the NMEA0183 sentence is parsed
        Then the sentence has invalid sentence fields
        
    Scenario: Handle properly formatted sentence with insufficient fields
        Given this NMEA0183 sentence from an instrument: "$IIDBT"
        When the NMEA0183 sentence is parsed
        Then the sentence has invalid sentence fields
        
    Scenario: Handle empty sentence
        Given this NMEA0183 sentence from an instrument: ""
        When the NMEA0183 sentence is parsed
        Then the sentence is malformed
        
