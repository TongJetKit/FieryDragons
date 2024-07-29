package com.example.fierydragons;

import com.example.fierydragons.Cards.CardType;
import com.example.fierydragons.Cards.LocationCard;

public class Movement {

    private LocationCard destination;
    private boolean endTurn;
    private Player currentPlayer;
    private Player targetPlayer;
    private int moveCount;
    private MoveType moveType;

    public Movement(LocationCard destination, boolean endTurn, Player currentPlayer, int moveCount, MoveType moveType) {
        this.destination = destination;
        this.endTurn = endTurn;
        this.currentPlayer = currentPlayer;
        this.moveCount = moveCount;
        this.moveType = moveType;
    }

    public Movement(boolean endTurn, Player currentPlayer, Player targetPlayer, int moveCount, MoveType moveType) {
        this.endTurn = endTurn;
        this.currentPlayer = currentPlayer;
        this.targetPlayer = targetPlayer;
        this.moveCount = moveCount;
        this.moveType = moveType;
    }

    public LocationCard getDestination() {
        return destination;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public boolean isEndTurn() {
        return endTurn;
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getTargetPlayer() {
        return targetPlayer;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    /**
     * Convert (i, j) coordinates to a linear position on the ring.
     */
    public int getClockwisePosition(int x, int y, int N) {
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

    /**
     * Compare two positions on the ring.
     */
    public boolean isAhead(LocationCard activePlayerLocation, LocationCard targetPlayerLocation, int N) {

        // Get the position index in the clockwise order
        int pos1 = getClockwisePosition(activePlayerLocation.getCurrentRow(), activePlayerLocation.getCurrentColumn(), N);
        int pos2 = getClockwisePosition(targetPlayerLocation.getCurrentRow(), targetPlayerLocation.getCurrentColumn(), N);

        return pos1 > pos2;
    }

    /**
     * To move the Player
     * @param boardSize the size of the board
     */
    public void move(int boardSize){
        if(moveType.equals(MoveType.TRAVERSE)){
            //remove player from location card
            currentPlayer.getCurrentLocation().removeCurrentPlayer();
            // add the player to the new location
            currentPlayer.setCurrentLocation(destination);
            destination.setCurrentPlayer(currentPlayer);
            // update player move count
            currentPlayer.setPlayerMoveCount(currentPlayer.getPlayerMoveCount() + getMoveCount());

            System.out.println("After moving, the player is at " + getCurrentPlayer().getCurrentLocation().getCardSymbol());
            System.out.println("Player move count: " + getCurrentPlayer().getPlayerMoveCount());
        }
        // if it is a swap move
        else if (moveType.equals(MoveType.SWAP)) {
            // get the current and target player location
            LocationCard currentPlayerLocation = currentPlayer.getCurrentLocation();
            LocationCard swappedPlayerLocation = targetPlayer.getCurrentLocation();

            // if the current player is at a cave then +1 to the move count to offset the movement
            if(currentPlayerLocation.getCardType().equals(CardType.CaveCard)){
                currentPlayer.setPlayerMoveCount(currentPlayer.getPlayerMoveCount() + (getMoveCount() + 1));
                targetPlayer.setPlayerMoveCount(targetPlayer.getPlayerMoveCount() - (getMoveCount() + 1));
            }else{
                currentPlayer.setPlayerMoveCount(currentPlayer.getPlayerMoveCount() + getMoveCount());
                targetPlayer.setPlayerMoveCount(targetPlayer.getPlayerMoveCount() - getMoveCount());
            }


            // reset the player count if it went past the step
            if (currentPlayer.getPlayerMoveCount() > 26) {
                System.out.println("Active Player passed the cave");
                // set back the player's move count
                currentPlayer.setPlayerMoveCount(currentPlayer.getPlayerMoveCount() - 24);
            }
            if(targetPlayer.getPlayerMoveCount() > 26){
                System.out.println("Target Player passed the cave");
                targetPlayer.setPlayerMoveCount(targetPlayer.getPlayerMoveCount() - 24);
            }

            // swap the active and the target
            currentPlayer.setCurrentLocation(swappedPlayerLocation);
            targetPlayer.setCurrentLocation(currentPlayerLocation);
            currentPlayerLocation.setCurrentPlayer(targetPlayer);
            swappedPlayerLocation.setCurrentPlayer(currentPlayer);

            System.out.println("After swapping, the player move count: " + getCurrentPlayer().getPlayerMoveCount());
            System.out.println("After swapping, the target move count: " + getTargetPlayer().getPlayerMoveCount());
            System.out.println("After swapping, the player is at " + getCurrentPlayer().getCurrentLocation().getCardSymbol() + " " + getCurrentPlayer().getCurrentLocation().getCardType());
            System.out.println("After swapping, the target is at " + getTargetPlayer().getCurrentLocation().getCardSymbol() + " " + getTargetPlayer().getCurrentLocation().getCardType());
        }

    }


}
