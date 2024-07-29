module com.example.fierydragons {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires com.google.gson;

    // Export the FXMLController package to javafx.fxml
    exports com.example.fierydragons.FXMLController to javafx.fxml;

    // Open the FXMLController class to javafx.fxml
    opens com.example.fierydragons.FXMLController to javafx.fxml;

    // Open your package to Gson
    opens com.example.fierydragons to com.google.gson;

    // You might need to open the package containing your FXMLController class to javafx.fxml as well
    // opens com.example.fierydragons to javafx.fxml;

    // Export other packages if needed
    exports com.example.fierydragons;
    opens com.example.fierydragons.Cards to javafx.fxml, com.google.gson;
    exports com.example.fierydragons.Cards;

}

