package com.webcheckers.appl;


import com.webcheckers.model.Person;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The unit test suite for the {@link PlayerLobby} component.
 *
 * @author Yu Liang
 */
@Tag("Application-tier")
public class PlayerLobbyTest {

    final static PlayerLobby CuT = new PlayerLobby();
    final static Player player1 = new Person("1");
    final static Player player2 = new Person("hi");
    final static Player player3 = new Person("1love2");


    /* add players and also test the addPlayer function
     */
    @BeforeEach
    public void setup(){
        CuT.addPlayer(player1);
        CuT.addPlayer(player2);
        CuT.addPlayer(player3);
    }

    /**
     * check if player has been added correctly
     */
    @Test
    public void getPlayerByNameTest(){
        assertEquals(CuT.getPlayerByName("1"), player1);
        assertNull(CuT.getPlayerByName("8"));
    }

    /**
     * test things after add players
     */
    @Test
    public void checkPlayersTest(){
        assertTrue(CuT.hasPlayer("1love2"));
        assertTrue(CuT.hasPlayer("hi"));
        assertFalse(CuT.hasPlayer("7"));
        assertEquals(CuT.numPlayers(),3);
    }

    /**
     * test things after remove players
     */
    @Test
    public void removePlayersTest(){
        CuT.removePlayer("1");
        assertFalse(CuT.hasPlayer("1"));
        assertEquals(CuT.numPlayers(),2);
        assertNull(CuT.getPlayerByName("1"));
        assertTrue(CuT.hasPlayer("hi"));
        CuT.removePlayer("hi");
        assertEquals(CuT.numPlayers(),1);
        assertNull(CuT.getPlayerByName("hi"));
        assertTrue(CuT.hasPlayer("1love2"));
        CuT.removePlayer("1love2");
        assertEquals(CuT.makeArray().size(),0);
        assertEquals(CuT.numPlayers(),0);
        assertFalse(CuT.hasPlayer("hi"));
    }

}
