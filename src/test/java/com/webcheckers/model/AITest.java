package com.webcheckers.model;

import com.webcheckers.appl.Game;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
public class AITest {

  @Test
  public void AIConstruct(){
    Player CuT = new AI();
  }

  @Test
  public void testAI(){
    AI CuT = new AI();
    Person person = new Person("real person");
    Game game = new Game(CuT, person);
    CuT.startGame(game);
    CuT.takeTurn();
    assertNotNull(CuT);
  }

  @Test
  public void testJump(){
    Space[][] b = new Space[8][8];
    for (int j = 0; j < 8; j++) {
      for (int i = 0; i < 8; i++) {
        b[i][j] = new Space(i, j);
      }
    }

    Piece p = new Piece(Color.WHITE);
    p.kingMe();
    b[4][3].setPiece(p);
    b[3][4].setPiece(new Piece(Color.RED));
    b[1][4].setPiece(new Piece(Color.RED));

    Person one = new Person("name");
    AI CuT = new AI();
    Game game = new Game(CuT, one, b);
    CuT.startGame(game);
    CuT.takeTurn();


    assertNotNull(CuT);

  }

}
