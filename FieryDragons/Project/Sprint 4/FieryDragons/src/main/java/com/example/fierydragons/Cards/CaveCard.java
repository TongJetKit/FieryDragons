package com.example.fierydragons.Cards;

import javafx.scene.layout.StackPane;

import java.util.HashMap;

/**
 * Class that represents the Cave Card that extends the Location Card class
 */
public class CaveCard extends LocationCard {

    /**
     * Constructor to create the cave card
     * @param caveImageURL: The cave card image string URL
     * @param caveCreature: The creature on the cave card
     * @param type: The card type
     */

    private StackPane stackPane;

    public CaveCard(String caveImageURL, Symbol caveCreature, CardType type) {
        super(caveCreature ,caveImageURL, type);
    }

    // get stackpane
    public StackPane getStackPane() {
        return stackPane;
    }


    public void setStackPane(StackPane newStackPane) {
        stackPane = newStackPane;
    }

    /**
     * Saves the current state of the card as a HashMap.
     *
     * Converts the card's attributes to string representations and stores them in a HashMap
     * with the following keys:
     *
     * cardSymbol: The symbol of the card as a string.
     * cardViewURL: The URL of the card's image.
     * ardType: The type of the card as a string.
     *
     * @return A HashMap containing the card's state.
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
}
