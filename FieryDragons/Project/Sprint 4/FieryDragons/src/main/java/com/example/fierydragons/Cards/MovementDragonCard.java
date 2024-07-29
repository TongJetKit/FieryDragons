package com.example.fierydragons.Cards;

import com.example.fierydragons.FieryDragonsGameBoard;
import com.example.fierydragons.Movement;
import com.example.fierydragons.Player;

public class MovementDragonCard extends DragonCard {
    /**
     * Constructor to create the Dragon card
     *
     * @param revealImageURL The dragon card's image URL
     * @param cardSymbol     The symbol of the card
     * @param creatureCount  The number of creatures on the dragon card
     * @param type           The card type
     */
    public MovementDragonCard(String revealImageURL, Symbol cardSymbol, int creatureCount, CardType type) {
        super(revealImageURL, cardSymbol, creatureCount, type);
    }

    public Movement flipCard(Player player, FieryDragonsGameBoard gameBoard){

        // get the dragon card's symbol
        Symbol dragonCardSymbol = getCardSymbol();

        // if the current location is -1, then the player is in his cave
        if(player.getCurrentLocation().getCardType().equals(CardType.CaveCard)){

            // if the dragon card's symbol is the same as the cave card's symbol then
            // move the player to the gridpane based on the number of creatures on the dragon card
            if(player.getCurrentLocation().getCardSymbol().equals(dragonCardSymbol)){
                Movement movement = gameBoard.movePlayerFromCave(gameBoard.getActivePlayer(), this);
                return movement;
            }
            else {
                System.out.println("The dragon card is a wrong Dragon Card or Pirate Dragon Card");
                return null; //end turn
            }

        }

        // if the dragon card's symbol has the same symbol with the symbol of the next location then
        // resolve it
        if(dragonCardSymbol.equals(gameBoard.getActivePlayer().getCurrentLocation().getCardSymbol())){
            // resolve movement here
            Movement movement = gameBoard.movePlayerOnBoard(gameBoard.getActivePlayer(), this);
            // return false cos the turn hasnt end yet
            System.out.println("The dragon card has the same symbol with the volcano card, continue your turn");
            return movement;
        }
        // if the dragon card is a Pirate Dragon Card then resolve it
        else if(dragonCardSymbol.equals(Symbol.PirateDragon)){
            // resolve movement here
            Movement movement = gameBoard.movePlayerOnBoard(gameBoard.getActivePlayer(), this);
            // return true because turn has end since it is a pirate dragon card
            System.out.println("The dragon card is a Pirate Dragon Card");
            return movement;
        }
        return null;
    };
}
