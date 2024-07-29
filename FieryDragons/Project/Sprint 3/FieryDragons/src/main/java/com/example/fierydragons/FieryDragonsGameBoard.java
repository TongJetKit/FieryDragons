package com.example.fierydragons;

import com.example.fierydragons.Cards.*;
import com.example.fierydragons.FXMLController.FieryDragonGameBoardController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.*;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.xml.stream.Location;

/*
This class is used to represent the Fiery Dragons gameboard
 */
public class FieryDragonsGameBoard {

    private static FieryDragonsGameBoard gameBoardInstance;
    // A list of dragon cards
    private ArrayList<DragonCard> dragonCards = new ArrayList<>();
    // A list of cave cards
    private ArrayList<CaveCard> caveCards = new ArrayList<>();
    // A list of volcano card tile groups that has no cave linked to it
    private ArrayList<VolcanoCardGroup> volcanoCardGroup = new ArrayList<>();
    // A list of volcano card tile groups that has a cave linked to it
    private ArrayList<VolcanoCardGroup> caveVolcanoCardGroup = new ArrayList<>();
    // A list of players of the game
    private ArrayList<Player> player =  new ArrayList<>();
    // A hashmap that links the cave creature type to the respective cave image file name
    private Map<Symbol, String> caveCreature = new HashMap<>(){{
        put(Symbol.Bat, "CaveBat");
        put(Symbol.BabyDragon, "CaveBabyDragon");
        put(Symbol.Salamander, "CaveSalamander");
        put(Symbol.Spider, "CaveSpider");
    }};

    // A list of the stackpane that houses the cave cards
    private ArrayList<StackPane> starting_point;
    // A list of the volcano Cards that makes up the square ring of the board in order from top left as the start
    private ArrayList<LocationCard> boardLocation = new ArrayList<>();
    // The board size of the gameboard
    private final int boardSize;
    // The gridpane that houses the gameboard
    private GridPane board;
    // The activePlayer
    private Player activePlayer;

    // The current player index
    private int currentPlayerIndex = 0;

    /***
     * The constructor of the gameboard
     * @param starting_point: A list of the stackpane that houses the cave cards
     * @param board The GridPane that will house the gameboard
     */
    private FieryDragonsGameBoard(ArrayList<StackPane> starting_point, GridPane board) {
        this.boardSize = 7;
        setboard(board);
        setStarting_point(starting_point);
    }

    /***
     * The method to set the board
     * @param board The GridPane that will house the gameboard
     */
    public static FieryDragonsGameBoard getGameBoardInstance(ArrayList<StackPane> starting_point, GridPane board){

        gameBoardInstance = new FieryDragonsGameBoard(starting_point, board);

        return gameBoardInstance;
    }

    /***
     * The method to initialize the gameboard objects
     */
    public void initializeBoard(){
        //create the volcano cards
        addVolcanoCardToBoard();
        // create the caves
        addCavesToBoard(caveCreature);
        // create the dragon cards
        addDragonCardsToBoard();
        // create the player token
        initializePlayer(4);
        // set the active player
        setActivePlayer(player.get(0));
    }

