package com.example.fierydragons.FXMLController;

import com.example.fierydragons.*;
import com.example.fierydragons.Cards.*;
import com.google.gson.GsonBuilder;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.IOException;
import java.io.FileWriter;
import com.google.gson.Gson;

import java.util.*;

/**
 * Controller class for the gameboard
 */
public class FieryDragonGameBoardController {

    @FXML
    public GridPane board; // the gridpane that will house the volcano card
    @FXML
    public AnchorPane background; // the background of the game screen
    @FXML
    private StackPane CaveStack1, CaveStack2, CaveStack3, CaveStack4; // the stack pane that houses the cave cards
    @FXML
    private VBox PlayerInfoBox; // the vbox that houses the game information
    @FXML
    private Label movementInfoLabel; // the label that will display the movement information
    private FieryDragonsGameBoard gameBoard; // the gameboard object
    private ArrayList<FlipCardPane> unCoveredDragonCard = new ArrayList<>();
    @FXML
    private Button freeFlipButton;
    private GameState gameState = null;

    private final ArrayList<HashMap<String, Integer>> dragonCardLocationList = new ArrayList<>();

    /**
     * Initializes the game board.
     *
     * This method performs the following steps to set up the game board:
     * - Creates a list of starting points for cave stacks.
     * - Initializes the game board object with the starting points and board layout.
     * - Initializes the elements of the game board with the current game state.
     * - Sets up the UI for the game board.
     * - Initializes the information card for the game board.
     * - Updates the state of the free flip button based on the active player.
     */
    public void initializeGameBoard() {
        ArrayList<StackPane> starting_point = new ArrayList<>(
                Arrays.asList(CaveStack1, CaveStack2, CaveStack3, CaveStack4));
        // create the gameboard object
        gameBoard = FieryDragonsGameBoard.getGameBoardInstance(starting_point, board);
        // initialize the elements of the gameboard
        gameBoard.initializeBoard(gameState);
        // set up the UI for the gameboard
        setUpUI(gameBoard);
        // initialize the info box
        initializeInfoCard(gameBoard);

        // Update free flip button state based on the active player
        updateFreeFlipButtonState();

    }


    /**
     * Flips all dragon cards on the board briefly to reveal their fronts.
     * This method can only be used once per player. After flipping, the cards
     * will be flipped back to their original state.
     *
     * Human values:
     * - Fairness: Ensures each player has an equal opportunity to use the free flip.
     * - Memory Skills: Encourages players to use their memory to remember the cards' positions.
     * - Engagement: Keeps players engaged by providing a strategic advantage when used wisely.
     */
    public void freeFlipCards() {
        // Check if the active player has already used their free flip
        if (!gameBoard.getActivePlayer().isFreeFlipUsed()) {
            // Set the free flip as used for the active player
            gameBoard.getActivePlayer().setFreeFlipUsed(true);

            // Create a list to hold the FlipCardPane objects
            List<FlipCardPane> flipCardPanes = new ArrayList<>();

            // Iterate through all the nodes in the board GridPane
            for (Node node : board.getChildren()) {
                // Check if the node is an instance of FlipCardPane
                if (node instanceof FlipCardPane) {
                    FlipCardPane flipCardPane = (FlipCardPane) node;
                    // Flip the card to show its front
                    flipCardPane.instantFlip();
                    // Add the flipped card to the list
                    flipCardPanes.add(flipCardPane);
                }
            }

            // Create a pause transition to show the cards for a brief period
            PauseTransition pause = new PauseTransition(Duration.seconds(3)); // Adjust the duration as needed
            pause.setOnFinished(event -> {
                // After the pause, flip all cards back to their original state
                for (FlipCardPane flipCardPane : flipCardPanes) {
                    flipCardPane.instantUnflip();
                }
            });
            // Start the pause transition
            pause.play();

            // Update the UI to disable the button since the free flip is used
            updateFreeFlipButtonState();
        }
    }

