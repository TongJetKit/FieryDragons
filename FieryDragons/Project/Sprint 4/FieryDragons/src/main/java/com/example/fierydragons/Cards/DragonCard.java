package com.example.fierydragons.Cards;

import com.example.fierydragons.FieryDragonsGameBoard;
import com.example.fierydragons.Player;

import java.util.HashMap;

/**
 * Class that represents the dragon card that extends the Card class and implements the Flippable interface
 */
public abstract class DragonCard extends Card implements Flippable {

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
     * Getter to return the number of creatures on the dragon card
     * @return The number of creatures on the dragon card
     */
    public int getCreatureCount() {
        return creatureCount;
    }

    /**
     * Saves the current state of the card as a HashMap.
     *
     * Converts the card's attributes to string representations and stores them in a HashMap
     * with the following keys:
     *
     * cardSymbol: The symbol of the card as a string.
     * cardViewURL: The URL of the card's image.
     * creatureCount: The number of creatures on the card as a string.
     * cardType: The type of the card as a string.
     *
     * @return A HashMap containing the card's state.
     */
    @Override
    public HashMap<String, String> saveCard(){

        HashMap<String, String> CardMap = new HashMap<>();

        // Convert Symbol to String
        CardMap.put("cardSymbol", this.getCardSymbol().toString());

        // getCardView() returns the path of the image as a string
        CardMap.put("cardViewURL", this.getCardViewURL());

        // Convert Integer to String
        CardMap.put("creatureCount", Integer.toString(this.getCreatureCount()));

        // getCardType() returns the type name as a string
        CardMap.put("cardType", this.getCardType().toString());

        return CardMap;
    }
}