    /***
     * The Method to create the Volcano Card Group tiles
     */
    private void addVolcanoCardToBoard(){

        //initialize Volcano Card Group Tiles that has a cave link to it
        //(hardcoded now but later will create a factory method to do so that generates a random volcanoCard)
        VolcanoCard left1 = new VolcanoCard(Symbol.BabyDragon,"/com/example/fierydragons/assets/images/DragonVolcanoCard.png", CardType.VolcanoCard);
        VolcanoCard middle1 = new VolcanoCard(Symbol.Bat,"/com/example/fierydragons/assets/images/BatCaveVolcanoCard.png", CardType.VolcanoCard);
        VolcanoCard right1 = new VolcanoCard(Symbol.Spider,"/com/example/fierydragons/assets/images/SpiderVolcanoCard.png", CardType.VolcanoCard);
        caveVolcanoCardGroup.add(new VolcanoCardGroup(left1, middle1, right1));

        VolcanoCard left2 = new VolcanoCard(Symbol.Salamander,"/com/example/fierydragons/assets/images/SalamanderVolcanoCard.png", CardType.VolcanoCard);
        VolcanoCard middle2 = new VolcanoCard(Symbol.Spider,"/com/example/fierydragons/assets/images/SpiderCaveVolcanoCard.png", CardType.VolcanoCard);
        VolcanoCard right2 = new VolcanoCard(Symbol.Bat,"/com/example/fierydragons/assets/images/BatVolcanoCard.png", CardType.VolcanoCard);
        caveVolcanoCardGroup.add(new VolcanoCardGroup(left2, middle2, right2));

        VolcanoCard left3 = new VolcanoCard(Symbol.Spider,"/com/example/fierydragons/assets/images/SpiderVolcanoCard.png", CardType.VolcanoCard);
        VolcanoCard middle3 = new VolcanoCard(Symbol.Salamander,"/com/example/fierydragons/assets/images/SalamanderCaveVolcanoCard.png", CardType.VolcanoCard);
        VolcanoCard right3 =  new VolcanoCard(Symbol.BabyDragon,"/com/example/fierydragons/assets/images/DragonVolcanoCard.png", CardType.VolcanoCard);
        caveVolcanoCardGroup.add(new VolcanoCardGroup(left3, middle3, right3));

        VolcanoCard left4 = new VolcanoCard(Symbol.Bat,"/com/example/fierydragons/assets/images/BatVolcanoCard.png", CardType.VolcanoCard);
        VolcanoCard middle4 = new VolcanoCard(Symbol.Spider,"/com/example/fierydragons/assets/images/SpiderCaveVolcanoCard.png", CardType.VolcanoCard);
        VolcanoCard right4 =  new VolcanoCard(Symbol.BabyDragon,"/com/example/fierydragons/assets/images/DragonVolcanoCard.png", CardType.VolcanoCard);
        caveVolcanoCardGroup.add(new VolcanoCardGroup(left4, middle4, right4));

        //initialize Volcano Card Group Tiles that has no cave link to it
        //(hardcoded now but later will create a factory method to do so that generates a random volcanoCard)
        volcanoCardGroup.add(new VolcanoCardGroup(
                new VolcanoCard(Symbol.Spider,"/com/example/fierydragons/assets/images/SpiderVolcanoCard.png", CardType.VolcanoCard),
                new VolcanoCard(Symbol.Bat,"/com/example/fierydragons/assets/images/BatVolcanoCard.png", CardType.VolcanoCard),
                new VolcanoCard(Symbol.Salamander,"/com/example/fierydragons/assets/images/SalamanderVolcanoCard.png", CardType.VolcanoCard)
        ));
        volcanoCardGroup.add(new VolcanoCardGroup(
                new VolcanoCard(Symbol.BabyDragon,"/com/example/fierydragons/assets/images/DragonVolcanoCard.png", CardType.VolcanoCard),
                new VolcanoCard(Symbol.Salamander,"/com/example/fierydragons/assets/images/SalamanderVolcanoCard.png", CardType.VolcanoCard),
                new VolcanoCard(Symbol.Bat,"/com/example/fierydragons/assets/images/BatVolcanoCard.png", CardType.VolcanoCard)
        ));
        volcanoCardGroup.add(new VolcanoCardGroup(
                new VolcanoCard(Symbol.Bat,"/com/example/fierydragons/assets/images/BatVolcanoCard.png", CardType.VolcanoCard),
                new VolcanoCard(Symbol.BabyDragon,"/com/example/fierydragons/assets/images/DragonVolcanoCard.png", CardType.VolcanoCard),
                new VolcanoCard(Symbol.Salamander,"/com/example/fierydragons/assets/images/SalamanderVolcanoCard.png", CardType.VolcanoCard)
        ));
        volcanoCardGroup.add(new VolcanoCardGroup(
                new VolcanoCard(Symbol.Salamander,"/com/example/fierydragons/assets/images/SalamanderVolcanoCard.png", CardType.VolcanoCard),
                new VolcanoCard(Symbol.BabyDragon,"/com/example/fierydragons/assets/images/DragonVolcanoCard.png", CardType.VolcanoCard),
                new VolcanoCard(Symbol.Spider,"/com/example/fierydragons/assets/images/SpiderVolcanoCard.png", CardType.VolcanoCard)
        ));

        // shuffle all the Volcano Card Group Tiles so it is random each game
        Collections.shuffle(caveVolcanoCardGroup);
        Collections.shuffle(volcanoCardGroup);
        // add the Volcano Card Group Tiles into the boardLocation array
        addBoardLocation(caveVolcanoCardGroup, volcanoCardGroup);

    }

