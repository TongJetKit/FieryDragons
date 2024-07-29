package com.example.fierydragons.Cards;

import com.example.fierydragons.FieryDragonsGameBoard;
import javafx.animation.*;

import javafx.event.ActionEvent;

import javafx.event.EventHandler;

import javafx.scene.image.*;

import javafx.scene.layout.StackPane;

import javafx.scene.transform.*;

import javafx.util.Duration;


/**

 * FlippableImage

 *

 * Two images back to back that can be flipped 180 degrees, such as turning

 * over a playing card.

 *

 * @author Randy A. Meyer

 * March 2013

 *

 */

/**
ReferenceLink: https://www.facebook.com/notes/3493190774060695/?paipv=0&eav=AfbCGu0S6EG2FWLCjirU3bd7AFNxFYFqd9SOE_TlC4-I2WMU-_cahMawx_mjysu6b6s
**/


public class FlipCardPane extends StackPane {

    // The dragon card in the stack pane
    private DragonCard card;
    // The timeline for the flipping animation
    private Timeline flipForward, flipBackward;
    // the animation time for flipping
    private final double FLIP_SECS = 0.3;
    // the covered image URL
    private final  String coveredImageURL = "/com/example/fierydragons/assets/images/UnflipDragonCard.png";

    /**
     * Constructor for the stackpane to house the dragon card
     * @param card The dragon card
     */
    public FlipCardPane(DragonCard card){
        // set the rotation on Y axis
        this.setRotationAxis(Rotate.Y_AXIS);
        final ImageView back = card.getCardView();
        final ImageView front = new ImageView(new Image(getClass().getResourceAsStream(coveredImageURL)));
        Rotate backRot = new Rotate(180, Rotate.Y_AXIS);
        backRot.setPivotX(back.prefWidth(USE_PREF_SIZE)/2);
        back.getTransforms().add(backRot);
        this.getChildren().addAll(back, front);
        this.card = card;

        // create the timeline for flipping forward
        flipForward = new Timeline(

                new KeyFrame(

                        Duration.ZERO,

                        new KeyValue(this.rotateProperty(), 0d)),

                new KeyFrame(

                        Duration.seconds(FLIP_SECS/2),

                        new EventHandler<ActionEvent>(){

                            @Override

                            public void handle(ActionEvent t) {

                                back.toFront();

                            }},

                        new KeyValue(this.rotateProperty(), 90d)),

                new KeyFrame(

                        Duration.seconds(FLIP_SECS),

                        new KeyValue(this.rotateProperty(), 180d)));


        // create the timeline for flipping backward
        flipBackward = new Timeline(

                new KeyFrame(

                        Duration.ZERO,

                        new KeyValue(this.rotateProperty(), 180d)),

                new KeyFrame(

                        Duration.seconds(FLIP_SECS/2),

                        new EventHandler<ActionEvent>() {

                            @Override

                            public void handle(ActionEvent t) {

                                front.toFront();

                            }},

                        new KeyValue(this.rotateProperty(), 90d)),

                new KeyFrame(

                        Duration.seconds(FLIP_SECS),

                        new KeyValue(this.rotateProperty(), 0d)));

    }


    /**
     * Method to do the flipping animation
     * @param gameBoard The gameboard
     * @return true if the card flipped is the card with the right creature on it
     *         false if the card flipped has the wrong creature on it or a pirate dragon card
     */
    public boolean flip(FieryDragonsGameBoard gameBoard) {

        boolean isNextPositionOccupied = false;
        // play the flip animation to show the creature
        flipForward.play();
        // call the method to resolve when the card is flipped
        boolean endTurn = card.flipCard(gameBoard.getActivePlayer(), gameBoard);
        // if player is in cave, move the player from the cave
//        if (gameBoard.getActivePlayer().getCurrentLocation() instanceof CaveCard) {
//            gameBoard.movePlayerFromCave(gameBoard.getActivePlayer(), card);
//        }
//        else {
//            isNextPositionOccupied = gameBoard.movePlayerOnBoard(gameBoard.getActivePlayer(), card);
//            endTurn = endTurn && isNextPositionOccupied;
//        }
        // return the results for flipping the card
        return endTurn;

    }

    /**
     * Method to flip the card back
     */
    public void unflip(){
        flipBackward.play();
    }

}
