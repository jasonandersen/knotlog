Feature: Error handling
    As a Knotlog user
    I want to see an error message dialog when an uncaught exception happens
    So the application doesn't just crash and I can report the error

    Scenario: Exception on the event bus
        When an uncaught exception occurs on the event bus
        Then I see
        
    #Scenario: Exception on a background thread
    
    #Scenario: Exception on the JavaFX thread