    /**
     * The method to create the cave object
     * @param caves: A hashmap of all the caves
     */
    private void addCavesToBoard(Map<Symbol,String> caves) {

        // for each caves in the Hashmap
        for (Map.Entry<Symbol, String> entry : caves.entrySet()) {
            Symbol key = entry.getKey();
            String imageName = "/com/example/fierydragons/assets/images/" + entry.getValue() + ".png";
            // create a cave card and add it to the arraylist
            caveCards.add(new CaveCard(imageName, key, CardType.CaveCard));
        }

        Collections.shuffle(caveCards);

        // for each stackpane in starting_point, add them to their respective cave card
        for (int i = 0; i < caveCards.size(); i++) {
            caveCards.get(i).setStackPane(starting_point.get(i));
        }

        // for each volcano card group tiles that has a cave linked to it
        for(int i = 0 ; i<caveVolcanoCardGroup.size(); i++){
            // link the middle volcano card of the group tiles to one of the cave.
            caveVolcanoCardGroup.get(i).getMiddleVolcano().setLinkedCard(caveCards.get(i));
            // set the cave card where its exit point is the corner volcano card
            caveCards.get(i).setLinkedCard(boardLocation.get(i*(boardSize-1)));
        }
    }

    /***
     * The method to create the possible dragon cards and add it to the list of dragoncards
     */
    public void addDragonCardsToBoard() {
        // add the dragon cards for the baby dragon
        for(int i = 1; i<4; i++){
            dragonCards.add(new DragonCard("/com/example/fierydragons/assets/images/BabyDragon"+i+".png", Symbol.BabyDragon, i, CardType.CreatureDragonCard));
        }
        // add the dragon cards for the bat
        for(int i = 1; i<4; i++){
            dragonCards.add(new DragonCard("/com/example/fierydragons/assets/images/Bat"+i+".png", Symbol.Bat, i,CardType.CreatureDragonCard));
        }
        // add the dragon cards for the spider
        for(int i = 1; i<4; i++){
            dragonCards.add(new DragonCard("/com/example/fierydragons/assets/images/Spider"+i+".png", Symbol.Spider, i,CardType.CreatureDragonCard));
        }
        // add the dragon cards for the salamander
        for(int i = 1; i<4; i++){
            dragonCards.add(new DragonCard("/com/example/fierydragons/assets/images/Salamander"+i+".png", Symbol.Salamander, i,CardType.CreatureDragonCard));
        }
        // add the dragon cards for the pirate dragon
        for(int i = 1; i<3; i++){
            dragonCards.add(new DragonCard("/com/example/fierydragons/assets/images/PirateDragon"+i+".png", Symbol.PirateDragon, i,CardType.PirateDragonCard));
            dragonCards.add(new DragonCard("/com/example/fierydragons/assets/images/PirateDragon"+i+".png", Symbol.PirateDragon, i,CardType.PirateDragonCard));
        }

        // shuffle the dragon cards
        Collections.shuffle(dragonCards);

    }

    /***
     * The method to initialize the players
     * @param playerCount: The number of players
     */
    public void initializePlayer(int playerCount){
        for(int i = 0; i< playerCount; i++){
            this.player.add(new Player(caveCards.get(i), i, caveCards.get(i)));
        }

        // Start with the first player highlighted
        player.get(0).highlight();
    }

