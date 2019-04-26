package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * The object to store all of the current players.
 *
 * @author Kayleigh Kolz
 * @author Elyse Castles
 */

public class PlayerLobby {

    //Attributes
    private static final Logger LOG = Logger.getLogger(PlayerLobby.class.getName());
    private HashMap<String, Player> playerList = new HashMap<>();

    //Public methods

    /**
     * Function returns HashMap of players online
     *
     * @return players
     */
    public HashMap<String, Player> getPlayerList() {
        return this.playerList;
    }

    /**
     * Function returns a player attribute based on their name
     *
     * @param name : name of player
     * @return Player
     */
    public Player getPlayerByName(String name) {
        return playerList.get(name);
    }

    /**
     * Adds a player to the list of current players.
     *
     * @param player a player object to be added
     */
    public void addPlayer(Player player) {
        this.playerList.put(player.getName(), player);
    }

    /**
     * Remove a player form the list of current players.
     *
     * @param player a player name to remove
     */
    public void removePlayer(String player) {
        this.playerList.remove(player);
    }

    /**
     * Find whether the username is already on the list of current players.
     *
     * @param username the username of a player
     * @return True if this user is already on the list of current players, false otherwise.
     */
    public boolean hasPlayer(String username) {
        return this.playerList.containsKey(username);
    }

    /**
     * Get the number of current players.
     *
     * @return The number of current players.
     */
    public int numPlayers() {
        return this.playerList.size();
    }


    /**
     * Converts the HashMap of current players into an ArrayList of their names.
     *
     * @return A new ArrayList of the names of all the current players.
     */
    public ArrayList<Player> makeArray() {
        ArrayList<Player> players = new ArrayList<>();
        for (String p : this.playerList.keySet()) {
            players.add(playerList.get(p));
        }
        return players;
    }
}