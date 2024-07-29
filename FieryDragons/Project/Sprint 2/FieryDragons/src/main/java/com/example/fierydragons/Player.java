package com.example.fierydragons;

import com.example.fierydragons.Cards.*;
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
    }

    /**
     * Method to create the player's token
     * @param caveSymbol: The Symbol of the cave card
     * @return A circle which is the player's token
     */
    private Circle makeToken(Symbol caveSymbol) {
        switch (caveSymbol){
            case Symbol.Bat:
                return new Circle(20, Color.BLACK);
            case Symbol.BabyDragon:
                return new Circle(20, Color.RED);
            case Symbol.Salamander:
                return new Circle(20, Color.ORANGE);
            case Symbol.Spider:
                return new Circle(20, Color.PURPLE);
            default:
                return new Circle(20, Color.WHITE);
        }
    }

    /**
     * Method to move the player
     * @param board: The GridPane
     * @param destination: The destination volcano card
     */
    public void move(GridPane board, VolcanoCard destination){
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

}