    /***
     * The method to initialize the boardLocation with volcano cards
     * @param caveVolcanoCardGroup: The Volcano Card Group Tiles that has a cave link to it
     * @param volcanoCardGroup: The Volcano Card Group Tiles that does not have a cave link to it
     */
    private void addBoardLocation(ArrayList<VolcanoCardGroup> caveVolcanoCardGroup, ArrayList<VolcanoCardGroup> volcanoCardGroup) {

        // An iterator to move through the volcano card group tiles with no cave link to it without using index
        Iterator<VolcanoCardGroup> iter = volcanoCardGroup.iterator();
        // set the top left corner volcano cards
        boardLocation.add(caveVolcanoCardGroup.get(0).getMiddleVolcano());
        boardLocation.add(caveVolcanoCardGroup.get(0).getRightVolcano());

        // keep adding the next volcano card group tiles until u reach the top right corner
        for(int col = 2; col< boardSize-2; col+=3){
            VolcanoCardGroup nextGroup = iter.next();
            boardLocation.add(nextGroup.getLeftVolcano());
            boardLocation.add(nextGroup.getMiddleVolcano());
            boardLocation.add(nextGroup.getRightVolcano());
        }

        // Set the top right corner volcano cards
        boardLocation.add(caveVolcanoCardGroup.get(1).getLeftVolcano());
        boardLocation.add(caveVolcanoCardGroup.get(1).getMiddleVolcano());
        boardLocation.add(caveVolcanoCardGroup.get(1).getRightVolcano());

        // keep adding the next volcano card group tiles until u reach the bottom right corner
        for(int row = 2; row < boardSize-2; row+=3){
            VolcanoCardGroup nextGroup = iter.next();
            boardLocation.add(nextGroup.getLeftVolcano());
            boardLocation.add(nextGroup.getMiddleVolcano());
            boardLocation.add(nextGroup.getRightVolcano());
        }

        // Set the bottom right corner volcano cards
        boardLocation.add(caveVolcanoCardGroup.get(2).getLeftVolcano());
        boardLocation.add(caveVolcanoCardGroup.get(2).getMiddleVolcano());
        boardLocation.add(caveVolcanoCardGroup.get(2).getRightVolcano());

        // keep adding the next volcano card group tiles until u reach the bottom left corner
        for(int col = boardSize-3; col > 1; col-=3){
            VolcanoCardGroup nextGroup = iter.next();
            boardLocation.add(nextGroup.getLeftVolcano());
            boardLocation.add(nextGroup.getMiddleVolcano());
            boardLocation.add(nextGroup.getRightVolcano());
        }

        // Set the bottom right corner volcano cards
        boardLocation.add(caveVolcanoCardGroup.get(3).getLeftVolcano());
        boardLocation.add(caveVolcanoCardGroup.get(3).getMiddleVolcano());
        boardLocation.add(caveVolcanoCardGroup.get(3).getRightVolcano());

        // keep adding the next volcano card group tiles until u reach the top left corner
        for(int row = boardSize-3; row >1; row-=3){
            VolcanoCardGroup nextGroup = iter.next();
            boardLocation.add(nextGroup.getLeftVolcano());
            boardLocation.add(nextGroup.getMiddleVolcano());
            boardLocation.add(nextGroup.getRightVolcano());
        }
        // add the last volcano card which is the card below the top right corner
        boardLocation.add(caveVolcanoCardGroup.get(0).getLeftVolcano());
    }

    /***
     * The method to get the destination volcano card based on the chosen dragon card
     * @param player: The current location of the player
     * @param creatureCount: The number of creatures on the flipped dragon card
     * @param moveOffset: The offset of the chosen dragon card (1 for a normal creature dragon card, -1 for pirate dragon card)
     * @return The Volcano Card that is the destination board location.
     */
    public LocationCard getDestination(Player player, int creatureCount, int moveOffset){
        // get the current location index
        int oldLocationIndex = boardLocation.indexOf(player.getCurrentLocation());
        LocationCard destination = player.getCurrentLocation();
        // while you can still move forward
        while(creatureCount > 0){
            // if it is a cave card then move to the volcano card tiles
            if(player.getCurrentLocation().getCardType().equals(CardType.CaveCard)){
                // set the destination to the volcano card tile link to it
                destination = player.getCurrentLocation().getLinkedCard();
            }
            // if it is a volcano card that links to a cave card and if the cave card is the player's home cave card
            else if(player.getCurrentLocation().hasLinkedCard() && player.getCave().equals(player.getCurrentLocation().getLinkedCard())){
                // set the destination to the cave then return
                destination = player.getCurrentLocation().getLinkedCard();
               return destination;
            }
            else{
                // else the destination is the next volcano card
                destination = boardLocation.get((oldLocationIndex+moveOffset)%boardLocation.size());
            }
            // reduce the creature count which is the moves left
            creatureCount--;
        }
        // return the destination card
        return destination;
    }



    /***
     * A getter to return the player arraylist
     * @return the player arraylist
     */
    public ArrayList<Player> getPlayer() {
        return player;
    }

