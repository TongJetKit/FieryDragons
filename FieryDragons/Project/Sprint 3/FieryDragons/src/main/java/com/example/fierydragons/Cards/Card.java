package com.example.fierydragons.Cards;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Abstract class that represents the card of the game
 */
public abstract class Card {
    // The symbol of the card
    private Symbol cardSymbol;
    // The imageview of the card
    private ImageView cardView;
    // The card type
    private CardType cardType;

    /**
     * Constructor to create the card
     * @param cardSymbol The symbol of the card
     * @param cardViewURL The image url of the card
     * @param type The card type
     */
    public Card(Symbol cardSymbol, String cardViewURL, CardType type) {
        setCardSymbol(cardSymbol);
        setCardView(cardViewURL);
        setCardType(type);
    }

    /**
     * Getter to return the card type
     * @return The card type
     */
    public CardType getCardType() {
        return cardType;
    }

    /**
     * Setter to set the card type
     * @param cardType The card type
     */
    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    /**
     * Getter to return the card symbol
     * @return The card symbol
     */
    public Symbol getCardSymbol() {
        return cardSymbol;
    }

    /**
     * Setter to set the card symbol
     * @param cardSymbol The card symbol
     */
    public void setCardSymbol(Symbol cardSymbol) {
        this.cardSymbol = cardSymbol;
    }

    /**
     * Getter to return the card image view
     * @return The card image view
     */
    public ImageView getCardView() {
        return cardView;
    }

    /**
     * Setter to set the card image view
     * @param cardViewURL: The URL string for the card image
     */
    public void setCardView(String cardViewURL) {
        this.cardView = new ImageView(new Image(getClass().getResourceAsStream(cardViewURL)));
    }


}
