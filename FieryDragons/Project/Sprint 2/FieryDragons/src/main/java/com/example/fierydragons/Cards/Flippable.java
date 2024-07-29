package com.example.fierydragons.Cards;

import com.example.fierydragons.FieryDragonsGameBoard;
import com.example.fierydragons.Player;

/**
 * Interface for flipping a card
 */
public interface Flippable {

    /**
     * Method to resolve the flipped card
     * @param player The active player
     * @param gameBoard The gameboard
     * @return true if flipped card has the same creature type as the next creature
     */
    boolean flipCard(Player player, FieryDragonsGameBoard gameBoard);
}