    /**
     * Updates the state of the free flip button based on the active player's usage.
     * If the active player has used their free flip, the button is disabled.
     * Otherwise, it is enabled.
     *
     * Human values:
     * - Fairness: Ensures that the game remains fair by disabling the button after use.
     */
    private void updateFreeFlipButtonState() {
        // Check if the active player has used their free flip
        if (gameBoard.getActivePlayer().isFreeFlipUsed()) {
            // Disable the free flip button
            freeFlipButton.setDisable(true);
        } else {
            // Enable the free flip button
            freeFlipButton.setDisable(false);
        }
    }

    /**
     * Handles the action event when the free flip button is pressed.
     * It triggers the flipping of all dragon cards and updates the UI accordingly.
     *
     * @param event The action event triggered by pressing the free flip button.
     *
     * Human values:
     * - Engagement: Increases player engagement by providing a strategic action.
     */
    @FXML
    private void handleFreeFlip(ActionEvent event) {
        // Call the method to flip all cards
        freeFlipCards();
        // Update the UI to reflect the usage of the free flip
        updateUIAfterFreeFlip();
    }

    /**
     * Updates the movement info label to inform the player that the free flip has been used.
     * This method is called after the free flip action is performed.
     *
     * Human values:
     * - Communication: Clearly communicates game state changes to the player.
     */
    private void updateUIAfterFreeFlip() {
        // Set the movement info label text to inform the player
        movementInfoLabel.setText("Free flip used. Memorize the cards!");
    }


    /***
     * Method to set up the UI for the gameboard
     * @param gameBoard: The GameBoard obejct
     */
    private void setUpUI(FieryDragonsGameBoard gameBoard) {
        // create the UI for the volcano cards
        placeVolcanoCard(gameBoard.getCaveVolcanoCardGroup(), gameBoard.getVolcanoCardGroup());
        // create the UI for the cave cards
        placeCave(gameBoard.getCaveCards(), gameBoard.getStarting_point(), gameBoard.getBoardLocation());
        // create the UI for the dragon cards
        if(gameState == null){
            placeDragonCards(gameBoard.getDragonCards());
        }else{
            loadDragonCards(gameBoard.getDragonCards());
        }
        // create the UI for the players
        placePlayer(gameBoard.getPlayer());
        // initialise movement info label
        updateMovementLabel("Welcome to Fiery Dragons Game");
    }

    /***
     * Method to create the UI for the players
     * @param players: An arraylist of the players of the game
     */
    private void placePlayer(ArrayList<Player> players) {
        if (gameState == null) {
            for (int i = 0; i < players.size(); i++) {
                gameBoard.getStarting_point().get(i).getChildren().add(players.get(i).getToken());
                System.out.println("Placed player " + i + " in starting cave.");
            }
        } else {
            for (HashMap<String, String> playerData : gameState.getPlayers()) {
                int playerNumber = Integer.parseInt(playerData.get("playerNumber"));
                int row = Integer.parseInt(playerData.get("row"));
                int column = Integer.parseInt(playerData.get("column"));
                System.out.println("Player Number: " + playerNumber);
                System.out.println("Row: " + row);
                System.out.println("Column: " + column);

                if (row == -1 && column == -1) {
                    gameBoard.getStarting_point().get(playerNumber).getChildren().add(players.get(playerNumber).getToken());
                    System.out.println("Placed player " + playerNumber + " in starting cave which is a" + gameBoard.getStarting_point().get(playerNumber).getId() + " cave." );

                    System.out.println("=====PLAYER DETAILS=====");
                    System.out.println("Player Number: " + playerNumber);
                    System.out.println("Row: " + row);
                    System.out.println("Column: " + column);
                    System.out.println("Player Token: " + players.get(playerNumber).getToken());
                    System.out.println("Player Token Parent: " + players.get(playerNumber).getToken().getParent());
                    System.out.println("Player Cave: " + gameBoard.getStarting_point().get(playerNumber).getId());
                    System.out.println("Player move count: " + playerData.get("moveCount"));
                    System.out.println("========================");


                } else {
                    board.getChildren().add(players.get(playerNumber).getToken());
                    GridPane.setRowIndex(players.get(playerNumber).getToken(), row);
                    GridPane.setColumnIndex(players.get(playerNumber).getToken(), column);
                    System.out.println("Placed player " + playerNumber + " at row " + row + " and column " + column + ".");

                    System.out.println("DEBUGGING");
                    System.out.println("Player location card: " + players.get(playerNumber).getToken().getParent());

                    // refresh player turn and location
                    for (int i = 0; i < players.size(); i++) {
                        nextPlayerTurn();
                    }

                }

            }
        }
    }


