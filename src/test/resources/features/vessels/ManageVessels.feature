@Vessels
Feature: View all vessels
    As someone with multiple vessels
    I want to view all the vessels I've registered
    So that I can choose which vessel to attach to vessel messages
    
    Scenario: Add a new vessel
        Given I have no vessels
        When  I add this new vessel:
            | s/v hello world    | SAILBOAT   |
        And   I view all of my vessels
        Then  I see these vessels:
            | s/v hello world    | SAILBOAT   |
    
    Scenario: Edit a vessel
        Given I have these vessels:
            | s/v hello world    | SAILBOAT   |
        When I change the name of vessel "s/v hello world" to "s/v Monkey Nutz"
        Then I see these vessels:
            | s/v Monkey Nutz    | SAILBOAT   |
    
    Scenario: View all vessels
        Given I have these vessels:
            | s/v hello world    | SAILBOAT   |
            | BMW R1200GS        | MOTORCYCLE |
            | Ford F250          | AUTOMOBILE |
        When I view all of my vessels
        Then I see these vessels:
            | s/v hello world    | SAILBOAT   |
            | BMW R1200GS        | MOTORCYCLE |
            | Ford F250          | AUTOMOBILE |