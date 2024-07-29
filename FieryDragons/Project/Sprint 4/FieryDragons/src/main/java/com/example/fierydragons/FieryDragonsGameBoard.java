package com.example.fierydragons;

import com.example.fierydragons.Cards.*;
import javafx.scene.layout.GridPane;

import java.util.*;

import javafx.scene.layout.StackPane;

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
    private ArrayList<Player> player = new ArrayList<>();
    // A hashmap that links the cave creature type to the respective cave image file name
    private Map<Symbol, String> caveCreature = new HashMap<>() {{
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
        setBoard(board);
        setStarting_point(starting_point);
    }

    /***
     * The method to set the board
     * @param board The GridPane that will house the gameboard
     */
    public static FieryDragonsGameBoard getGameBoardInstance(ArrayList<StackPane> starting_point, GridPane board) {

        gameBoardInstance = new FieryDragonsGameBoard(starting_point, board);

        return gameBoardInstance;
    }

    /**
     * Initializes the game board based on the provided game state.
     *
     * This method performs the following actions:
     * - Adds volcano cards to the board based on the game state.
     * - Adds caves to the board based on the game state.
     * - Adds dragon cards to the board based on the game state.
     * - Initializes player tokens based on the game state.
     * - Sets the active player.
     *
     * @param gameState The current state of the game.
     */
    public void initializeBoard(GameState gameState) {
        //create the volcano cards
        addVolcanoCardToBoard(gameState);
        // create the caves
        addCavesToBoard(caveCreature, gameState);
        // create the dragon cards
        addDragonCardsToBoard(gameState);
        // create the player token
        initializePlayer(4, gameState);
        // set the active player
        setActivePlayer(player.get(0));
    }

    /***
     * The Method to create the Volcano Card Group tiles
     *
     * @param gameState The current state of the game.
     */
    private void addVolcanoCardToBoard(GameState gameState) {

        if (gameState == null){
            //initialize Volcano Card Group Tiles that has a cave link to it
            //(hardcoded now but later will create a factory method to do so that generates a random volcanoCard)
            VolcanoCard left1 = new VolcanoCard(Symbol.BabyDragon, "/com/example/fierydragons/assets/images/DragonVolcanoCard.png", CardType.VolcanoCard);
            VolcanoCard middle1 = new VolcanoCard(Symbol.Bat, "/com/example/fierydragons/assets/images/BatCaveVolcanoCard.png", CardType.VolcanoCard);
            VolcanoCard right1 = new VolcanoCard(Symbol.Spider, "/com/example/fierydragons/assets/images/SpiderVolcanoCard.png", CardType.VolcanoCard);
            caveVolcanoCardGroup.add(new VolcanoCardGroup(left1, middle1, right1));

            VolcanoCard left2 = new VolcanoCard(Symbol.Salamander, "/com/example/fierydragons/assets/images/SalamanderVolcanoCard.png", CardType.VolcanoCard);
            VolcanoCard middle2 = new VolcanoCard(Symbol.Spider, "/com/example/fierydragons/assets/images/SpiderCaveVolcanoCard.png", CardType.VolcanoCard);
            VolcanoCard right2 = new VolcanoCard(Symbol.Bat, "/com/example/fierydragons/assets/images/BatVolcanoCard.png", CardType.VolcanoCard);
            caveVolcanoCardGroup.add(new VolcanoCardGroup(left2, middle2, right2));

            VolcanoCard left3 = new VolcanoCard(Symbol.Spider, "/com/example/fierydragons/assets/images/SpiderVolcanoCard.png", CardType.VolcanoCard);
            VolcanoCard middle3 = new VolcanoCard(Symbol.Salamander, "/com/example/fierydragons/assets/images/SalamanderCaveVolcanoCard.png", CardType.VolcanoCard);
            VolcanoCard right3 = new VolcanoCard(Symbol.BabyDragon, "/com/example/fierydragons/assets/images/DragonVolcanoCard.png", CardType.VolcanoCard);
            caveVolcanoCardGroup.add(new VolcanoCardGroup(left3, middle3, right3));

            VolcanoCard left4 = new VolcanoCard(Symbol.Bat, "/com/example/fierydragons/assets/images/BatVolcanoCard.png", CardType.VolcanoCard);
            VolcanoCard middle4 = new VolcanoCard(Symbol.Spider, "/com/example/fierydragons/assets/images/SpiderCaveVolcanoCard.png", CardType.VolcanoCard);
            VolcanoCard right4 = new VolcanoCard(Symbol.BabyDragon, "/com/example/fierydragons/assets/images/DragonVolcanoCard.png", CardType.VolcanoCard);
            caveVolcanoCardGroup.add(new VolcanoCardGroup(left4, middle4, right4));

            //initialize Volcano Card Group Tiles that has no cave link to it
            //(hardcoded now but later will create a factory method to do so that generates a random volcanoCard)
            volcanoCardGroup.add(new VolcanoCardGroup(
                    new VolcanoCard(Symbol.Spider, "/com/example/fierydragons/assets/images/SpiderVolcanoCard.png", CardType.VolcanoCard),
                    new VolcanoCard(Symbol.Bat, "/com/example/fierydragons/assets/images/BatVolcanoCard.png", CardType.VolcanoCard),
                    new VolcanoCard(Symbol.Salamander, "/com/example/fierydragons/assets/images/SalamanderVolcanoCard.png", CardType.VolcanoCard)
            ));
            volcanoCardGroup.add(new VolcanoCardGroup(
                    new VolcanoCard(Symbol.BabyDragon, "/com/example/fierydragons/assets/images/DragonVolcanoCard.png", CardType.VolcanoCard),
                    new VolcanoCard(Symbol.Salamander, "/com/example/fierydragons/assets/images/SalamanderVolcanoCard.png", CardType.VolcanoCard),
                    new VolcanoCard(Symbol.Bat, "/com/example/fierydragons/assets/images/BatVolcanoCard.png", CardType.VolcanoCard)
            ));
            volcanoCardGroup.add(new VolcanoCardGroup(
                    new VolcanoCard(Symbol.Bat, "/com/example/fierydragons/assets/images/BatVolcanoCard.png", CardType.VolcanoCard),
                    new VolcanoCard(Symbol.BabyDragon, "/com/example/fierydragons/assets/images/DragonVolcanoCard.png", CardType.VolcanoCard),
                    new VolcanoCard(Symbol.Salamander, "/com/example/fierydragons/assets/images/SalamanderVolcanoCard.png", CardType.VolcanoCard)
            ));
            volcanoCardGroup.add(new VolcanoCardGroup(
                    new VolcanoCard(Symbol.Salamander, "/com/example/fierydragons/assets/images/SalamanderVolcanoCard.png", CardType.VolcanoCard),
                    new VolcanoCard(Symbol.BabyDragon, "/com/example/fierydragons/assets/images/DragonVolcanoCard.png", CardType.VolcanoCard),
                    new VolcanoCard(Symbol.Spider, "/com/example/fierydragons/assets/images/SpiderVolcanoCard.png", CardType.VolcanoCard)
            ));
            // shuffle all the Volcano Card Group Tiles so it is random each game
            Collections.shuffle(caveVolcanoCardGroup);
            Collections.shuffle(volcanoCardGroup);
        }
        else {
            for (ArrayList<HashMap<String, String>> volGroup : gameState.getVolcanoCardGroups()) {
                if (volGroup.size() != 3) {
                    throw new IllegalArgumentException("Each volcano card group must contain exactly 3 cards.");
                }

                // Retrieve left, middle, and right volcano cards
                VolcanoCard leftVolcano = VolcanoCard.fromMap(volGroup.get(0));
                VolcanoCard middleVolcano = VolcanoCard.fromMap(volGroup.get(1));
                VolcanoCard rightVolcano = VolcanoCard.fromMap(volGroup.get(2));
                volcanoCardGroup.add(new VolcanoCardGroup(leftVolcano, middleVolcano, rightVolcano));
            }

            for (ArrayList<HashMap<String, String>> volGroup : gameState.getCaveVolcanoCardGroups()) {
                if (volGroup.size() != 3) {
                    throw new IllegalArgumentException("Each volcano card group must contain exactly 3 cards.");
                }

                // Retrieve left, middle, and right volcano cards
                VolcanoCard leftVolcano = VolcanoCard.fromMap(volGroup.get(0));
                VolcanoCard middleVolcano = VolcanoCard.fromMap(volGroup.get(1));
                VolcanoCard rightVolcano = VolcanoCard.fromMap(volGroup.get(2));
                caveVolcanoCardGroup.add(new VolcanoCardGroup(leftVolcano, middleVolcano, rightVolcano));
            }
        }

        // add the Volcano Card Group Tiles into the boardLocation array
        addBoardLocation(caveVolcanoCardGroup, volcanoCardGroup);

    }

    /**
     * The method to create the cave object
     *
     * @param caves: A hashmap of all the caves.
     * @param gameState The current state of the game.
     */
    private void addCavesToBoard(Map<Symbol, String> caves, GameState gameState) {

        if(gameState == null){
            // for each caves in the Hashmap
            for (Map.Entry<Symbol, String> entry : caves.entrySet()) {
                Symbol key = entry.getKey();
                String imageName = "/com/example/fierydragons/assets/images/" + entry.getValue() + ".png";
                // create a cave card and add it to the arraylist
                caveCards.add(new CaveCard(imageName, key, CardType.CaveCard));
            }
            Collections.shuffle(caveCards);
        }
        else{
            // Loop through the list of cave card data from GameState
            for (HashMap<String, String> caveData : gameState.getCaves()) {
                CaveCard caveCard = new CaveCard(
                        caveData.get("cardViewURL"),
                        Symbol.valueOf(caveData.get("cardSymbol")),
                        CardType.valueOf(caveData.get("cardType"))
                );
                caveCards.add(caveCard);
            }
        }

        // for each stackpane in starting_point, add them to their respective cave card
        for (int i = 0; i < caveCards.size(); i++) {
            caveCards.get(i).setStackPane(starting_point.get(i));
        }

        // for each volcano card group tiles that has a cave linked to it
        for (int i = 0; i < caveVolcanoCardGroup.size(); i++) {
            // link the middle volcano card of the group tiles to one of the cave.
            caveVolcanoCardGroup.get(i).getMiddleVolcano().setLinkedCard(caveCards.get(i));
            // set the cave card where its exit point is the corner volcano card
            caveCards.get(i).setLinkedCard(boardLocation.get(i * (boardSize - 1)));
        }
    }

    /***
     * The method to create the possible dragon cards and add it to the list of dragoncards
     *
     * @param gameState The current state of the game.
     */
    public void addDragonCardsToBoard(GameState gameState) {

        if(gameState == null){
            // add the dragon cards for the baby dragon
            for (int i = 1; i < 4; i++) {
                dragonCards.add(new MovementDragonCard("/com/example/fierydragons/assets/images/BabyDragon" + i + ".png", Symbol.BabyDragon, i, CardType.CreatureDragonCard));
            }
            // add the dragon cards for the bat
            for (int i = 1; i < 4; i++) {
                dragonCards.add(new MovementDragonCard("/com/example/fierydragons/assets/images/Bat" + i + ".png", Symbol.Bat, i, CardType.CreatureDragonCard));
            }
            // add the dragon cards for the spider
            for (int i = 1; i < 4; i++) {
                dragonCards.add(new MovementDragonCard("/com/example/fierydragons/assets/images/Spider" + i + ".png", Symbol.Spider, i, CardType.CreatureDragonCard));
            }
            // add the dragon cards for the salamander
            for (int i = 1; i < 4; i++) {
                dragonCards.add(new MovementDragonCard("/com/example/fierydragons/assets/images/Salamander" + i + ".png", Symbol.Salamander, i, CardType.CreatureDragonCard));
            }
            // add the dragon cards for the pirate dragon
            for (int i = 1; i < 3; i++) {
                dragonCards.add(new MovementDragonCard("/com/example/fierydragons/assets/images/PirateDragon" + i + ".png", Symbol.PirateDragon, i, CardType.PirateDragonCard));
                dragonCards.add(new MovementDragonCard("/com/example/fierydragons/assets/images/PirateDragon" + i + ".png", Symbol.PirateDragon, i, CardType.PirateDragonCard));
            }

            dragonCards.add(new SwapDragonCard("/com/example/fierydragons/assets/images/SwapDragonCard.png", Symbol.SWAP,1, CardType.SwapDragonCard));
            dragonCards.add(new SpeedDragonCard("/com/example/fierydragons/assets/images/SpeedBoost.png", Symbol.FAST,1, CardType.SpeedDragonCard));
            dragonCards.add(new SlowDragonCard("/com/example/fierydragons/assets/images/SpeedNerf.png", Symbol.SLOW,1, CardType.SlowDragonCard));

            // shuffle the dragon cards
            Collections.shuffle(dragonCards);

        } else {
            // Loop through the list of dragon card data from GameState
            for (HashMap<String, String> dragonCardData : gameState.getDragonCards()) {

                // Extract values from the map
                Symbol symbol = null;
                String cardViewURL = null;
                Integer creatureCount = null;
                CardType type = null;

                if (dragonCardData.containsKey("cardSymbol")) {
                    symbol = Symbol.valueOf(dragonCardData.get("cardSymbol"));
                }
                if (dragonCardData.containsKey("cardViewURL")) {
                    cardViewURL = dragonCardData.get("cardViewURL");
                }
                if (dragonCardData.containsKey("creatureCount")) {
                    creatureCount = Integer.parseInt(dragonCardData.get("creatureCount"));
                }
                if (dragonCardData.containsKey("cardType")) {
                    type = CardType.valueOf(dragonCardData.get("cardType"));
                }

                if (type.equals(CardType.SwapDragonCard)) {
                    // Create swap dragonCard instance
                    dragonCards.add(new SwapDragonCard(cardViewURL, symbol, creatureCount, type));
                } else if (type.equals(CardType.SpeedDragonCard)){
                    // Create movement dragonCard instance
                    dragonCards.add(new SpeedDragonCard(cardViewURL, symbol, creatureCount, type));
                } else if (type.equals(CardType.SlowDragonCard)){
                    // Create movement dragonCard instance
                    dragonCards.add(new SlowDragonCard(cardViewURL, symbol, creatureCount, type));
                } else {
                    // Create movement dragonCard instance
                    dragonCards.add(new MovementDragonCard(cardViewURL, symbol, creatureCount, type));
                }
            }
        }
    }

    /***
     * The method to initialize the players
     * @param playerCount: The number of players
     * @param gameState The current state of the game.
     */
    public void initializePlayer(int playerCount, GameState gameState) {
        if(gameState == null){
            for (int i = 0; i < playerCount; i++) {
                this.player.add(new Player(caveCards.get(i), i, caveCards.get(i)));
            }
            // Start with the first player highlighted
            player.get(0).highlight();
        }
        else {
            for(Map<String, String> playerData : gameState.getPlayers()){
                int playerNumber = Integer.parseInt(playerData.get("playerNumber"));
                Symbol caveSymbol = Symbol.valueOf(playerData.get("caveSymbol"));
                int moveCount = Integer.parseInt(playerData.get("moveCount"));
                int moveSpeed = Integer.parseInt(playerData.get("moveSpeed"));
                Boolean freeFlipUsed = Boolean.parseBoolean(playerData.get("freeFlipUsed"));
                CaveCard playerCave = null;

                for (CaveCard caveCard : caveCards) {
                    if (caveCard.getCardSymbol().equals(caveSymbol)) {
                        playerCave = caveCard;
                            break;
                    }
                }
                Player currentPlayer = new Player(playerNumber, playerCave, freeFlipUsed);
                player.add(currentPlayer);
                currentPlayer.setMoveCount(moveCount);
                currentPlayer.setMoveSpeed(moveSpeed);
                if(gameState.getCurrentPlayerIndex() == playerNumber) {
                    this.setActivePlayer(currentPlayer);
                    this.setCurrentPlayerIndex(playerNumber);
                    currentPlayer.highlight();
                }
            }
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
        for (int col = 2; col < boardSize - 2; col += 3) {
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
        for (int row = 2; row < boardSize - 2; row += 3) {
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
        for (int col = boardSize - 3; col > 1; col -= 3) {
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
        for (int row = boardSize - 3; row > 1; row -= 3) {
            VolcanoCardGroup nextGroup = iter.next();
            boardLocation.add(nextGroup.getLeftVolcano());
            boardLocation.add(nextGroup.getMiddleVolcano());
            boardLocation.add(nextGroup.getRightVolcano());
        }
        // add the last volcano card which is the card below the top right corner
        boardLocation.add(caveVolcanoCardGroup.get(0).getLeftVolcano());
    }

    /***
     * This method is used to move the player from the cave to the board.
     * @param currentPlayer
     * @param flippedCard
     */
    public Movement movePlayerFromCave(Player currentPlayer, DragonCard flippedCard) {
        LocationCard currentLocation = currentPlayer.getCurrentLocation();
        // if the current location is a cave card and the symbol of the cave card is the same as the flipped card
        if (currentLocation.getCardType().equals(CardType.CaveCard) && currentLocation.getCardSymbol().equals(flippedCard.getCardSymbol())) {

            int stepsToMove = flippedCard.getCreatureCount(); // Number of spaces to move;

            if (currentPlayer.getMoveSpeed() < 0) {
                stepsToMove = stepsToMove / 2;
                currentPlayer.setMoveSpeed(1);
            } else if (currentPlayer.getMoveSpeed() > 1) {
                stepsToMove = stepsToMove * currentPlayer.getMoveSpeed();
                currentPlayer.setMoveSpeed(currentPlayer.getMoveSpeed() - 1);
            }

            LocationCard newLocation = getNewLocation(currentPlayer, currentLocation, stepsToMove, 1); // Get new location based on steps to move forward

            if (newLocation != null && !newLocation.hasPlayer()) {
                return new Movement(newLocation, false, currentPlayer, stepsToMove,MoveType.TRAVERSE);
            } else {
                System.out.println("New position is occupied or doesn't exist.");
            }
        }
        return null;
    }

    /***
     * This method is used to move the player on the board.
     * @param currentPlayer
     * @param flippedCard
     * @return
     */
    public Movement movePlayerOnBoard(Player currentPlayer, DragonCard flippedCard) {
        System.out.println("===================================");
        // lets say its 28... 28 - 26 = 2 (extra that needs to be added to player move count)

        LocationCard currentLocation = currentPlayer.getCurrentLocation();

        if (currentLocation.getCardSymbol().equals(flippedCard.getCardSymbol())) {
            int stepsToMove = flippedCard.getCreatureCount(); // Number of spaces to move

            if (currentPlayer.getMoveSpeed() < 1) {
                stepsToMove = stepsToMove / 2;
                currentPlayer.setMoveSpeed(1);
            } else if (currentPlayer.getMoveSpeed() > 1) {
                stepsToMove = stepsToMove * currentPlayer.getMoveSpeed();
                currentPlayer.setMoveSpeed(currentPlayer.getMoveSpeed() - 1);
            }

            LocationCard newLocation = getNewLocation(currentPlayer, currentLocation, stepsToMove, 1); // Get new location based on steps to move forward

            System.out.println("stepsToMove: " + stepsToMove);

            System.out.println("newLocation does not have player: " + !newLocation.hasPlayer());

            // if player does not move
            if (currentLocation == newLocation) {
                return null;
            }

            // if the next location does not have a player
            if (!newLocation.hasPlayer()) {
                return new Movement(newLocation, false, currentPlayer, stepsToMove, MoveType.TRAVERSE);
            }

        } else if (flippedCard.getCardSymbol().equals(Symbol.PirateDragon)) {
            System.out.println("pirate dragon hehe");
            int stepsToMove = flippedCard.getCreatureCount(); // Number of spaces to move

            if ((currentPlayer.getMoveSpeed() < 0) || (currentPlayer.getMoveSpeed() > 1)) {
                stepsToMove = stepsToMove * 2;
                currentPlayer.setMoveSpeed(1);
            }

            LocationCard newLocation = getNewLocation(currentPlayer, currentLocation, stepsToMove, -1); // Get new location based on steps to move backward
            if (newLocation != null && !newLocation.hasPlayer()) {
                System.out.println("Will Move backwards");
                return new Movement(newLocation, false, currentPlayer, -stepsToMove, MoveType.TRAVERSE);
            }
        }
        return null;
    }


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
            else if(destination.getCardType().equals(CardType.VolcanoCard) && destination.hasLinkedCard() &&
                    playerMoves == 25 && direction > 0 &&
                    destination.getLinkedCard().getCardType().equals(CardType.CaveCard) &&
                    player.getCave().getCardSymbol().equals(destination.getLinkedCard().getCardSymbol())
                ) {
 
                if (i == stepsToMove - 1) {
                    return destination.getLinkedCard();

                } else { return currentLocation; }

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

    public int getDistance(int x1, int y1, int x2, int y2, int N) {

        // Get the position index in the clockwise order
        int pos1 = getClockwisePosition(x1, y1, N);
        int pos2 = getClockwisePosition(x2, y2, N);
        System.out.println("Active Player" + pos1);
        System.out.println("Target Player" + pos2);
        // Calculate the clockwise distance

        int clockwiseDistance = 0;
        int counterclockwiseDistance = 0;
        int perimeter = 4 * (N - 1);
        if(pos1 > pos2){
            clockwiseDistance =  perimeter - (pos1 - pos2);
            counterclockwiseDistance = pos1 - pos2;
        }
        else{
            clockwiseDistance =  pos2 - pos1;
            counterclockwiseDistance = perimeter - (pos2 - pos1);
        }

        // Determine the shortest distance and its direction
        if (clockwiseDistance <= counterclockwiseDistance) {
            return clockwiseDistance;
        } else {
            return -counterclockwiseDistance;
        }
    }

    private int getClockwisePosition(int x, int y, int N) {
        if (x == 0) {
            // Top row (moving right)
            return y;
        } else if (y == N - 1) {
            // Right column (moving down)
            return (N - 1) + x;
        } else if (x == N - 1) {
            // Bottom row (moving left)
            return (3 * (N - 1)) - y;
        } else if (y == 0) {
            // Left column (moving up)
            return (4 * (N - 1)) - x;
        } else {
            // This should never happen for a valid N x N grid traversal
            return -1;
        }
    }

    public Movement swapPlayers(Player activePlayer) {
        int minimum_distance =  999;
        Player swappedPlayer = null;
        int activePlayer_row = 0;
        int activePlayer_col = 0;
        int gameBoardPlayer_row = 0;
        int gameBoardPlayer_col = 0;

        if(activePlayer.getCurrentLocation().getCardType().equals(CardType.VolcanoCard)){
            activePlayer_row = activePlayer.getCurrentLocation().getCurrentRow();
            activePlayer_col = activePlayer.getCurrentLocation().getCurrentColumn();
        }else if(activePlayer.getCurrentLocation().getCardType().equals(CardType.CaveCard)){
            activePlayer_row = activePlayer.getCurrentLocation().getLinkedCard().getCurrentRow();
            activePlayer_col = activePlayer.getCurrentLocation().getLinkedCard().getCurrentColumn();
        }

        System.out.println("active player row:" + activePlayer_row);
        System.out.println("active player col:" + activePlayer_col);

        // for each player in the gameboard
        for (Player gameBoardPlayer : this.getPlayer()) {
            // if it is not in the cave and the player is not the current active player
            if (!(gameBoardPlayer.getCurrentLocation().getCardType().equals(CardType.CaveCard)) &&
                    gameBoardPlayer.getNumber() != activePlayer.getNumber()) {
                // calculate the manhatten distance
                // get gameBoardPlayer row and column
                gameBoardPlayer_row = gameBoardPlayer.getCurrentLocation().getCurrentRow();
                gameBoardPlayer_col = gameBoardPlayer.getCurrentLocation().getCurrentColumn();

                int distance = getDistance(activePlayer_row,activePlayer_col, gameBoardPlayer_row, gameBoardPlayer_col, getBoardSize());
                System.out.println("Distance: " + distance);
                if(Math.abs(distance) < minimum_distance){
                    minimum_distance = distance;
                    swappedPlayer = gameBoardPlayer;
                }
            }
        }


        if(swappedPlayer != null){
            System.out.println("target player row:" + swappedPlayer.getCurrentLocation().getCurrentRow());
            System.out.println("target player col:" + swappedPlayer.getCurrentLocation().getCurrentColumn());
            System.out.println("Swap distance: " + minimum_distance);
            return new Movement(true,activePlayer, swappedPlayer, minimum_distance, MoveType.SWAP);
        }else{
            return null;
        }

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
     *
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
     *
     * @param board: The GridPane
     */
    public void setBoard(GridPane board) {
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
     *
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
     *
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

    /**
     * Saves the current state of the game into the provided GameState object.
     *
     * This method populates the provided GameState object with the current state of the game,
     * including volcano card groups, cave volcano card groups, cave cards, player locations,
     * dragon cards, and the current player index.
     *
     * @param gameState The GameState object to store the game state.
     */
    public void saveGameState(GameState gameState){

        ArrayList<ArrayList<HashMap<String, String>>> volcanoCardGroupList = new ArrayList<>();
        for (VolcanoCardGroup volcanoCardGroup : volcanoCardGroup){
            volcanoCardGroupList.add(volcanoCardGroup.saveVolcanoCardGroup());
        }

        ArrayList<ArrayList<HashMap<String, String>>> caveVolcanoCardGroupList = new ArrayList<>();
        for (VolcanoCardGroup caveVolcanoCardGroup : caveVolcanoCardGroup){
            caveVolcanoCardGroupList.add(caveVolcanoCardGroup.saveVolcanoCardGroup());
        }

        ArrayList<HashMap<String, String>> caveCardList = new ArrayList<>();
        for (CaveCard caveCard : caveCards){
            caveCardList.add(caveCard.saveCard());
        }

        ArrayList<HashMap<String, String>> playerLocationList = new ArrayList<>();
        for (Player player : player) {
            playerLocationList.add(player.savePlayer());
        }

        ArrayList<HashMap<String, String>> dragonCardList = new ArrayList<>();
        for (DragonCard dragonCard : dragonCards){
            dragonCardList.add(dragonCard.saveCard());
        }

        gameState.setVolcanoCardGroups(volcanoCardGroupList);
        gameState.setCaveVolcanoCardGroups(caveVolcanoCardGroupList);
        gameState.setCaves(caveCardList);
        gameState.setPlayers(playerLocationList);
        gameState.setDragonCards(dragonCardList);
        gameState.setCurrentPlayerIndex(currentPlayerIndex);

    }
}