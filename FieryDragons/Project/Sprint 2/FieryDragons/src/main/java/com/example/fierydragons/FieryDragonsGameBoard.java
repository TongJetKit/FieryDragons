package com.example.fierydragons;

import com.example.fierydragons.Cards.*;
import javafx.scene.layout.GridPane;

import java.util.*;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Pair;
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

    public static FieryDragonsGameBoard getGameBoardInstance(ArrayList<StackPane> starting_point, GridPane board){
        if(gameBoardInstance == null){
            gameBoardInstance = new FieryDragonsGameBoard(starting_point, board);
        }
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
     * Method to call when it is time to change the players turn
     */
    public void nextTurn(){

    }

    /***
     * A getter to return the player arraylist
     * @return the player arraylist
     */
    public ArrayList<Player> getPlayer() {
        return player;
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
    public GridPane getboard() {
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

    // Method to move the player one tile up, only if on the left edge
//    public void movePlayerUp() {
//        if (player.getCurrentColumn() == 0 && player.getCurrentRow() > 0) {
//            placePlayer(player.getCurrentRow() - 1, player.getCurrentColumn());
//        }
//    }
//
//    // Method to move the player one tile down, only if on the right edge
//    public void movePlayerDown() {
//        if (player.getCurrentColumn() == boardSize - 1 && player.getCurrentRow() < boardSize - 1) {
//            placePlayer(player.getCurrentRow() + 1, player.getCurrentColumn());
//        }
//    }
//
//    // Method to move the player one tile to the left, only if on the bottom edge
//    public void movePlayerLeft() {
//        if (player.getCurrentRow() == boardSize - 1 && player.getCurrentColumn() > 0) {
//            placePlayer(player.getCurrentRow(), player.getCurrentColumn() - 1);
//        }
//    }
//
//    // Method to move the player one tile to the right, only if on the top edge
//    public void movePlayerRight() {
//        if (player.getCurrentRow() == 0 && player.getCurrentColumn() < boardSize - 1) {
//            placePlayer(player.getCurrentRow(), player.getCurrentColumn() + 1);
//        }
//    }



    // Method for a player to choose and uncover a dragon card
//    public boolean uncoverDragonCard(int cardIndex) {
//        if (cardIndex >= 0 && cardIndex < dragonCards.size()) {
//            DragonCard card = dragonCards.get(cardIndex);
//            card.flipCard();
//
//            if (player.getRepresentation().equals(card.getCreatureType())) {
//
//                int moves = card.getCreatureCount();
//
//                return true;
//            }
//        }
//        return false; // Return false if the card does not match
//    }
}