    /***
     * A method to randomly choose 16 locations
     * @param width: The width of the limit of the location zone
     * @param height: The Height of the limit of the location zone
     * @return An arraylist of tuples of the i j location
     */
    private ArrayList<Pair<Integer, Integer>> chooseLocation(int width, int height) {
        ArrayList<Pair<Integer, Integer>> positions = new ArrayList<>();

        // Step 1: Generate all possible (x, y) positions
        for (int x = 1; x <= width; x++) {
            for (int y = 1; y <= height; y++) {
                positions.add(new Pair<>(x, y));
            }
        }

        // Step 2: Shuffle the list of positions to randomize
        Collections.shuffle(positions);

        // Step 3: Select the first position from the shuffled list
        return positions; // Unique random position
    }

    /***
     * Method to create the UI for the dragon cards
     * @param dragonCards An arraylist of the dragon cards
     */
    private void placeDragonCards(ArrayList<DragonCard> dragonCards) {

        // ensure arraylist is empty before initialising cards
        dragonCardLocationList.clear();

        //An arraylist of the chosen i j location of the GridPane
        ArrayList<Pair<Integer, Integer>> coords = chooseLocation(5, 5);
        //For each dragon cards
        for (int i = 0; i < dragonCards.size(); i++) {
            // get the coordinate
            Pair<Integer, Integer> cardCoord = coords.get(i);
            DragonCard dragonCard = dragonCards.get(i);
            // create a stackpane of the dragon card
            FlipCardPane flipCardPaneDragonCard = new FlipCardPane(dragonCard);
            // add the stackpane of the dragon card to the gridpane
            board.add(flipCardPaneDragonCard, cardCoord.getValue(), cardCoord.getKey());
            // set an onclick listener for the stackpane dragon card
            setDragonCardClickListener(flipCardPaneDragonCard);

            // Store the coordinates in gameState
            HashMap<String, Integer> dragonCardLocation = new HashMap<>();
            dragonCardLocation.put("row", cardCoord.getKey());
            dragonCardLocation.put("column", cardCoord.getValue());
            dragonCardLocationList.add(dragonCardLocation);
        }
    }

    /***
     * Method to load the UI for the dragon cards from GameState saved
     * @param dragonCards An arraylist of the dragon cards
     */
    private void loadDragonCards(ArrayList<DragonCard> dragonCards) {
        ArrayList<HashMap<String, Integer>> dragonCardLocations = gameState.getDragonCardLocations();

        for (int i = 0; i < dragonCards.size(); i++) {
            DragonCard dragonCard = dragonCards.get(i);
            HashMap<String, Integer> location = dragonCardLocations.get(i);

            int row = location.get("row");
            int column = location.get("column");

            HashMap<String, Integer> dragonCardLocation = new HashMap<>();
            dragonCardLocation.put("row", row);
            dragonCardLocation.put("column", column);
            dragonCardLocationList.add(dragonCardLocation);

            // create a stackpane of the dragon card
            FlipCardPane flipCardPaneDragonCard = new FlipCardPane(dragonCard);
            // add the stackpane of the dragon card to the gridpane
            board.add(flipCardPaneDragonCard, column, row);
            // set an onclick listener for the stackpane dragon card
            setDragonCardClickListener(flipCardPaneDragonCard);

        }
    }


    /**
     * Method to create the UI for the cave cards
     *
     * @param caves:         An arraylist of the cave cards
     * @param caveLocations: An arraylist of the stack pane cave location
     * @param boardLocation: An arraylist of the board location
     */
    private void placeCave(ArrayList<CaveCard> caves, ArrayList<StackPane> caveLocations, ArrayList<LocationCard> boardLocation) {

        System.out.println("caveCards size: " + gameBoard.getCaveCards().size());
        // for each cave card create a circle that has an image pattern on it and add it to the stackpane
        for (int i = 0; i < gameBoard.getCaveCards().size(); i++) {
            Circle caveCircle = new Circle(45);
            caveCircle.setFill(new ImagePattern(caves.get(i).getCardView().getImage()));
            caveLocations.get(i).getChildren().add(caveCircle);
        }
    }

