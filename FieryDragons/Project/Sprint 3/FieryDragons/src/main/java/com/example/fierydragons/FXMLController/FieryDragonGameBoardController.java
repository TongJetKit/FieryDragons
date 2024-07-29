package com.example.fierydragons.FXMLController;

import com.example.fierydragons.*;
import com.example.fierydragons.Cards.*;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    /***
     * The method to initialize the controller class
     */
    public void initialize(){
        // create an arraylist of the stack pane that houses the cave cards
        ArrayList<StackPane> starting_point = new ArrayList<>(
                Arrays.asList(CaveStack1, CaveStack2, CaveStack3, CaveStack4));
        // create the gameboard object
        gameBoard = FieryDragonsGameBoard.getGameBoardInstance(starting_point, board);
        // initialize the elements of the gameboard
        gameBoard.initializeBoard();
        // set up the UI for the gameboard
        setUpUI(gameBoard);
        // initialize the info box
        initializeInfoCard(gameBoard);
    }

    /***
     * Method to set up the UI for the gameboard
     * @param gameBoard: The GameBoard obejct
     */
    private void setUpUI(FieryDragonsGameBoard gameBoard) {
        // create the UI for the volcano cards
        placeVolcanoCard(gameBoard.getCaveVolcanoCardGroup(), gameBoard.getVolcanoCardGroup());
        // create the UI for the cave cards
        placeCave(gameBoard.getCaveCards(),gameBoard.getStarting_point(),gameBoard.getBoardLocation());
        // create the UI for the dragon cards
        placeDragonCards(gameBoard.getDragonCards());
        // create the UI for the players
        placePlayer(gameBoard.getPlayer());
        // initialise movement info label
        updateMovementLabel("Welcome to Fiery Dragons Game");

    }

    /***
     * Method to create the UI for the players
     * @param player: An arraylist of the players of the game
     */
    private void placePlayer(ArrayList<Player> player) {
        // loop through each players to set the player token on its respective starting cave
        for(int i = 0; i< player.size(); i++){
            gameBoard.getStarting_point().get(i).getChildren().add(player.get(i).getToken());
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

        //An arraylist of the chosen i j location of the GridPane
        ArrayList<Pair<Integer, Integer>> coords = chooseLocation(5,5);
        //For each dragon cards
        for(int i = 0; i< dragonCards.size(); i++){
            // get the coordinate
            Pair<Integer, Integer> cardCoord = coords.get(i);
            DragonCard dragonCard = dragonCards.get(i);
            // create a stackpane of the dragon card
            FlipCardPane flipCardPaneDragonCard = new FlipCardPane(dragonCard);
            // add the stackpane of the dragon card to the gridpane
            board.add(flipCardPaneDragonCard,cardCoord.getValue(), cardCoord.getKey());
            // set an onclick listener for the stackpane dragon card
            setDragonCardClickListener(flipCardPaneDragonCard);
        }
    }

    /**
     * Method to create the UI for the cave cards
     * @param caves: An arraylist of the cave cards
     * @param caveLocations: An arraylist of the stack pane cave location
     * @param boardLocation: An arraylist of the board location
     */
    private void placeCave(ArrayList<CaveCard> caves, ArrayList<StackPane> caveLocations, ArrayList<LocationCard> boardLocation) {

        System.out.println("caveCards size: " + gameBoard.getCaveCards().size());
        // for each cave card create a circle that has an image pattern on it and add it to the stackpane
        for(int i = 0; i< gameBoard.getCaveCards().size(); i++){
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
    private void createVolcanoCardPane(VolcanoCard volcanoCard, int col, int row){

        // set the rotation of the image based on its location
        if(row == 0){
            volcanoCard.getCardView().setRotate(180);
        } else if (col == gameBoard.getBoardSize()-1 && row != gameBoard.getBoardSize()-1) {
            volcanoCard.getCardView().setRotate(-90);
        } else if(col == 0 && row != gameBoard.getBoardSize()-1){
            volcanoCard.getCardView().setRotate(90);
        }

        // add it to the gridpane
        board.add(volcanoCard.getCardView() , col, row);
        // set the row col of the volcano card
        volcanoCard.setCurrentRow(row);
        volcanoCard.setCurrentColumn(col);


    }

    /**
     * Method to go through each volcano card to set the UI
     * @param caveVolcanoCardGroup: A list of volcano card tile groups that has a cave linked to it
     * @param volcanoCardGroup: A list of volcano card tile groups that has no cave linked to it
     */
    private void placeVolcanoCard(ArrayList<VolcanoCardGroup> caveVolcanoCardGroup, ArrayList<VolcanoCardGroup> volcanoCardGroup) {

        // get the board size
        int boardSize = gameBoard.getBoardSize();
        // An iterator to move through the volcano card group tiles with no cave link to it without using index
        Iterator<VolcanoCardGroup> iter = volcanoCardGroup.iterator();
        // create the UI for the top left corner volcano cards
        createVolcanoCardPane(caveVolcanoCardGroup.get(0).getMiddleVolcano(),0,0);
        createVolcanoCardPane(caveVolcanoCardGroup.get(0).getRightVolcano(),1,0);

        // keep creating the UI for the next volcano card group tiles until u reach the top right corner
        for(int col = 2; col< boardSize-2; col+=3){
            VolcanoCardGroup nextGroup = iter.next();
            createVolcanoCardPane(nextGroup.getLeftVolcano(),col,0);
            createVolcanoCardPane(nextGroup.getMiddleVolcano(),col+1,0);
            createVolcanoCardPane(nextGroup.getRightVolcano(),col+2,0);
        }
        // create the UI for the top right corner volcano cards
        createVolcanoCardPane(caveVolcanoCardGroup.get(1).getLeftVolcano(),boardSize-2,0);
        createVolcanoCardPane(caveVolcanoCardGroup.get(1).getMiddleVolcano(),boardSize-1,0);
        createVolcanoCardPane(caveVolcanoCardGroup.get(1).getRightVolcano(),boardSize-1,1);

        // keep creating the UI for the next volcano card group tiles until u reach the bottom right corner
        for(int row = 2; row < boardSize-2; row+=3){
            VolcanoCardGroup nextGroup = iter.next();
            createVolcanoCardPane(nextGroup.getLeftVolcano(),boardSize-1,row);
            createVolcanoCardPane(nextGroup.getMiddleVolcano(),boardSize-1,row+1);
            createVolcanoCardPane(nextGroup.getRightVolcano(),boardSize-1,row+2);
        }

        // create the UI for the bottom right corner volcano cards
        createVolcanoCardPane(caveVolcanoCardGroup.get(2).getLeftVolcano(),boardSize-1,boardSize-2);
        createVolcanoCardPane(caveVolcanoCardGroup.get(2).getMiddleVolcano(),boardSize-1,boardSize-1);
        createVolcanoCardPane(caveVolcanoCardGroup.get(2).getRightVolcano(),boardSize-2,boardSize-1);

        // keep creating the UI for the next volcano card group tiles until u reach the bottom left corner
        for(int col = boardSize-3; col > 1; col-=3){
            VolcanoCardGroup nextGroup = iter.next();
            createVolcanoCardPane(nextGroup.getLeftVolcano(),col, boardSize-1);
            createVolcanoCardPane(nextGroup.getMiddleVolcano(),col-1, boardSize-1);
            createVolcanoCardPane(nextGroup.getRightVolcano(),col-2, boardSize-1);
        }

        // create the UI for the bottom left corner volcano cards
        createVolcanoCardPane(caveVolcanoCardGroup.get(3).getLeftVolcano(),1,boardSize-1);
        createVolcanoCardPane(caveVolcanoCardGroup.get(3).getMiddleVolcano(),0,boardSize-1);
        createVolcanoCardPane(caveVolcanoCardGroup.get(3).getRightVolcano(),0,boardSize-2);

        // keep creating the UI for the next volcano card group tiles until u reach the top left corner
        for(int row = boardSize-3; row >1; row-=3){
            VolcanoCardGroup nextGroup = iter.next();
            createVolcanoCardPane(nextGroup.getLeftVolcano(),0, row);
            createVolcanoCardPane(nextGroup.getMiddleVolcano(),0, row-1);
            createVolcanoCardPane(nextGroup.getRightVolcano(),0, row-2);
        }
        // create the UI for the last volcano card which is the card below the top right corner
        createVolcanoCardPane(caveVolcanoCardGroup.get(0).getLeftVolcano(),0,1);
    }

    /**
     * Method to set the onClickListener to each dragon card
     * @param dragonCardPane: The stackpane with the dragon card
     */
    private void setDragonCardClickListener(FlipCardPane dragonCardPane){

        dragonCardPane.setOnMouseClicked(event -> {

            unCoveredDragonCard.add(dragonCardPane); // remember the flipped card
            dragonCardPane.setDisable(true); // ensure that the flipped card cannot be flipped again
            board.setDisable(true); // make the board unclickable so 1 card is flipped at a time
            boolean isTurnEnd = dragonCardPane.flip(gameBoard); // call the method to flip the dragon card
            // if it flip the wrong card then end the turn
            if(isTurnEnd){
                // the pause transition to pause before flipping back
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                // the method to call once it finishes pausing
                pause.setOnFinished(actionEvent -> {
                    endTurn(); // resolve the end turn
                });
                pause.play(); // play the pause transition

            }else{
                movementInfoLabel.setText("You have flipped the correct card! Please flip another card"); // if not wrong then show the message
                board.setDisable(false); // else if not wrong then make the board clickable again
            }
        });


    }

    /**
     * Method to end the current player's turn
     */
    private void endTurn() {
        // go through each saved flipped card flip it back and then make it clickable
        for(FlipCardPane dragonCardPane: unCoveredDragonCard){
            dragonCardPane.setDisable(false);
            dragonCardPane.unflip();
        }
        //reset the array list of flipped card
        unCoveredDragonCard = new ArrayList<>();
        // make the board clickable
        board.setDisable(false);
        //resolve to next turn()
        nextPlayerTurn();
    }

    // method to change to the next player
    private void nextPlayerTurn(){
        // Unhighlight the current player before changing
        gameBoard.getActivePlayer().unhighlight();

        // Update the current player index
        gameBoard.setCurrentPlayerIndex(((gameBoard.getCurrentPlayerIndex() + 1) % gameBoard.getPlayer().size()));  // like the circular queue from 1008 lols
        gameBoard.setActivePlayer(gameBoard.getPlayer().get(gameBoard.getCurrentPlayerIndex()));  // Update current player

        // Highlight the new current player
        gameBoard.getActivePlayer().highlight();

        // update movement info label
        movementInfoLabel.setText("Your turn has ended due to an occupied position, you flipped a wrong dragon card or you flipped a Pirate Dragon Card");
    }
    

    /**
     * Method to create the info card for the possible dragon cards
     * @param imageUrl: The URL for the image
     * @param name: The creature name of the dragon card
     * @return A HBox of the dragon card information
     */
    private HBox createInfoCard(String imageUrl, String name) {
        // create an info card HBox
        HBox InfoCard = new HBox();
        // set the style for the HBox
        InfoCard.setAlignment(Pos.CENTER_LEFT);
        InfoCard.setSpacing(10);
        // create the ImageView and set style for it
        ImageView image = new ImageView(new Image(getClass().getResourceAsStream(imageUrl)));
        image.setFitWidth(50);
        image.setFitHeight(50);
        // create a label with the dragon card creature name
        Label label = new Label(name);
        // style the label
        styleLabel(label);
        // add the imageview and label to the HBox infocard
        InfoCard.getChildren().addAll(image,label);
        // return the HBox
        return InfoCard;
    }

    /**
     * Method to initialize the information column at the right side
     * @param gameBoard: The gameboard object
     */
    private void initializeInfoCard(FieryDragonsGameBoard gameBoard) {
        // get the list of players
        ArrayList<Player> players = gameBoard.getPlayer();
        // for each player create a player card and add it into the information column
        for(int i = 0; i< players.size(); i++){
            PlayerInfoBox.getChildren().add(createPlayerCard(players.get(i)));
        }
        // add a space in between
        Region space = new Region();
        space.setMinHeight(20);
        PlayerInfoBox.getChildren().add(space);

        // add each Dragon Card information card
        PlayerInfoBox.getChildren().add(createInfoCard("/com/example/fierydragons/assets/images/Bat1.png", "Bat"));
        PlayerInfoBox.getChildren().add(createInfoCard("/com/example/fierydragons/assets/images/Spider1.png", "Spider"));
        PlayerInfoBox.getChildren().add(createInfoCard("/com/example/fierydragons/assets/images/Salamander1.png", "Salamander"));
        PlayerInfoBox.getChildren().add(createInfoCard("/com/example/fierydragons/assets/images/BabyDragon1.png", "Baby Dragon"));
        PlayerInfoBox.getChildren().add(createInfoCard("/com/example/fierydragons/assets/images/PirateDragon1.png", "Pirate Dragon"));
    }

    /**
     * Method to create the player card VBox
     * @param player: A player
     * @return: A Vbox containing the player information
     */
    public VBox createPlayerCard(Player player){
        // create the Vbox and style it
        VBox playerCard = new VBox();
        playerCard.setSpacing(8);
        // create a circle that represents the player's token
        Circle token = new Circle(player.getToken().getRadius(), player.getToken().getFill());
        // create a label of the player's name and style it
        Label playerName = new Label("Player "+(player.getNumber()+1), token);
        styleLabel(playerName);
        // create a label of the player's current location's creature and style it
        Label caveCreature = new Label("Cave Creature: "+ player.getCave().getCardSymbol().name());
        styleLabel(caveCreature);
//        Label nextCreature;
//        nextCreature = new Label("Next Creature: "+ gameBoard.getDestination(player,1,1).getCardSymbol().name());
//        styleLabel(nextCreature);
        // add all the created labels into the VBox
        playerCard.getChildren().addAll(playerName, caveCreature);
        // return the player card VBox
        return playerCard;
    }

    /**
     * Method to style a label
     * @param label The label to be styled
     */
    public void styleLabel(Label label){
        // add a color for the text
        label.setTextFill(Color.web("#ffffff"));
        // set the font for the text
        Font font = Font.font("Arial", FontWeight.EXTRA_BOLD, 15);
        label.setFont(font);
    }

    /**
     * The method to change to the game screen
     * @param event: An action event
     * @throws IOException
     */
    public void switchToStartScreen(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        AnchorPane root = FXMLLoader.load(getClass().getResource("/com/example/fierydragons/game-start-screen.fxml"));
        Scene scene = new Scene(root,1280, 720);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();


    }

    @FXML
    /**
     * Method to update the movement label
     */
    public void updateMovementLabel(String message){
        movementInfoLabel.setText(message);
    }

}