    /***
     * A getter to return the current player index
     * @return the current player index
     */
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    /***
     * A setter to set the current player index
     * @param currentPlayerIndex: The current player index
     */
    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    /**
     * A setter to set the starting point array list
     * @param starting_point: The starting point array list
     */
    public void setStarting_point(ArrayList<StackPane> starting_point) {
        this.starting_point = starting_point;
    }

    /***
     * A getter to return the GridPane
     * @return the GridPane
     */
    public GridPane getGridPane() {
        return board;
    }

    /**
     * A setter to set the GridPane
     * @param board: The GridPane
     */
    public void setboard(GridPane board) {
        this.board = board;
    }

    /***
     * A setter to set the active player
     * @return active player
     */
    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    /***
     * A getter to return the active player
     * @return the active player
     */
    public Player getActivePlayer() {
        return activePlayer;
    }

    /***
     * A getter to return the board location arraylist
     * @return: The boardlocation array list
     */
    public ArrayList<LocationCard> getBoardLocation() {
        return boardLocation;
    }

    /***
     * A setter to set the board location array list
     * @param boardLocation: The array list of the board locations
     */
    public void setBoardLocation(ArrayList<LocationCard> boardLocation) {
        this.boardLocation = boardLocation;
    }

    /***
     * A getter to return the dragon cards arraylist
     * @return the dragon cards arraylist
     */
    public ArrayList<DragonCard> getDragonCards() {
        return dragonCards;
    }

    /***
     * A setter to set the dragon cards arraylist
     * @param dragonCards: The dragon cards arraylist
     */
    public void setDragonCards(ArrayList<DragonCard> dragonCards) {
        this.dragonCards = dragonCards;
    }

    /***
     * A getter to return the cave cards arraylist
     * @return the cave cards arraylist
     */
    public ArrayList<CaveCard> getCaveCards() {
        return caveCards;
    }

    /***
     * A setter to set the cave cards arraylist
     * @param caveCards The cave cards arraylist
     */
    public void setCaveCards(ArrayList<CaveCard> caveCards) {
        this.caveCards = caveCards;
    }

    /***
     * A getter to return the arraylist of volcano card group that has no linked caves
     * @return the arraylist of volcano card group that has no linked caves
     */
    public ArrayList<VolcanoCardGroup> getVolcanoCardGroup() {
        return volcanoCardGroup;
    }

    /**
     * A setter to set the arraylist of volcano card group that has no linked caves
     * @param volcanoCardGroup: the arraylist of volcano card group that has no linked caves
     */
    public void setVolcanoCardGroup(ArrayList<VolcanoCardGroup> volcanoCardGroup) {
        this.volcanoCardGroup = volcanoCardGroup;
    }

    /***
     * A getter to return the arraylist of volcano card group that has linked caves
     * @return the arraylist of volcano card group that has linked caves
     */
    public ArrayList<VolcanoCardGroup> getCaveVolcanoCardGroup() {
        return caveVolcanoCardGroup;
    }

    /**
     * A setter to set the arraylist of volcano card group that has linked caves
     * @param caveVolcanoCardGroup: the arraylist of volcano card group that has no linked caves
     */
    public void setCaveVolcanoCardGroup(ArrayList<VolcanoCardGroup> caveVolcanoCardGroup) {
        this.caveVolcanoCardGroup = caveVolcanoCardGroup;
    }

    /***
     * A getter to return the starting point stackpane arraylist
     * @return the starting point stackpane arraylist
     */
    public ArrayList<StackPane> getStarting_point() {
        return starting_point;
    }

    /***
     * A getter to return the board size
     * @return the board size
     */
    public int getBoardSize() {
        return boardSize;
    }


