Feature: Knotlog directories
    As a Knotlog user
    I want my Knotlog data stored in a specific directory
    So that my data is stored in a consistent place
    
    #Scenario: Knotlog directory from preferences
        #Given this directory exists: "/path/from/preferences"
        #And my preferences have this directory "/path/from/preferences" as my Knotlog directory
        #Then my Knotlog directory is "/path/from/preferences"
        
    #Scenario: Knotlog directory in user home directory
        #Given my preferences have no entry for my Knotlog directory
        #Then my Knotlog directory is a directory called "knotlog" in my user home directory
        
    #Scenario: Database directory from preferences
        #Given my preferences have this directory "/path/for/data" as my data directory
        #And my Knotlog directory is "/path/for/knotlog/directory"
        #Then my data directory is "/path/for/data"
        
    #Scenario: Database directory with nothing in preferences
        #Given my preferences have no entry for my data directory
        #And my Knotlog directory is "/path/for/knotlog"
        #Then my data directory is "/path/for/knotlog/data"
