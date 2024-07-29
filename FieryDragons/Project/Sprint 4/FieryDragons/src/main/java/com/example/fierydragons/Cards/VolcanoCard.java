package com.example.fierydragons.Cards;

import java.util.HashMap;

/**
 * Class to represent the volcano card tile that extends the LocationCard class
 */
public class VolcanoCard extends LocationCard {

    /**
     * Constructor to create the Volcano Card Tile
     * @param cardSymbol The symbol of the card
     * @param cardViewURL The image url of the card
     * @param type The card type
     */
    public VolcanoCard(Symbol cardSymbol, String cardViewURL, CardType type) {
        super(cardSymbol, cardViewURL, type);
    }

    /**
     * Saves the current state of the VolcanoCard as a HashMap.
     *
     * @return A HashMap containing the VolcanoCard's state.
     */
    @Override
    public HashMap<String, String> saveCard(){

        HashMap<String, String> CardMap = new HashMap<>();

        // Convert Symbol to String
        CardMap.put("cardSymbol", this.getCardSymbol().toString());

        // Assuming getCardView() returns the path of the image as a string
        CardMap.put("cardViewURL", this.getCardViewURL());

        // Assuming getCardType() returns the type name as a string
        CardMap.put("cardType", this.getCardType().toString());

        return CardMap;
    }

    /**
     * Creates a VolcanoCard instance from a given map of attributes.
     *
     * @param map A HashMap containing the attributes of the VolcanoCard.
     * @return A VolcanoCard instance created from the map.
     */
    public static VolcanoCard fromMap(HashMap<String, String> map) {
        if (map == null) {
            return null; // Handle null input
        }

        // Extract values from the map
        Symbol symbol = null;
        String cardViewURL = null;
        CardType type = null;

        if (map.containsKey("cardSymbol")) {
            symbol = Symbol.valueOf(map.get("cardSymbol"));
        }
        if (map.containsKey("cardViewURL")) {
            cardViewURL = map.get("cardViewURL");
        }
        if (map.containsKey("cardType")) {
            type = CardType.valueOf(map.get("cardType"));
        }

        // Create and return the VolcanoCard instance
        return new VolcanoCard(symbol, cardViewURL, type);
    }


}
