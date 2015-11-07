######################### Wind Speed Over Time ######################################
# 
# Display wind speeds over time. The user can specify a time period
# 
#####################################################################################
@WindSpeed
Feature: Wind speed over time
    As a boat operator
    I want to see a history of wind speed over time
    So I can guage weather conditions
    
    Scenario: Last hour of wind speed
        Given 24 hours of instrument data
        And I have selected "knots" as my preferred wind speed unit
        When I select the last hour of wind speed
        Then I see these wind speed messages:
        # put test data wind speeds in here!
        