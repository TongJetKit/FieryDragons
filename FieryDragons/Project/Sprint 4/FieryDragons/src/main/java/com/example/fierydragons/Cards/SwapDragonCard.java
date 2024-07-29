package com.example.fierydragons.Cards;

import com.example.fierydragons.FieryDragonsGameBoard;
import com.example.fierydragons.Movement;
import com.example.fierydragons.Player;

public class SwapDragonCard extends DragonCard {
    /**
     * Constructor to create the Dragon card
     *
     * @param revealImageURL The dragon card's image URL
     * @param cardSymbol     The symbol of the card
     * @param creatureCount  The number of creatures on the dragon card
     * @param type           The card type
     */
    public SwapDragonCard(String revealImageURL, Symbol cardSymbol, int creatureCount, CardType type) {
        super(revealImageURL, cardSymbol, creatureCount, type);
    }

    @Override
    public Movement flipCard(Player activePlayer, FieryDragonsGameBoard gameBoard) {

        Movement movement = gameBoard.swapPlayers(activePlayer);
        return movement;

    }
}
