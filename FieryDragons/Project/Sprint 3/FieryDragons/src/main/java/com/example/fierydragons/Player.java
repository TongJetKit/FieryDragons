package com.example.fierydragons;

import com.example.fierydragons.Cards.*;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Class that represents the player
 */
public class Player {
    // The player's current location
    private LocationCard currentLocation;
    // The player's number
    private int number;
    // The player's token
    private Circle token;
    // The player's starting cave
    private CaveCard cave;
    // The player's move count
    private int moveCount = 0;

    /**
     * Constructor for the Player class
     * @param location the player's location
     * @param number the player's number
     * @param cave the player's starting cave
     */
    public Player(LocationCard location, int number, CaveCard cave) {
        this.currentLocation = location;
        this.number = number;
        this.cave = cave;
        this.token = makeToken(cave.getCardSymbol());

        unhighlight();
    }

    /**
     * Method to create the player's token
     * @param caveSymbol: The Symbol of the cave card
     * @return A circle which is the player's token
     */
    private Circle makeToken(Symbol caveSymbol) {

        if(caveSymbol == Symbol.Bat){
            return new Circle(10, Color.PURPLE);
        }
        else if(caveSymbol == Symbol.BabyDragon){
            return new Circle(10, Color.GREEN);
        }
        else if(caveSymbol == Symbol.Salamander){
            return new Circle(10, Color.ORANGE);
        }
        else if(caveSymbol == Symbol.Spider){
            return new Circle(10, Color.RED);
        }
        else {
            return new Circle(20, Color.BLACK);
        }

    }


    public void setCurrentLocation(LocationCard newLocation) {
        this.currentLocation = newLocation;
        // When setting a new location, also update the row and column of the player
        this.token.setLayoutX(newLocation.getCurrentColumn() * 75);  // cell width
        this.token.setLayoutY(newLocation.getCurrentRow() * 75);  // cell height
    }


    /**
     * Getter to get the player's current location
     * @return The player's current location
     */
    public LocationCard getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Getter to get the player's cave
     * @return The player's cave
     */
    public CaveCard getCave() {
        return cave;
    }

    /**
     * Getter to get the player's token
     * @return The player's token
     */
    public Circle getToken() {
        return token;
    }

    /**
     * Getter to get the player's number
     * @return The player's number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Highlights the player's token
     */
    public void highlight() {
        this.token.setStroke(Color.ANTIQUEWHITE); // Highlight with a gold border
        this.token.setStrokeWidth(4); // Make the border thicker
    }

    /**
     * Unhighlights the player's token
     */
    public void unhighlight() {
        // colour of the token
        Color color = (Color) this.token.getFill();

        this.token.setStroke(color.darker());
        this.token.setStrokeWidth(2);
    }

    public int getPlayerMoveCount() {
        return moveCount;
    }

    public void setPlayerMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }
}