package com.example.fierydragons;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents the state of the game, including information about volcano cards, cave cards, players, dragon cards,
 * and the current player index.
 */
public class GameState {
    private ArrayList<ArrayList<HashMap<String, String>>> volcanoCardGroups;
    private ArrayList<ArrayList<HashMap<String, String>>> caveVolcanoCardGroups;
    private ArrayList<HashMap<String, String>> caves;
    private ArrayList<HashMap<String, String>> players;
    private ArrayList<HashMap<String, String>> dragonCards;
    private ArrayList<HashMap<String, Integer>> dragonCardLocations;
    private int currentPlayerIndex;

    /**
     * Constructs a new GameState with default values.
     * Initializes all lists to null and sets the current player index to -1.
     */
    public GameState() {
        this.volcanoCardGroups = null;
        this.caveVolcanoCardGroups = null;
        this.caves = null;
        this.players = null;
        this.dragonCards = null;
        this.dragonCardLocations = null;
        this.currentPlayerIndex = -1;
    }

    /**
     * Gets the groups of volcano cards.
     *
     * @return A list of volcano card groups.
     */
    public ArrayList<ArrayList<HashMap<String, String>>> getVolcanoCardGroups() {
        return volcanoCardGroups;
    }

    /**
     * Sets the groups of volcano cards.
     *
     * @param volcanoCardGroups A list of volcano card groups.
     */
    public void setVolcanoCardGroups(ArrayList<ArrayList<HashMap<String, String>>> volcanoCardGroups) {
        this.volcanoCardGroups = volcanoCardGroups;
    }

    /**
     * Gets the groups of cave volcano cards.
     *
     * @return A list of cave volcano card groups.
     */
    public ArrayList<ArrayList<HashMap<String, String>>> getCaveVolcanoCardGroups() {
        return caveVolcanoCardGroups;
    }

    /**
     * Sets the groups of cave volcano cards.
     *
     * @param caveVolcanoCardGroups A list of cave volcano card groups.
     */
    public void setCaveVolcanoCardGroups(ArrayList<ArrayList<HashMap<String, String>>> caveVolcanoCardGroups) {
        this.caveVolcanoCardGroups = caveVolcanoCardGroups;
    }

    /**
     * Gets the list of cave cards.
     *
     * @return A list of cave cards.
     */
    public ArrayList<HashMap<String, String>> getCaves() {
        return caves;
    }

    /**
     * Sets the list of cave cards.
     *
     * @param caves A list of cave cards.
     */
    public void setCaves(ArrayList<HashMap<String, String>> caves) {
        this.caves = caves;
    }

    /**
     * Gets the list of players.
     *
     * @return A list of players.
     */
    public ArrayList<HashMap<String, String>> getPlayers() {
        return players;
    }

    /**
     * Sets the list of players.
     *
     * @param players A list of players.
     */
    public void setPlayers(ArrayList<HashMap<String, String>> players) {
        this.players = players;
    }

    /**
     * Gets the list of dragon cards.
     *
     * @return A list of dragon cards.
     */
    public ArrayList<HashMap<String, String>> getDragonCards() {
        return dragonCards;
    }

    /**
     * Sets the list of dragon cards.
     *
     * @param dragonCards A list of dragon cards.
     */
    public void setDragonCards(ArrayList<HashMap<String, String>> dragonCards) {
        this.dragonCards = dragonCards;
    }

    /**
     * Gets the index of the current player.
     *
     * @return The index of the current player.
     */
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    /**
     * Sets the index of the current player.
     *
     * @param currentPlayerIndex The index of the current player.
     */
    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    /**
     * Gets the locations of the dragon cards.
     *
     * @return A list of dragon card locations.
     */
    public ArrayList<HashMap<String, Integer>> getDragonCardLocations() {
        return dragonCardLocations;
    }

    /**
     * Sets the locations of the dragon cards.
     *
     * @param dragonCardLocations A list of dragon card locations.
     */
    public void setDragonCardLocations(ArrayList<HashMap<String, Integer>> dragonCardLocations) {
        this.dragonCardLocations = dragonCardLocations;
    }
}