    /***
     * Method to set the UI a volcano card
     * @param volcanoCard: A volcano Card
     * @param col: the column index of the volcano card in the gridpane
     * @param row: the row index of the volcano card in the gridpane
     */
    private void createVolcanoCardPane(VolcanoCard volcanoCard, int col, int row) {

        // set the rotation of the image based on its location
        if (row == 0) {
            volcanoCard.getCardView().setRotate(180);
        } else if (col == gameBoard.getBoardSize() - 1 && row != gameBoard.getBoardSize() - 1) {
            volcanoCard.getCardView().setRotate(-90);
        } else if (col == 0 && row != gameBoard.getBoardSize() - 1) {
            volcanoCard.getCardView().setRotate(90);
        }

        // add it to the gridpane
        board.add(volcanoCard.getCardView(), col, row);
        // set the row col of the volcano card
        volcanoCard.setCurrentRow(row);
        volcanoCard.setCurrentColumn(col);


    }

    /**
     * Method to go through each volcano card to set the UI
     *
     * @param caveVolcanoCardGroup: A list of volcano card tile groups that has a cave linked to it
     * @param volcanoCardGroup:     A list of volcano card tile groups that has no cave linked to it
     */
    private void placeVolcanoCard(ArrayList<VolcanoCardGroup> caveVolcanoCardGroup, ArrayList<VolcanoCardGroup> volcanoCardGroup) {

        // get the board size
        int boardSize = gameBoard.getBoardSize();
        // An iterator to move through the volcano card group tiles with no cave link to it without using index
        Iterator<VolcanoCardGroup> iter = volcanoCardGroup.iterator();
        // create the UI for the top left corner volcano cards
        createVolcanoCardPane(caveVolcanoCardGroup.get(0).getMiddleVolcano(), 0, 0);
        createVolcanoCardPane(caveVolcanoCardGroup.get(0).getRightVolcano(), 1, 0);

        // keep creating the UI for the next volcano card group tiles until u reach the top right corner
        for (int col = 2; col < boardSize - 2; col += 3) {
            VolcanoCardGroup nextGroup = iter.next();
            createVolcanoCardPane(nextGroup.getLeftVolcano(), col, 0);
            createVolcanoCardPane(nextGroup.getMiddleVolcano(), col + 1, 0);
            createVolcanoCardPane(nextGroup.getRightVolcano(), col + 2, 0);
        }
        // create the UI for the top right corner volcano cards
        createVolcanoCardPane(caveVolcanoCardGroup.get(1).getLeftVolcano(), boardSize - 2, 0);
        createVolcanoCardPane(caveVolcanoCardGroup.get(1).getMiddleVolcano(), boardSize - 1, 0);
        createVolcanoCardPane(caveVolcanoCardGroup.get(1).getRightVolcano(), boardSize - 1, 1);

        // keep creating the UI for the next volcano card group tiles until u reach the bottom right corner
        for (int row = 2; row < boardSize - 2; row += 3) {
            VolcanoCardGroup nextGroup = iter.next();
            createVolcanoCardPane(nextGroup.getLeftVolcano(), boardSize - 1, row);
            createVolcanoCardPane(nextGroup.getMiddleVolcano(), boardSize - 1, row + 1);
            createVolcanoCardPane(nextGroup.getRightVolcano(), boardSize - 1, row + 2);
        }

        // create the UI for the bottom right corner volcano cards
        createVolcanoCardPane(caveVolcanoCardGroup.get(2).getLeftVolcano(), boardSize - 1, boardSize - 2);
        createVolcanoCardPane(caveVolcanoCardGroup.get(2).getMiddleVolcano(), boardSize - 1, boardSize - 1);
        createVolcanoCardPane(caveVolcanoCardGroup.get(2).getRightVolcano(), boardSize - 2, boardSize - 1);

        // keep creating the UI for the next volcano card group tiles until u reach the bottom left corner
        for (int col = boardSize - 3; col > 1; col -= 3) {
            VolcanoCardGroup nextGroup = iter.next();
            createVolcanoCardPane(nextGroup.getLeftVolcano(), col, boardSize - 1);
            createVolcanoCardPane(nextGroup.getMiddleVolcano(), col - 1, boardSize - 1);
            createVolcanoCardPane(nextGroup.getRightVolcano(), col - 2, boardSize - 1);
        }

        // create the UI for the bottom left corner volcano cards
        createVolcanoCardPane(caveVolcanoCardGroup.get(3).getLeftVolcano(), 1, boardSize - 1);
        createVolcanoCardPane(caveVolcanoCardGroup.get(3).getMiddleVolcano(), 0, boardSize - 1);
        createVolcanoCardPane(caveVolcanoCardGroup.get(3).getRightVolcano(), 0, boardSize - 2);

        // keep creating the UI for the next volcano card group tiles until u reach the top left corner
        for (int row = boardSize - 3; row > 1; row -= 3) {
            VolcanoCardGroup nextGroup = iter.next();
            createVolcanoCardPane(nextGroup.getLeftVolcano(), 0, row);
            createVolcanoCardPane(nextGroup.getMiddleVolcano(), 0, row - 1);
            createVolcanoCardPane(nextGroup.getRightVolcano(), 0, row - 2);
        }
        // create the UI for the last volcano card which is the card below the top right corner
        createVolcanoCardPane(caveVolcanoCardGroup.get(0).getLeftVolcano(), 0, 1);

        if (gameState != null){
            //Deal with player location
            for(Map<String, String> playerData : gameState.getPlayers()){
                int playerNumber = Integer.parseInt(playerData.get("playerNumber"));
                int row = Integer.parseInt(playerData.get("row"));
                int column = Integer.parseInt(playerData.get("column"));
                Symbol caveSymbol = Symbol.valueOf(playerData.get("caveSymbol"));
                LocationCard location = null;
                CaveCard playerCave = null;

                for(VolcanoCardGroup volGroup : gameBoard.getVolcanoCardGroup()){
                    VolcanoCard leftVolcanoCard = volGroup.getLeftVolcano();
                    VolcanoCard middleVolcanoCard = volGroup.getMiddleVolcano();
                    VolcanoCard rightVolcanoCard = volGroup.getRightVolcano();
                    if (cardMatchesLocation(leftVolcanoCard, row, column)) {
                        location = leftVolcanoCard;
                        break;
                    }else if(cardMatchesLocation(middleVolcanoCard, row, column)){
                        location = middleVolcanoCard;
                        break;
                    }else if(cardMatchesLocation(rightVolcanoCard, row, column)){
                        location = rightVolcanoCard;
                        break;
                    }
                }
                for(VolcanoCardGroup caveVolGroup : gameBoard.getCaveVolcanoCardGroup()) {
                    VolcanoCard leftVolcanoCard = caveVolGroup.getLeftVolcano();
                    VolcanoCard middleVolcanoCard = caveVolGroup.getMiddleVolcano();
                    VolcanoCard rightVolcanoCard = caveVolGroup.getRightVolcano();
                    if (cardMatchesLocation(leftVolcanoCard, row, column)) {
                        location = leftVolcanoCard;
                        break;
                    } else if (cardMatchesLocation(middleVolcanoCard, row, column)) {
                        location = middleVolcanoCard;
                        break;
                    } else if (cardMatchesLocation(rightVolcanoCard, row, column)) {
                        location = rightVolcanoCard;
                        break;
                    }
                }
                for (CaveCard caveCard : gameBoard.getCaveCards()) {
                    if (caveCard.getCardSymbol().equals(caveSymbol)) {
                        playerCave = caveCard;
                        break;
                    }
                }
                if (location == null){
                    location = playerCave;
                }
                for (Player player : gameBoard.getPlayer()) {
                    if (player.getNumber() == playerNumber) {
                        player.setCurrentLocation(location);
                        location.setCurrentPlayer(player);
                    }
                }
            }
        }
    }

