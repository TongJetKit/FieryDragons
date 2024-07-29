module com.fierydragons.fierydragons {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.sql;
    requires java.desktop;


    opens com.example.fierydragons to javafx.fxml;
    exports com.example.fierydragons;
    exports com.example.fierydragons.FXMLController;
    opens com.example.fierydragons.FXMLController to javafx.fxml;
    exports com.example.fierydragons.Cards;
    opens com.example.fierydragons.Cards to javafx.fxml;
}