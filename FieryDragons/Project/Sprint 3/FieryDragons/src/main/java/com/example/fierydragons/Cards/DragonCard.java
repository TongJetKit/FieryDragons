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
        // get the dragon card's symbol
        Symbol dragonCardSymbol = getCardSymbol();

        // if the current location is -1, then the player is in his cave
        if(player.getCurrentLocation().getCardType().equals(CardType.CaveCard)){
            // if the dragon card's symbol is the same as the cave card's symbol then
            // move the player to the gridpane based on the number of creatures on the dragon card
            if(player.getCave().getCardSymbol().equals(dragonCardSymbol)){
                gameBoard.movePlayerFromCave(gameBoard.getActivePlayer(), this);
                return false;
            }
            else {
                System.out.println("The dragon card is a wrong Dragon Card or Pirate Dragon Card");
                return true;
            }

        }

        // handle current location index
        // get the next location card
        LocationCard nextLocation = gameBoard.getBoardLocation().get((currentLocationIndex+1) % gameBoard.getBoardLocation().size());
        // get the symbol of the next location
        Symbol nextLocationSymbol = nextLocation.getCardSymbol();
        boolean isNextPositionOccupied = false;
        // if the dragon card's symbol has the same symbol with the symbol of the next location then
        // resolve it
        if(dragonCardSymbol.equals(gameBoard.getActivePlayer().getCurrentLocation().getCardSymbol())){
            // resolve movement here
            isNextPositionOccupied = gameBoard.movePlayerOnBoard(gameBoard.getActivePlayer(), this);
            // return false cos the turn hasnt end yet
            System.out.println("The dragon card has the same symbol with the volcano card, continue your turn");
            return isNextPositionOccupied;
        }
        // if the dragon card is a Pirate Dragon Card then resolve it
        else if(dragonCardSymbol.equals(Symbol.PirateDragon)){
            // resolve movement here
            gameBoard.movePlayerOnBoard(gameBoard.getActivePlayer(), this);
            // return true because turn has end since it is a pirate dragon card
            System.out.println("The dragon card is a Pirate Dragon Card");
            return true;
        }
        return true;
    };

    /**
     * Getter to return the number of creatures on the dragon card
     * @return The number of creatures on the dragon card
     */
    public int getCreatureCount() {
        return creatureCount;
    }


}