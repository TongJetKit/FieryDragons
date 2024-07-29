package com.example.fierydragons.Cards;

import com.example.fierydragons.Player;

import java.util.HashMap;

/**
 * Class to represent the card that acts as the location where the player can resides in that extends the Card class
 */
public abstract class LocationCard extends Card{

    // to represent the links between the caveCards and the volcanoCards
    private LocationCard linkedCard = null;
    // the row of the card in the board
    private int currentRow;
    // the column of the card in the board
    private int currentColumn;
    // the player on it
    private Player currentPlayer;

    /**
     * Constructor for the Location Card class
     * @param cardSymbol The symbol on the card
     * @param cardViewURL The image URL of the card image
     * @param type The Card type
     */
    public LocationCard(Symbol cardSymbol, String cardViewURL, CardType type) {
        super(cardSymbol, cardViewURL, type);
        // initially there are no players on it
        currentPlayer = null;
    }

    /**
     * Setter to set the linked cave of the card
     * @param linkedCard The linked cave of the card
     */
    public void setLinkedCard(LocationCard linkedCard) {
        this.linkedCard = linkedCard;
    }

    /**
     * Method to check if the card has a linked cave
     * @return true if it has a linked cave; false otherwise
     */
    public boolean hasLinkedCard() {
        if(linkedCard != null){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Getter to return the linked cave card
     * @return The linked cave card
     */
    public LocationCard getLinkedCard() {
        return linkedCard;
    }



    /**
     * Setter to set the card's row in the GridPane
     * @param currentRow The row index of the card in the GridPane
     */
    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    /**
     * Setter to set the card's column in the GridPane
     * @param currentColumn The column index of the card in the GridPane
     */
    public void setCurrentColumn(int currentColumn) {
        this.currentColumn = currentColumn;
    }

    /**
     * Method to check if the card has a player on it
     * @return true if it has a player on it; false otherwise
     */
    public boolean hasPlayer(){
        if(currentPlayer != null){
            return true;
        }
        return false;
    }

    /**
     * Getter to return the current column
     * @return
     */
    public int getCurrentColumn() {
        return currentColumn;
    }

    /**
     * Getter to return the current row
     * @return
     */
    public int getCurrentRow() {
        return currentRow;
    }

    public void removeCurrentPlayer(){
        currentPlayer = null;
    }

    // set current player
    public void setCurrentPlayer(Player player){
        currentPlayer = player;
    }
}
