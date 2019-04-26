package com.webcheckers.model;

import com.webcheckers.appl.Game;

import java.util.ArrayList;
import java.util.Random;

/**
 * The AI Player
 *
 * @author Elyse Castles
 */
public class AI extends Player {

  private Random rand;

  /**
   * Start new AI Player
   */
  public AI (){
    super();
    rand = new Random();
  }

  /**
   * Generate moves available for AI to take
   * @return
   */
  private ArrayList<Move> getAIMoves(){
    ArrayList<Move> moves = new ArrayList<>();
    Board board = super.getGame().getBoard();
    moves.addAll(board.canWhiteJumpForward(Color.WHITE));
    moves.addAll(board.canRedJumpForward(Color.WHITE));
    if (!moves.isEmpty()){
      return moves;
    }
    moves.addAll(board.generateSimple(Color.WHITE));
    return moves;
  }

  /**
   * Select a move and update board
   */
    public void takeTurn() {
        ArrayList<Move> jumps = new ArrayList<>();
        Game g = super.getGame();
        Board b = g.getBoard();
        ArrayList<Move> moves = getAIMoves();
        int n = moves.size();
        int r = rand.nextInt(n);
        Move move = moves.get(r); // may not need the -1 is a current precaution
        while (b.AIMove(move) == 1) {
            jumps = b.canAIJumpAgain(move);
            if (jumps.size() == 0) {
                break;
            }
            r = rand.nextInt(jumps.size());
            move = jumps.get(r);
        }
        g.setActiveColor(Color.RED);

    }

}
