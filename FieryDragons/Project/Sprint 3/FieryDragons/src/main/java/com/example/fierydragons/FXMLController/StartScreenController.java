package com.example.fierydragons.FXMLController;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.net.URL;

public class StartScreenController {

    // Switching scene declarations
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button startButton; // Start Button

    @FXML
    private Button exitButton; // Exit Button

    @FXML
    private Button tutorialButton; // Tutorial Button

    @FXML
    private ImageView welcomeText; // The welcome text

//    @FXML
//    private MediaPlayer mediaPlayer; // A media player

    @FXML
    private AnchorPane scenePane; // the anchorpane of the start screen

    /***
     * The method to initialize the start screen
     */
    public void initialize() {
        animateWelcomeText();
        spinAnimation(startButton);
        spinAnimation(exitButton);
        spinAnimation(tutorialButton);
        //playBackgroundMusic();
    }

    /**
     * The Method to animate the Welcome Text
     */
    private void animateWelcomeText() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), welcomeText);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    /**
     * The Method to create the spin animation for a button
     * @param btn: A button component
     */
    private void spinAnimation(Button btn) {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), btn);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(1);
        rotateTransition.play();
    }

    /**
     * Method to play a background music
     */
//    private void playBackgroundMusic() {
//        try {
//            URL musicFileUrl = getClass().getResource("/com/example/fierydragons/assets/music/background.mp3");
//            if (musicFileUrl != null) {
//                Media media = new Media(musicFileUrl.toExternalForm());
//                mediaPlayer = new MediaPlayer(media);
//                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Play indefinitely
//                mediaPlayer.play();
//            } else {
//                System.out.println("Cannot find file: /music/background.mp3");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Error playing background music.");
//        }
//    }

    /**
     * A method to exit the game using the exit button
     * @param event: The action event that will exit the game
     */
    public void exitGame(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Game");
        alert.setHeaderText("Are you sure you want to exit the game?");
        alert.setContentText("Press OK to exit the game.");

        // see if they really wanna exit the game or not
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) scenePane.getScene().getWindow();
            System.out.println("Game exited.");
            stage.close();
        }

    }

    /**
     * The method to change to the game screen
     * @param event: An action event
     * @throws IOException
     */
    public void switchToGameScreen(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        AnchorPane root = FXMLLoader.load(getClass().getResource("/com/example/fierydragons/fiery-dragons-game.fxml"));
        Scene scene = new Scene(root,1280, 720);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();


    }

}

