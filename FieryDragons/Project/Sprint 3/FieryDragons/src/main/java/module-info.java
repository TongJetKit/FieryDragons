module com.example.fierydragons {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    // Export the FXMLController package to javafx.fxml
    exports com.example.fierydragons.FXMLController to javafx.fxml;

    // Open the FXMLController class to javafx.fxml
    opens com.example.fierydragons.FXMLController to javafx.fxml;

    // You might need to open the package containing your FXMLController class to javafx.fxml as well
    // opens com.example.fierydragons to javafx.fxml;

    // Export other packages if needed
    exports com.example.fierydragons;
    opens com.example.fierydragons.Cards to javafx.fxml;
    exports com.example.fierydragons.Cards;
}
