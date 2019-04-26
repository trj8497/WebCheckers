/*
package com.webcheckers.appl;

import com.webcheckers.appl.Game;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.webcheckers.util.Message.Type.INFO;
import static org.junit.jupiter.api.Assertions.*;

*/
/**
 * The unit test suite for the {@link Game} component.
 *
 * @author Himani Munshi
 *//*

@Tag("Application-Tier")
public class GameTest {
    private static final String NAME = "Alpha";
    private static final String OPPONENT = "Beta";
    private Player red;
    private Player white;
    private Game CuT;
    private Space[][] mockBoard ;

    */
/**
     * setUp a mock game to test
     *//*

    @BeforeEach
    public void setUp(){
        this.red = new Person(NAME);
        this.white = new Person(OPPONENT);
        CuT = new Game(red, white);
        this.mockBoard = CuT.getBoard().getBoard();
    }

    */
/**
     * Test that the main constructor works without failure along
     * with the getters in the Game class.
     *//*

    @Test
    public void ctor_withArg() {
        assertEquals(this.red, CuT.getRedPlayer());
        assertEquals(this.white, CuT.getWhitePlayer());
    }


    */
/**
     * Check the moves. test all the possible moves
     *//*

    @Test
    public void makeMoveTest(){
        Message validJumpMsg = Message.info("Valid jump move has been made");
        Message invalidJumpMsg = Message.error("Invalid move or possible jump move.");
        Message validSimpleMsg = Message.info("Valid simple move");
        Message invalidMoveMsg = Message.error("That is not a valid move");
        Move mock_validMove = new Move(new Position(5,0),new Position(4,1));
        assertEquals(validSimpleMsg.getText(), CuT.makeMove(mock_validMove).getText());
        Move mock_invalidPlayerMove = new Move(new Position(2,1),new Position(3,0));
        assertEquals(invalidMoveMsg.getText(), CuT.makeMove(mock_invalidPlayerMove).getText());
        CuT.submitTurn();
        CuT.submitTurn();
        Move mock_invalidbackMove = new Move(new Position(4,1),new Position(5,0));
        assertEquals(invalidJumpMsg.getText(), CuT.makeMove(mock_invalidbackMove).getText());
        CuT.submitTurn();
        CuT.submitTurn();
        mockBoard[4][5].setPiece(new Piece(Color.WHITE));
        Move mock_validJumpMove = new Move(new Position(5,4),new Position(3,6));
        assertEquals(validJumpMsg.getText(), CuT.makeMove(mock_validJumpMove).getText());
        CuT.submitTurn();
        CuT.submitTurn();
        Move mock_invalidJumpMove = new Move(new Position(5,2),new Position(3,0));
        assertEquals(invalidJumpMsg.getText(), CuT.makeMove(mock_invalidJumpMove).getText());
        CuT.submitTurn();
        CuT.submitTurn();
        Piece mockKing = new Piece(Color.RED);
        mockKing.kingMe();
        mockBoard[1][2].setPiece(mockKing);
        Move mock_validKingMove = new Move(new Position(1,2),new Position(3,4));
        assertEquals(validJumpMsg.getText(), CuT.makeMove(mock_validKingMove).getText());
    }

    */
/**
     * test the game over and the winner
     *//*

    @Test
    public void isGameOverTest(){
        assertFalse(CuT.isGameOver());
        for (int i = 2; i < 8; i++){
            for (int j = 0; j < 8; j++){
                mockBoard[i][j].setPiece(null);
            }
        }
        assertTrue(CuT.isGameOver());
        assertEquals(CuT.getWinner(),white);
    }

}
*/
