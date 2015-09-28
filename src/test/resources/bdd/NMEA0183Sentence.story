Narrative: 
In order to ensure my vessel doesn't hit bottom and cause damage or endanger my crew 
As a vessel operator using a depth transducer that transmits NMEA0183 sentences 
I want to have NMEA0183 "Depth Below Transducer" messages translated correctly 

Scenario: Parse a single IIDBT message
Given this NMEA0183 sentence from an instrument: "25713116,V,SRL,IIDBT,020.8,f,006.3,M,003.4,F"
When the NMEA0183 sentence is parsed
Then the sentence is interpreted as a water depth message
And the water depth is 20.8 feet
