package com.webcheckers.appl;

import com.webcheckers.model.*;
import com.webcheckers.util.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The object to represent the Web Checkers game.
 * Contains game information, and red and white player information.
 *
 * @author Elyse Castles
 * @author Jasmine Liang
 */
public class Game {

    //Attributes
    private Player red;
    private Player white;
    private Player winner;
    private Player loser;
    private Color activeColor;
    private Board board;
    private ArrayList<Move> currentMoves;
    private ArrayList<Move> unchangeableMoves;
    private Map<Move, Piece> deletePiece;
    private Map<Move, Piece> kinged;
    private boolean resigned;

    //Constructor

    /**
     * Makes a new Game instance and assigns two players.
     *
     * @param red:   red player object
     * @param white: white player object
     */
    public Game(Player red, Player white) {
        this.red = red;
        this.white = white;
        this.board = new Board();
        this.activeColor = Color.RED;
        this.currentMoves = new ArrayList<>();
        this.unchangeableMoves = new ArrayList<>();
        this.deletePiece = new HashMap<>();
        this.kinged = new HashMap<>();
        this.resigned = false;
        this.winner = null;
        this.loser = null;
    }

    public Game(Player red, Player white, Space[][] board) {
        this.red = red;
        this.white = white;
        this.board = new Board(board);
        this.activeColor = Color.RED;
        this.currentMoves = new ArrayList<>();
        this.unchangeableMoves = new ArrayList<>();
        this.deletePiece = new HashMap<>();
        this.kinged = new HashMap<>();
        this.resigned = false;
        this.winner = null;
    }

    //public methods

    /**
     * Return the red player for a game
     *
     * @return red player
     */
    public Player getRedPlayer() {
        return red;
    }

    /**
     * Return the white player for a game
     *
     * @return white player
     */
    public Player getWhitePlayer() {
        return white;
    }

    /**
     * Returns the active color, or who's turn it is
     *
     * @return Color
     */
    public Color getActiveColor() {
        return activeColor;
    }

    /**
     * Returns the list of unchangeable moves
     *
     * @return unchangeableMoves
     */
    public ArrayList<Move> getUnchangeable() {
        return unchangeableMoves;
    }

    /**
     * Return the list of current moves for a turn
     *
     * @return currentMoves
     */
    public ArrayList<Move> getCurrent() {
        return currentMoves;
    }

    /**
     * Returns the map of pieces to delete
     *
     * @return deletePiece
     */
    public Map<Move, Piece> getDelete() {
        return deletePiece;
    }

    /**
     * Returns the map of pieces to be kinged
     *
     * @return kinged
     */
    public Map<Move, Piece> getKingged() {
        return kinged;
    }

    /**
     * Set active color to change turn
     *
     * @param color active color
     */
    public void setActiveColor(Color color) {
        this.activeColor = color;
    }

    /**
     * Set the winner of a game
     *
     * @param winner winning player
     */
    public void setWinner(Player winner) {
        this.winner = winner;
    }

    /**
     * Return the winning player
     *
     * @return player
     */
    public Player getWinner() {
        return winner;
    }

    /**
     * Return the losing player
     *
     * @return player
     */
    public Player getLoser() {
        return loser;
    }

    /**
     * Return the board
     *
     * @return board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Return the board view
     *
     * @return baord view
     */
    public BoardView getBoardView() {
        return board.getBoardView();
    }

    /**
     * Return boolean whether game is won or lost
     *
     * @return boolean
     */
    public boolean isGameOver() {
        return board.isGameOver(this);
    }

    /**
     * Un-dos the last move made
     *
     * @return Message of success or failure
     */
    public Message backMove() {
        return board.backMove(currentMoves, deletePiece, kinged);
    }

    /**
     * Validat a players move
     *
     * @param move move
     * @return Message of validity or invalidity
     */
    public Message takeTurn(Move move) {
        return board.makeMove(move, activeColor, currentMoves, kinged, deletePiece);
    }

    /**
     * Submit a valid turn
     */
    public Message submit() {
        boolean m = board.submitTurn(this);
        if (m) {
            if (this.activeColor == Color.WHITE && this.white instanceof AI && !isGameOver()) {
                AI ai = (AI) this.white;
                ai.takeTurn();
            }
            return Message.info("Valid move submitted.");
        }
        return Message.error("Must complete all jump moves.");

    }

    /**
     * Resign a game and set a winning player
     *
     * @param p current player
     */
    public void resignGame(Player p) {
        this.resigned = true;
        if (p.getName().equals(red.getName())) {
            winner = white;
            loser = red;
        } else {
            winner = red;
            loser = white;
        }
    }

    /**
     * Check if a player has resigned.
     *
     * @return boolean
     */
    public boolean checkResignations() {
        return resigned;
    }

}