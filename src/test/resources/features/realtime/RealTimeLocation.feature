@RealTime
Feature: Real time location
    As a vessel operator
    I want to see my location updated in real time as I move
    So that I always know where the hell I'm at
    
    Scenario: 
        Given I'm connected to a real time NMEA0183 source
        When I operate my vessel in motion for five minutes
        Then I see my location change like this: