package com.example.fierydragons;

import com.example.fierydragons.Cards.*;
import com.google.gson.Gson;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Pair;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

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
    // The player's free flip status
    private boolean freeFlipUsed = false;
    // The player's current move speed
    private int moveSpeed = 1;


    /**
     * Constructor for the Player class
     *
     * @param number the player's number
     * @param cave   the player's starting cave
     */
    public Player(int number, CaveCard cave, Boolean freeFlipUsed) {
        this.currentLocation =null;
        this.number = number;
        this.cave = cave;
        this.token = makeToken(cave.getCardSymbol());
        this.freeFlipUsed = freeFlipUsed;

        unhighlight();
    }

    /**
     * Constructor for the Player class
     *
     * @param location the player's location
     * @param number   the player's number
     * @param cave     the player's starting cave
     */
    public Player(LocationCard location, int number, CaveCard cave) {
        this.currentLocation = location;
        this.number = number;
        this.cave = cave;
        this.token = makeToken(cave.getCardSymbol());

        unhighlight();
    }


    /**
     * Method to get whether the player has used the free flip
     */
    public boolean isFreeFlipUsed() {
        return freeFlipUsed;
    }

    /**
     * Method to set whether the player has used the free flip
     * @param freeFlipUsed
     */
    public void setFreeFlipUsed(boolean freeFlipUsed) {
        this.freeFlipUsed = freeFlipUsed;
    }

    /**
     * Method to create the player's token
     *
     * @param caveSymbol: The Symbol of the cave card
     * @return A circle which is the player's token
     */
    private Circle makeToken(Symbol caveSymbol) {

        if (caveSymbol == Symbol.Bat) {
            return new Circle(10, Color.BLUEVIOLET);
        } else if (caveSymbol == Symbol.BabyDragon) {
            return new Circle(10, Color.GREEN);
        } else if (caveSymbol == Symbol.Salamander) {
            return new Circle(10, Color.ORANGE);
        } else if (caveSymbol == Symbol.Spider) {
            return new Circle(10, Color.RED);
        } else {
            return new Circle(20, Color.BLACK);
        }

    }

    /**
     * Setter to set the player's current location
     * @param newLocation
     */
    public void setCurrentLocation(LocationCard newLocation) {
        this.currentLocation = newLocation;
        // When setting a new location, also update the row and column of the player
//        this.token.setLayoutX(newLocation.getCurrentColumn() * 75);  // cell width
//        this.token.setLayoutY(newLocation.getCurrentRow() * 75);  // cell height
    }


    /**
     * Getter to get the player's current location
     *
     * @return The player's current location
     */
    public LocationCard getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Getter to get the player's cave
     *
     * @return The player's cave
     */
    public CaveCard getCave() {
        return cave;
    }

    /**
     * Getter to get the player's token
     *
     * @return The player's token
     */
    public Circle getToken() {
        return token;
    }

    /**
     * Getter to get the player's number
     *
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

    /**
     * Saves the current state of the player.
     *
     * This method stores the player's number, current location (row and column),
     * move count, cave symbol, and whether a free flip has been used into a
     * HashMap for later retrieval.
     *
     * If the player is currently in a cave, the row and column values will be set
     * to -1.
     *
     * @return A HashMap containing the player's state with the following keys:
     * - "playerNumber": The player's number as a string.
     * - "row": The current row of the player's location, or -1 if in a cave.
     * - "column": The current column of the player's location, or -1 if in a cave.
     * - "moveCount": The player's move count as a string.
     * - "caveSymbol": The symbol of the cave card the player is associated with.
     * - "freeFlipUsed": A boolean indicating whether the free flip has been used, as a string.
     */
    public HashMap<String, String> savePlayer() {
        HashMap<String, String> locationMap = new HashMap<>();
        locationMap.put("playerNumber", Integer.toString(number));

        // if in cave, then the row and column are -1
        if (currentLocation.getCardType().equals(CardType.CaveCard)) {
            locationMap.put("row", "-1");
            locationMap.put("column", "-1");
        } else {
            locationMap.put("row", Integer.toString(currentLocation.getCurrentRow()));
            locationMap.put("column", Integer.toString(currentLocation.getCurrentColumn()));
        }
        locationMap.put("moveCount",Integer.toString(moveCount));
        locationMap.put("caveSymbol", cave.getCardSymbol().toString());
        locationMap.put("freeFlipUsed",Boolean.toString(freeFlipUsed));
        locationMap.put("moveSpeed",Integer.toString(moveSpeed));
        return locationMap;
    }

    /**
     * Gets the player's move count.
     * @return
     */
    public int getPlayerMoveCount() {
        return moveCount;
    }

    /**
     * Sets the player's move count.
     *
     * @param moveCount The new move count for the player.
     */
    public void setPlayerMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }

    /**
     * Sets the player's move count.
     *
     * @param moveCount The new move count for the player.
     */
    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }

    /**
     * Sets the player's move speed.
     *
     * @param moveSpeed The new move speed for the player.
     */
    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    /**
     * Gets the player's move speed.
     */
    public int getMoveSpeed() {
         return this.moveSpeed;
    }

}