    /***
     * This method is used to move the player from the cave to the board.
     * @param currentPlayer
     * @param flippedCard
     */
    public void movePlayerFromCave(Player currentPlayer, DragonCard flippedCard) {
        LocationCard currentLocation = currentPlayer.getCurrentLocation();

        // if the current location is a cave card and the symbol of the cave card is the same as the flipped card
        if (currentLocation.getCardType().equals(CardType.CaveCard) && currentLocation.getCardSymbol().equals(flippedCard.getCardSymbol())) {
            // Start from the linked board location, not the cave itself
            //LocationCard startLocation = ((CaveCard) currentLocation).getLinkedCard();
            int stepsToMove = flippedCard.getCreatureCount(); // Number of spaces to move

            LocationCard newLocation = getNewLocation(currentPlayer, currentLocation, stepsToMove, 1); // Get new location based on steps to move forward

            System.out.println("newLocation: " + newLocation);

            System.out.println("newLocation null check: " + (newLocation != null));

            if (newLocation != null && !newLocation.hasPlayer()) {
                // remove player from location card
                currentLocation.removeCurrentPlayer();

                System.out.println("newLocation: " + newLocation.getCardSymbol() + " " + newLocation.getCurrentRow() + " " + newLocation.getCurrentColumn());
                currentPlayer.setCurrentLocation(newLocation);
                updatePlayerPositionInGrid(currentPlayer, newLocation.getCurrentRow(), newLocation.getCurrentColumn());
                System.out.println("Player moved " + stepsToMove + " spaces to board from cave.");

                // Update player move count based on steps moved
                currentPlayer.setPlayerMoveCount(currentPlayer.getPlayerMoveCount() + stepsToMove);

                // Add player to new location
                newLocation.setCurrentPlayer(currentPlayer);

                System.out.println("Player move count: " + currentPlayer.getPlayerMoveCount());
            } else {
                System.out.println("problem here");
                System.out.println("New position is occupied or doesn't exist.");
            }
        } else if (!(flippedCard.getCardSymbol().equals(Symbol.PirateDragon))){
            // remove player from location card
            currentLocation.removeCurrentPlayer();

            movePlayerOnBoard(currentPlayer, flippedCard);
        }
    }

    /***
     * This method is used to move the player on the board.
     * @param currentPlayer
     * @param flippedCard
     * @return
     */
    public boolean movePlayerOnBoard(Player currentPlayer, DragonCard flippedCard) {
        System.out.println("===================================");
        // lets say its 28... 28 - 26 = 2 (extra that needs to be added to player move count)

        LocationCard currentLocation = currentPlayer.getCurrentLocation();
        boolean isNextLocationOccupied = false;

        System.out.println("Player move count right now lol: " + currentPlayer.getPlayerMoveCount());
        if (currentPlayer.getPlayerMoveCount() > 25) {
            System.out.println("this is running");
            currentPlayer.setPlayerMoveCount(currentPlayer.getPlayerMoveCount() - 26);
            return true;

        }

        if (currentLocation.getCardSymbol().equals(flippedCard.getCardSymbol())) {
            int stepsToMove = flippedCard.getCreatureCount(); // Number of spaces to move

            LocationCard newLocation = getNewLocation(currentPlayer, currentLocation, stepsToMove, 1); // Get new location based on steps to move forward

            System.out.println("stepsToMove: " + stepsToMove);

            System.out.println("newLocation does not have player: " + !newLocation.hasPlayer());
            if (!newLocation.hasPlayer()) {
                System.out.println("prob1");

                // Update player move count based on steps moved
                currentPlayer.setPlayerMoveCount(currentPlayer.getPlayerMoveCount() + stepsToMove);
                System.out.println("Player move count: " + currentPlayer.getPlayerMoveCount());

                // if player count is a multiple of 26, then player wins and add him back to the cave... else add player to new location
                if (currentPlayer.getPlayerMoveCount() == 26) {
                    System.out.println("Player wins!");

                    // remove player from location card
                    currentLocation.removeCurrentPlayer();

                    // Add player to cave
                    currentPlayer.setCurrentLocation(currentPlayer.getCave());

                    // Add player stackpane to cave
                    currentPlayer.getCave().getStackPane().getChildren().add(currentPlayer.getToken());

                    // Set current player in cave
                    currentPlayer.getCave().setCurrentPlayer(currentPlayer);

                    // display win message
                    displayWinningMessage("Player " + (currentPlayer.getNumber() + 1), currentPlayer);

                    isNextLocationOccupied = false;
                }
                // else, he did not win yet, so move him to the new location
                else {
                    System.out.println("prob2");

                    // if the player doesnt have the exact number of steps to move, then end his turn
                    if (currentPlayer.getPlayerMoveCount() > 26) {
                        System.out.println("prob3");

                        currentPlayer.setPlayerMoveCount(currentPlayer.getPlayerMoveCount() - 24);


                        // remove player from location card
                        currentLocation.removeCurrentPlayer();

                        // set location to current player
                        currentPlayer.setCurrentLocation(newLocation);

                        // Add player to new location
                        newLocation.setCurrentPlayer(currentPlayer);


                        updatePlayerPositionInGrid(currentPlayer, newLocation.getCurrentRow(), newLocation.getCurrentColumn());

                        System.out.println("Player moved " + stepsToMove + " spaces to board from cave.");

                        return true;
                    }

                    // remove player from location card
                    currentLocation.removeCurrentPlayer();

                    // Add player to new location
                    newLocation.setCurrentPlayer(currentPlayer);

                    currentPlayer.setCurrentLocation(newLocation);
                    updatePlayerPositionInGrid(currentPlayer, newLocation.getCurrentRow(), newLocation.getCurrentColumn());
                    System.out.println("Player moved " + stepsToMove + " spaces to board from cave.");


                    isNextLocationOccupied = false;
                }
            } else {
                System.out.println("prob4");
                System.out.println("New position is occupied or doesn't exist.");
                isNextLocationOccupied = true;
            }
        } else if (flippedCard.getCardSymbol().equals(Symbol.PirateDragon)) {
            System.out.println("pirate dragon hehe");
            int stepsToMove = flippedCard.getCreatureCount(); // Number of spaces to move
            LocationCard newLocation = getNewLocation(currentPlayer, currentLocation, stepsToMove, -1); // Get new location based on steps to move backward

            if (newLocation != null && !newLocation.hasPlayer()) {

                // remove player from location card
                currentLocation.removeCurrentPlayer();

                // add player to new location
                newLocation.setCurrentPlayer(currentPlayer);

                currentPlayer.setCurrentLocation(newLocation);
                updatePlayerPositionInGrid(currentPlayer, newLocation.getCurrentRow(), newLocation.getCurrentColumn());
                System.out.println("Player moved backwards " + stepsToMove);
                currentPlayer.setPlayerMoveCount(currentPlayer.getPlayerMoveCount() - stepsToMove);
                isNextLocationOccupied = false;
            } else {
                System.out.println("New position is occupied or doesn't exist.");
                isNextLocationOccupied = true;
            }
        }
        return isNextLocationOccupied;
    }


