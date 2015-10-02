Meta:
@themes NMEA0183
@themes Parse
@themes TimeOfDayZulu
@themes GPSPosition
@themes PositionPrecision
@themes Altitude
@themes GGA

Narrative:
    In order to get an accurate fix on my location
    As a vessel operator using a GPS that transmits NMEA0183 sentences
    I want to have GGA (Global Positioning System Fix Data) messages parsed correctly 

Background:
Given this NMEA0183 sentence from an instrument: "25713092,V,GPS,GPGGA,130004,2531.3369,N,11104.4274,W,2,09,0.9,1.7,M,-31.5,M,,"


Scenario: Parse messages from a GGA sentence
When the NMEA0183 sentence is parsed
Then a time of day zulu message is returned
And a GPS position message is returned
And a position precision message is returned
And an altitude message is returned


Scenario: Parse a GPS position message from a GGA sentence
When the NMEA0183 sentence is parsed
Then this GPS position message is returned:
    | latitude      | longitude    | source   |
    | 25°31.3369'N  | 111°4.4274'W | NMEA0183 |
    

Scenario: Parse a position precision message from a GGA sentence
When the NMEA0183 sentence is parsed
Then this position precision message is returned:
    | position precision | source    |
    | 99.99 meters       | NMEA0183  |


Scenario: Parse an altitude message from a GGA sentence
When the NMEA0183 sentence is parsed
Then this altitude message is returned:
    | altitude     | source    |
    | 99.99 meters | NMEA0183  |


Scenario: Parse a time of day message from a GGA sentence
When the NMEA0183 sentence is parsed
Then this time of day message is returned:
    | time of day (GMT) | source   |
    | 13:00:04          | NMEA0183 |

