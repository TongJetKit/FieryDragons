package com.example.fierydragons.Cards;

import com.example.fierydragons.FieryDragonsGameBoard;
import com.example.fierydragons.Player;

/**
 * Class that represents the dragon card that extends the Card class and implements the Flippable interface
 */
public class DragonCard extends Card implements Flippable {

    // The number of creatures that determines the move
    private final int creatureCount;

    /**
     * Constructor to create the Dragon card
     * @param revealImageURL The dragon card's image URL
     * @param cardSymbol The symbol of the card
     * @param creatureCount The number of creatures on the dragon card
     * @param type The card type
     */
    public DragonCard(String revealImageURL, Symbol cardSymbol, int creatureCount, CardType type) {
        super(cardSymbol,revealImageURL, type);
        this.creatureCount = creatureCount;
    }



    /**
     * Method to resolve the flipped card
     * @param player The active player
     * @param gameBoard The gameboard
     * @return true if flipped card has the same creature type as the next creature
     */
    public boolean flipCard(Player player, FieryDragonsGameBoard gameBoard){
        // get the next location volcano card
        int currentLocationIndex = gameBoard.getBoardLocation().indexOf(player.getCurrentLocation());
        LocationCard nextLocation = gameBoard.getBoardLocation().get((currentLocationIndex+1) % gameBoard.getBoardLocation().size());
        // get the symbol of the next location
        Symbol nextLocationSymbol =nextLocation.getCardSymbol();
        // get the dragon card's symbol
        Symbol dragonCardSymbol = getCardSymbol();
        // if the dragon card's symbol has the same symbol with the symbol of the next location then
        // resolve it
        if(nextLocationSymbol.equals(dragonCardSymbol)){
            // resolve movement here
            // return false cos the turn hasnt end yet
            return false;
        }
        // if the dragon card is a Pirate Dragon Card then resolve it
        if(dragonCardSymbol.equals(Symbol.PirateDragon)){
            // resolve movement here
            // return true because turn has end since it is a pirate dragon card
            return true;
        }
        // return true because turn has end since the dragon card doesnt match
        return true;
    };




}