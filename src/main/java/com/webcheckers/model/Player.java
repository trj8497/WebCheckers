package com.webcheckers.model;

import com.webcheckers.appl.Game;

/**
 * The object to represent a single player, human by
 * default.
 *
 * @author Elyse Castles
 * @author Jasmine Liang
 * @author Tejaswini Jagtap
 * @author Himani Munshi
 */
public abstract class Player {

    //Attributes
    private String name;
    private Boolean inGame;
    private Game game;
    private Game spectating;
    private Color color;
    private Color lastColor;
    private String mode;

    //Constructor
    public Player() {
        this.name = "Siri";
        this.color = Color.WHITE;
    }

    /**
     * Create a new player and sets the name.
     *
     * @param name the username of this player
     */
    public Player(String name) {
        this.name = name;
        this.inGame = false;
        this.spectating = null;
        this.lastColor = null;
        this.mode="ai";
    }

    //Public methods

    /**
     * Get the name of the player.
     *
     * @return name (String)
     */
    public String getName() {
        return name;
    }

    /**
     * Returns if a player is already in a game or not
     *
     * @return boolean
     */
    public boolean isPlayerFree() {
        return !inGame;
    }

    /**
     * Sets player inGame value to true
     */
    public void startGame(Game game) {
        this.inGame = true;
        this.game = game;
    }

    /**
     * Sets the last color for updating the most recent turn
     *
     * @param lastColor Color
     */
    public void setLastColor(Color lastColor) {
        this.lastColor = lastColor;
    }

    /**
     * Get the last color for check turn
     *
     * @return Color
     */
    public Color getLastColor() {
        return this.lastColor;
    }

    /**
     * Leave current game
     */
    public void stopGame() {
        this.game = null;
        this.inGame = false;
    }

    /**
     * Return game player is in
     *
     * @return game (Game)
     */
    public Game getGame() {
        return game;
    }

    /**
     * Set the game of a player as spectating
     *
     * @param spectating Game the player is spectating
     */
    public void setSpectating(Game spectating) {
        this.spectating = spectating;
    }

    /**
     * Get the game that is being spectated
     *
     * @return Game
     */
    public Game getSpectating() {
        return spectating;
    }

    /**
     * Return the opposing player
     *
     * @return opponent (Player)
     */
    public Player getOpponent() {
        Player red = game.getRedPlayer();
        if (this.equals(red)) {
            return game.getWhitePlayer();
        } else {
            return red;
        }
    }

    /**
     * Set a players color
     *
     * @param color color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Return players color affiliation
     *
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /**
     * set the mode this player wants to play
     *
     * @param mode the mode
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * return the mode back to UI
     *
     * @return the mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * Return a players name as their toString
     *
     * @return name
     */
    @Override
    public String toString() {
        return this.name;
    }

}
