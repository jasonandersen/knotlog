@Parse
@GPX
Feature: GPX file import
    As a user of GPX files
    I want to be able to import a track from a GPX file
    So I can retain history of my journeys
    
    Background:
        Given this GPX file:
"""
<?xml version="1.0" encoding="UTF-8" standalone="no" ?>
<gpx xmlns="http://www.topografix.com/GPX/1/1"
        xmlns:gpxx="http://www.garmin.com/xmlschemas/GpxExtensions/v3"
        xmlns:gpxtpx="http://www.garmin.com/xmlschemas/TrackPointExtension/v1"
        xmlns:badelf="http://bad-elf.com/xmlschemas"
        version="1.1"
        creator="Bad Elf GPS Pro 2.0.48"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd http://www.garmin.com/xmlschemas/GpxExtensions/v3 http://www.garmin.com/xmlschemas/GpxExtensionsv3.xsd http://www.garmin.com/xmlschemas/TrackPointExtension/v1 http://www.garmin.com/xmlschemas/TrackPointExtensionv1.xsd http://bad-elf.com/xmlschemas http://bad-elf.com/xmlschemas/GpxExtensionsV1.xsd">
    <metadata>
        <extensions>
            <!-- Name -->
            <badelf:modelName>Bad Elf GPS Pro</badelf:modelName>
            <!-- Ser No -->
            <badelf:modelSerialNumber>120048</badelf:modelSerialNumber>
            <!-- Model -->
            <badelf:modelNumber>BE-GPS-2200</badelf:modelNumber>
            <!-- Hardware Revision -->
            <badelf:modelHardwareRevision>2.0.0</badelf:modelHardwareRevision>
            <!-- Firmware Revision -->
            <badelf:modelFirmwareRevision>2.0.48</badelf:modelFirmwareRevision>
        </extensions>
    </metadata>
    <trk>
        <name>Simple GPX Track</name>
        <trkseg>
            <trkpt lat="39.4375" lon="-123.805077"><ele>20.2</ele><time>2013-05-25T14:23:49Z</time><hdop>0.9</hdop></trkpt>
            <trkpt lat="39.437637" lon="-123.806"><ele>28.7</ele><time>2013-05-25T14:24:19Z</time><hdop>0.9</hdop></trkpt>
            <trkpt lat="39.433102" lon="-123.806175"><ele>33.2</ele><time>2013-05-25T14:24:50Z</time><hdop>0.9</hdop></trkpt>
        </trkseg>
    </trk>
</gpx>
"""
    And the GPX file is parsed
        
    Scenario: GPX creator
        Then the track creator is "Bad Elf GPS Pro 2.0.48"
        
    Scenario: Track name
        Then the track name is "Simple GPX Track"
        
    Scenario: Track segment and points
        Then the track has 1 track segment
        And the track has these track points:
            | location                 | date       | time     | time zone |
            | 39°26.25'N 123°48.305'W  | 2013-05-25 | 14:23:49 | GMT       |
            | 39°26.258'N 123°48.36'W  | 2013-05-25 | 14:24:19 | GMT       | 
            | 39°25.986'N 123°48.371'W | 2013-05-25 | 14:24:50 | GMT       |
            
    Scenario: Meta data
        Then the track has this meta data:
            | badelf:modelName             | Bad Elf GPS Pro |
            | badelf:modelSerialNumber     | 120048          |
            | badelf:modelNumber           | BE-GPS-2200     |
            | badelf:modelHardwareRevision | 2.0.0           |
            | badelf:modelFirmwareRevision | 2.0.48          |