    /**
     * Checks if the given VolcanoCard matches the specified row and column.
     *
     * This method compares the row and column of the VolcanoCard to the provided
     * row and column values. Additional properties of the card might be checked
     * if needed to determine a match.
     *
     * @param volcanoCard The VolcanoCard to be checked.
     * @param row The row to check against the card's current row.
     * @param column The column to check against the card's current column.
     * @return true if the card's current row and column match the provided values, false otherwise.
     */
    private boolean cardMatchesLocation(VolcanoCard volcanoCard, int row, int column) {
        // Implement the logic to check if the card matches the given row and column
        // This might involve checking additional properties of the card
        return volcanoCard.getCurrentRow() == row && volcanoCard.getCurrentColumn() == column;
    }

    /**
     * Method to set the onClickListener to each dragon card
     *
     * @param dragonCardPane: The stackpane with the dragon card
     */
    private void setDragonCardClickListener(FlipCardPane dragonCardPane) {

        dragonCardPane.setOnMouseClicked(event -> {

            // boolean indicating whether turn is over
            final boolean[] isTurnOver = { false };

            // the pause transition to pause before flipping back
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));

            // the method to call once it finishes pausing
            pause.setOnFinished(actionEvent -> {
                if (isTurnOver[0]) {
                    endTurn(); // resolve the end turn
                } else {
                    unflipCards();

                    // to create illusion of card flip
                    PauseTransition temp = new PauseTransition(Duration.seconds(0.5));
                    temp.setOnFinished(actionEvent2 -> {
                        reshuffleBoard();
                    });
                    temp.play();

                }
            });

            unCoveredDragonCard.add(dragonCardPane); // remember the flipped card
            dragonCardPane.setDisable(true); // ensure that the flipped card cannot be flipped again
            board.setDisable(true); // make the board unclickable so 1 card is flipped at a time
            Movement currentMovement = dragonCardPane.flip(gameBoard); // call the method to flip the dragon card
            // if the player cannot move
            if (currentMovement == null) {
                isTurnOver[0] = true;
                pause.play(); // play the pause transition
            }

            else if (currentMovement.getMoveType() == MoveType.AUGMENT) {
                if (currentMovement.getCurrentPlayer().getMoveSpeed() < 0) {
                    movementInfoLabel.setText("You have flipped a speed augment card! The dragon cards will now be reshuffled. " +
                            "Steps forward are now halved, and steps backward are doubled.");
                } else {
                    movementInfoLabel.setText("You have flipped a speed augment card! The dragon cards will now be reshuffled. " +
                            "Steps forward and backwards are now doubled.");
                }
                pause.play();

            }

            // if the player move doesn't stop its turn (Normal)
            else if (!currentMovement.isEndTurn()) {
                updatePlayerPositionInGameBoard(currentMovement);
                movementInfoLabel.setText("You have flipped the correct card! Please flip another card"); // if not wrong then show the message
                board.setDisable(false); // else if not wrong then make the board clickable again
            }

            // if the player move stop its turn(Pirate)
            else {
                updatePlayerPositionInGameBoard(currentMovement);
                isTurnOver[0] = true;
                pause.play();
            }
        });


    }

    /**
     * Methods to unflip all cards
     */
    private void unflipCards() {
        // go through each saved flipped card flip it back and then make it clickable
        for (FlipCardPane dragonCardPane : unCoveredDragonCard) {
            dragonCardPane.setDisable(false);
            dragonCardPane.unflip();
        }
        //reset the array list of flipped card
        unCoveredDragonCard = new ArrayList<>();
        // make the board clickable
        board.setDisable(false);

    }

    /**
     * Method to end the current player's turn
     */
    private void endTurn() {
        unflipCards();
        //resolve to next turn()
        nextPlayerTurn();
    }

    // method to change to the next player
    private void nextPlayerTurn() {
        // Unhighlight the current player before changing
        gameBoard.getActivePlayer().unhighlight();

        // Update the current player index
        gameBoard.setCurrentPlayerIndex(((gameBoard.getCurrentPlayerIndex() + 1) % gameBoard.getPlayer().size()));  // like the circular queue from 1008 lols
        gameBoard.setActivePlayer(gameBoard.getPlayer().get(gameBoard.getCurrentPlayerIndex()));  // Update current player

        // Highlight the new current player
        gameBoard.getActivePlayer().highlight();

        // Update the free flip button state for the new active player
        updateFreeFlipButtonState();

        // update movement info label
        movementInfoLabel.setText("Your turn has ended");
    }

    private void updatePlayerPositionInGameBoard(Movement currentMovement) {
        // move the player
        currentMovement.move(gameBoard.getBoardSize());
        if(currentMovement.getMoveType().equals(MoveType.TRAVERSE)){
            // update the player's position in the UI
            if (currentMovement.getDestination().getCardType().equals(CardType.VolcanoCard)) {
                updatePlayerPositionInGrid(currentMovement.getCurrentPlayer(), currentMovement.getDestination().getCurrentRow(), currentMovement.getDestination().getCurrentColumn());
            }else {
                updatePlayerPositionInCave(currentMovement.getCurrentPlayer());
            }
            checkForWin(currentMovement.getCurrentPlayer());
        }else if(currentMovement.getMoveType().equals(MoveType.SWAP)){
            swapPlayersInBoard(currentMovement);

        }

    }

    private void swapPlayersInBoard(Movement currentMovement) {
        LocationCard currentPlayerLocation = currentMovement.getCurrentPlayer().getCurrentLocation();
        LocationCard swappedPlayerLocation = currentMovement.getTargetPlayer().getCurrentLocation();
        // if originally the player is in the cave
        if(swappedPlayerLocation.getCardType().equals(CardType.CaveCard)){
            // swap the target with the active in the cave
            currentMovement.getCurrentPlayer().getCave().getStackPane().getChildren().remove(currentMovement.getCurrentPlayer().getToken());
            currentMovement.getCurrentPlayer().getCave().getStackPane().getChildren().add(currentMovement.getTargetPlayer().getToken());
            board.getChildren().remove(currentMovement.getTargetPlayer().getToken());
            board.add(currentMovement.getCurrentPlayer().getToken(), currentPlayerLocation.getCurrentColumn(), currentPlayerLocation.getCurrentRow());
            GridPane.setHalignment(currentMovement.getCurrentPlayer().getToken(), HPos.CENTER);
            GridPane.setValignment(currentMovement.getCurrentPlayer().getToken(), VPos.CENTER);
        }
        // else if swap with a token on a volcano card
        else if (swappedPlayerLocation.getCardType().equals(CardType.VolcanoCard)) {
            board.getChildren().remove(currentMovement.getTargetPlayer().getToken());
            board.getChildren().remove(currentMovement.getCurrentPlayer().getToken());
            board.add(currentMovement.getCurrentPlayer().getToken(), currentPlayerLocation.getCurrentColumn(), currentPlayerLocation.getCurrentRow());
            board.add(currentMovement.getTargetPlayer().getToken(), swappedPlayerLocation.getCurrentColumn(), swappedPlayerLocation.getCurrentRow());
            GridPane.setHalignment(currentMovement.getCurrentPlayer().getToken(), HPos.CENTER);
            GridPane.setValignment(currentMovement.getCurrentPlayer().getToken(), VPos.CENTER);
            GridPane.setHalignment(currentMovement.getTargetPlayer().getToken(), HPos.CENTER);
            GridPane.setValignment(currentMovement.getTargetPlayer().getToken(), VPos.CENTER);
        }
    }

    private void updatePlayerPositionInCave(Player currentPlayer) {
        // Add player stackpane to cave
        currentPlayer.getCave().getStackPane().getChildren().add(currentPlayer.getToken());
        // Set current player in cave
        currentPlayer.getCave().setCurrentPlayer(currentPlayer);
    }

    private void checkForWin(Player currentPlayer) {
        if (currentPlayer.getPlayerMoveCount() > 26) {
            System.out.println("Player passed the cave");
            // set back the player's move count
            currentPlayer.setPlayerMoveCount(currentPlayer.getPlayerMoveCount() - 24);
        } else if (currentPlayer.getPlayerMoveCount() == 26) {
            System.out.println("Player wins");
            // display win message
            displayWinningMessage("Player " + (currentPlayer.getNumber() + 1), currentPlayer);
        }
    }


    /***
     * This method is used to update the player position in the grid.
     * @param player
     * @param newRow
     * @param newColumn
     */
    private void updatePlayerPositionInGrid(Player player, int newRow, int newColumn) {
        board.getChildren().remove(player.getToken());  // Remove the token from its current position
        board.add(player.getToken(), newColumn, newRow);  // Place the token at the new position
        GridPane.setHalignment(player.getToken(), HPos.CENTER);
        GridPane.setValignment(player.getToken(), VPos.CENTER);
    }


    /**
     * Method to initialize the information column at the right side
     *
     * @param gameBoard: The gameboard object
     */
    private void initializeInfoCard(FieryDragonsGameBoard gameBoard) {

        // get the list of players
        ArrayList<Player> players = gameBoard.getPlayer();

        // for each player create a player card and add it into the information column
        for (int i = 0; i < players.size(); i++) {

            // create a circle that represents the player's token
            Circle token = new Circle(players.get(i).getToken().getRadius(), players.get(i).getToken().getFill());
            VBox details = createPlayerCard(players.get(i));

            HBox playerCard = (HBox) PlayerInfoBox.getChildren().get(i);
            playerCard.getChildren().addAll(token, details);

        }

    }

    /**
     * Method to create the player card VBox
     *
     * @param player: A player
     * @return: A HBox containing the player information
     */
    public VBox createPlayerCard(Player player) {
        // create the Vbox and style it
        VBox playerDetails = new VBox();
        playerDetails.setMinHeight(62);
        playerDetails.setMinWidth(150);
        playerDetails.setSpacing(4);
        playerDetails.setAlignment(Pos.CENTER);
        playerDetails.setPadding(new Insets(2,0,2,0));

        // create a label of the player's name and style it
        Label playerName = new Label("Player " + (player.getNumber() + 1));
        styleLabel(playerName);

        // create a label of the player's current location's creature and style it
        Label caveCreature = new Label(player.getCave().getCardSymbol().name());
        styleLabel(caveCreature);

        if (player.getCave().getCardSymbol().name() == "BabyDragon") {
            caveCreature.setText("Baby Dragon");
        }

        // add all the created labels into the VBox
        playerDetails.getChildren().addAll(playerName, caveCreature);

        // return the player card VBox
        return playerDetails;
    }

    /**
     * Method to style a label
     *
     * @param label The label to be styled
     */
    public void styleLabel(Label label) {
        label.setTextFill(Color.web("#ffffff"));
        Font font = Font.font("Arial", FontWeight.EXTRA_BOLD, 14);
        label.setFont(font);
        label.setAlignment(Pos.CENTER);
    }

    /**
     * The method to change to the game screen
     *
     * @param event: An action event
     * @throws IOException
     */
    public void switchToStartScreen(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        AnchorPane root = FXMLLoader.load(getClass().getResource("/com/example/fierydragons/game-start-screen.fxml"));
        Scene scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();

    }

    @FXML
    /**
     * Method to update the movement label
     */
    public void updateMovementLabel(String message) {
        movementInfoLabel.setText(message);
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

                    Scene scene = new Scene(root, 1280, 720);
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

    /**
     * Saves the current state of the game to a JSON file.
     *
     * This method creates a GameState object, populates it with relevant game data,
     * and then converts it to JSON format using Gson library. The resulting JSON
     * is written to a file named "gameState.json".
     *
     * @see GameState
     */
    public void saveGame() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("gameState.json")) {
            GameState gameState = new GameState();
            gameBoard.saveGameState(gameState);
            gameState.setDragonCardLocations(dragonCardLocationList);

            // Convert the gameInstance object to JSON and save it to a file
            gson.toJson(gameState, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the current game state.
     *
     * @param gameState The new game state to be set.
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Reshuffles the Dragon Cards on the board.
     */
    public void reshuffleBoard() {

        // remove all dragon cards from the board
        board.getChildren().removeIf(node -> node instanceof FlipCardPane);

        // reshuffle them
        ArrayList<DragonCard> currentCards = gameBoard.getDragonCards();
        Collections.shuffle(currentCards);
        gameBoard.setDragonCards(currentCards);

        // add them back to the board
        placeDragonCards(gameBoard.getDragonCards());


    }

}



