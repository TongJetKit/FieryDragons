package com.example.fierydragons.Cards;

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
    public CaveCard(String caveImageURL, Symbol caveCreature, CardType type) {
        super(caveCreature ,caveImageURL, type);
    }


}
