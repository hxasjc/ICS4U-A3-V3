/**
 * The module containing the project
 */
open module hxasjc.ics4ua3v3 {
    requires javafx.controls;
    requires javafx.fxml;

    requires beans;


    //opens hxasjc.ics4ua3v3 to javafx.fxml;
    exports hxasjc.ics4ua3v3;
    exports hxasjc.ics4ua3v3.monsters;
    //opens hxasjc.ics4ua3v3.monsters to javafx.fxml;
}