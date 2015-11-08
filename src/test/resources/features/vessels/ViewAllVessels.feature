@Vessels
Feature: View all vessels
    As someone with multiple vessels
    I want to view all the vessels I've registered
    So that I can choose which vessel to attach to vessel messages
    
    Scenario: view all vessels
        Given I have these vessels:
            | s/v hello world    | SAILBOAT   |
            | BMW R1200GS        | MOTORCYCLE |
            | Ford F250          | AUTOMOBILE |
        When I view all of my vessels
        Then I see these vessels:
            | s/v hello world    | SAILBOAT   |
            | BMW R1200GS        | MOTORCYCLE |
            | Ford F250          | AUTOMOBILE |