    /***
     * This method is used to display the winning message to the player.
     * @param playerName
     * @param winner
     */
    public void displayWinningMessage(String playerName, Player winner) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(playerName + " wins!");
            alert.setContentText("Do you want to return to the main menu of exit the game?");

            // Get the buttons from the dialog pane
            Button menuButton = (Button) alert.getDialogPane().lookupButton(alert.getButtonTypes().get(0));
            Button exitButton = (Button) alert.getDialogPane().lookupButton(alert.getButtonTypes().get(1));

            // Set new labels for the buttons
            menuButton.setText("Main Menu");
            exitButton.setText("Exit");



            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Stage stage = (Stage) winner.getToken().getScene().getWindow();

                    AnchorPane root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("/com/example/fierydragons/game-start-screen.fxml"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    Scene scene = new Scene(root,1280, 720);
                    stage.setScene(scene);
                    stage.setResizable(true);
                    stage.show();
                    System.out.println("Returning to main menu.");


                } else {
                    Stage stage = (Stage) winner.getToken().getScene().getWindow();
                    stage.close();
                    System.out.println("Game closed.");
                }
            });
        });
    }


//    public void movePlayerAfterOneRound(Player currentPlayer, DragonCard flippedCard) {
//
//        // get current location of player
//        LocationCard currentLocation = currentPlayer.getCurrentLocation();
//
//        int extraMoves = currentPlayer.getPlayerMoveCount() - 25 + flippedCard.getCreatureCount();
//
////        // that means he flipped a pirate dragon card before he reached 26
////        if (extraMoves < 0) {
////            extraMoves = 0;
////        }
//
//        // else he has extra moves to move forward
//        LocationCard newLocation = getNewLocation(currentPlayer, currentPlayer.getCave().getLinkedCard(), extraMoves, 1); // Get new location based on steps to move forward
//
//        currentPlayer.setPlayerMoveCount(extraMoves);
//
//        System.out.println("Extra moves: " + extraMoves);
//
//        System.out.println("removing current player...");
//        // remove player from location card
//        currentLocation.removeCurrentPlayer();
//
//        // set location to current player
//        currentPlayer.setCurrentLocation(newLocation);
//
//        // Add player to new location
//        newLocation.setCurrentPlayer(currentPlayer);
//
//        // Update player position in grid
//        updatePlayerPositionInGrid(currentPlayer, newLocation.getCurrentRow(), newLocation.getCurrentColumn());
//
//
//
//    }

    /***
     * This method is used to get the new location of the player on the board.
     * @param currentLocation
     * @param stepsToMove
     * @param direction
     * @return
     */
    public LocationCard getNewLocation(Player player, LocationCard currentLocation, int stepsToMove, int direction) {


        int playerMoves = player.getPlayerMoveCount();

        if (playerMoves > 26) {
            playerMoves = 0;
        }

//        int newIndex = (currentIndex + direction * stepsToMove) % boardLocation.size();

//        if (newIndex < 0) newIndex += boardLocation.size();  // Handle negative indices for backward movement
        // copy the player move count
        // when moving, + count - count
        // scenario 3: if the current player location is a normal volcano card
        int newIndex;
        LocationCard destination = currentLocation;
        int currentIndex = boardLocation.indexOf(destination);

        for(int i = 0; i<stepsToMove; i++){
            System.out.println("Player moves in getNewLocation: " + playerMoves);
            // scenario 1: if the current player location is in the cave
            if(destination.getCardType().equals(CardType.CaveCard)){
                destination = currentLocation.getLinkedCard();
                currentIndex = boardLocation.indexOf(destination);
                playerMoves++;
            }
            // scenario 2: if the current volcano card has a cave card
            // TODO need to check if player count(copied) is 25(entire circle)
            else if(destination.getCardType().equals(CardType.VolcanoCard) && destination.hasLinkedCard() && ((playerMoves == 25) && i == stepsToMove-1)) {
                LocationCard linkedCard = destination.getLinkedCard();
                if(direction >0){
                    if (linkedCard.getCardType().equals(CardType.CaveCard) &&
                            player.getCave().getCardSymbol().equals(linkedCard.getCardSymbol())) {

                        // if the player is in the last volcano card and the linked card is a cave card
                        // and the cave card has the same symbol as the player's cave card
                        // move the player to the linked cave card
                        System.out.println("getNewLocation debug");

//                        newIndex = (currentIndex + direction) % boardLocation.size();
//                        System.out.println("newIndex: " + newIndex);
//                        if (newIndex >= 0) {
//                            newIndex = (currentIndex + direction - 1) % boardLocation.size();
//                            newIndex += 1;
//                            destination = boardLocation.get(newIndex);
//                            currentIndex = newIndex;
//                            playerMoves++;
//                        }
//                        else {
                            return destination.getLinkedCard();
//                        }

                    }
                }

            }
            // scenario 3: if the player is in a normal volcano card
            else{
                newIndex = (currentIndex + direction) % boardLocation.size();
                if (newIndex < 0) newIndex += boardLocation.size();  // Handle negative indices
                destination = boardLocation.get(newIndex);
                currentIndex = newIndex;
                playerMoves++;
            }

        }


        return destination;
    }


    /***
     * This method is used to check if the position is occupied by another player.
     * @param row
     * @param column
     * @return
     */
    public boolean isPositionOccupied(int row, int column) {
        for (Player p : player) {
            if (p.getCurrentLocation().getCurrentRow() == row && p.getCurrentLocation().getCurrentColumn() == column) {
                return true;  // Position is occupied
            }
        }
        return false;  // Position is not occupied
    }


    /***
     * This method is used to update the player position in the grid.
     * @param player
     * @param newRow
     * @param newColumn
     */
    public void updatePlayerPositionInGrid(Player player, int newRow, int newColumn) {
        GridPane gridPane = getGridPane();  // Assuming you have a getter for your GridPane
        gridPane.getChildren().remove(player.getToken());  // Remove the token from its current position
        gridPane.add(player.getToken(), newColumn, newRow);  // Place the token at the new position
        GridPane.setHalignment(player.getToken(), HPos.CENTER);
        GridPane.setValignment(player.getToken(), VPos.CENTER);
    }

}
