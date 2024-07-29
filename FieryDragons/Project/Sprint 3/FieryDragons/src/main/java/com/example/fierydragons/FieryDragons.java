package com.example.fierydragons;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FieryDragons extends Application {
    /**
     * Start method to start the game
     * @param stage The stage
     * @throws IOException The exception
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FieryDragons.class.getResource("game-start-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720); // set the scene with 1200 x 720 size
        stage.setTitle("Fiery Dragons"); // set the title of the window stage
        stage.setScene(scene);
        stage.centerOnScreen(); // in the center of the screen
        stage.setResizable(false); // make it not resizable
        stage.show();
    }

    /**
     * Main method to run the game
     * @param args The arguments
     */
    public static void main(String[] args) {
        launch();
    }
}