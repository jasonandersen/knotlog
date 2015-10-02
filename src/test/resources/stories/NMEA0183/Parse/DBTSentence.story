Meta:
@themes NMEA0183
@themes WaterDepth
@themes Parse
@themes DBT

Narrative: 
    In order to ensure my vessel doesn't hit bottom and cause damage or endanger my crew 
    As a vessel operator using a depth transducer that transmits NMEA0183 sentences 
    I want to have DBT (Depth Below Transducer) messages parsed correctly 


Scenario: Parse a water depth message from a DBT sentence
Given this NMEA0183 sentence from an instrument: "25713116,V,SRL,IIDBT,020.8,f,006.3,M,003.4,F"
When the NMEA0183 sentence is parsed
Then this water depth message is returned:
    | water depth | distance unit | source   |
    | 20.8        | feet          | NMEA0183 |









