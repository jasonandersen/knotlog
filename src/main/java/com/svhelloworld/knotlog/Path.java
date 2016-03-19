package com.svhelloworld.knotlog;

/**
 * Path constants for the application.
 */
public class Path {

    /*
     * FXML paths
     */
    private static final String BASE_PACKAGE = "/com/svhelloworld/knotlog/ui/";
    public static final String FXML_ROOT_LAYOUT = BASE_PACKAGE + "root/RootLayout.fxml";
    public static final String FXML_CURRENT_STATE = BASE_PACKAGE + "currentstate/CurrentState.fxml";
    public static final String FXML_RAW_NMEA0183 = BASE_PACKAGE + "raw/RawNMEA0183.fxml";

    /*
     * NMEA0183 simulation backing data
     */
    public static final String SIMULATION_FEED = "data/GarminDiagFeed.csv";

}
