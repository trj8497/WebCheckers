package com.webcheckers.model;

import com.webcheckers.appl.Game;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-Tier")
public class BoardTest {

  @Test
  public void singleMoveRed(){

    Space[][] b = new Space[8][8];
    for (int j = 0; j < 8; j++) {
      for (int i = 0; i < 8; i++) {
        b[i][j] = new Space(i, j);
      }
    }

    Piece p = new Piece(Color.RED);
    p.kingMe();
    Piece p2 = new Piece(Color.RED);
    b[2][1].setPiece(p2);
    b[3][2].setPiece(p);
    Board CuT = new Board(b);
    ArrayList<Move> current = new ArrayList<>();
    Move m = new Move(new Position(3,2), new Position(2,3));

    Move m2 = new Move(new Position(2,1), new Position(3,2));
    Message error = CuT.makeMove(m2, Color.RED, current, new HashMap<Move, Piece>(), new HashMap<Move, Piece>());
    assertEquals(error.getType(), Message.Type.ERROR);

    Message message = CuT.makeMove(m, Color.RED, current, new HashMap<Move, Piece>(), new HashMap<Move, Piece>());
    assertEquals(message.getType(), Message.Type.INFO);
  }

  @Test
  public void jumpMoves(){
    Space[][] b = new Space[8][8];
    for (int j = 0; j < 8; j++) {
      for (int i = 0; i < 8; i++) {
        b[i][j] = new Space(i, j);
      }
    }

    Piece p = new Piece(Color.RED);
    p.kingMe();
    b[4][3].setPiece(new Piece(Color.RED));
    b[3][4].setPiece(new Piece(Color.WHITE));
    b[1][4].setPiece(new Piece(Color.WHITE));
    Board CuT = new Board(b);

    ArrayList<Move> current = new ArrayList<>();
    Move m = new Move(new Position(4,3), new Position(2,5));
    Message message = CuT.makeMove(m, Color.RED, current, new HashMap<Move, Piece>(), new HashMap<Move, Piece>());
    assertEquals(message.getType(), Message.Type.INFO);
  }

  @Test
  public void testWhiteSimple(){
    Space[][] b = new Space[8][8];
    for (int j = 0; j < 8; j++) {
      for (int i = 0; i < 8; i++) {
        b[i][j] = new Space(i, j);
      }
    }
    Piece one = new Piece(Color.WHITE);
    Piece two = new Piece(Color.WHITE);
    b[0][1].setPiece(one);
    b[1][0].setPiece(two);


    Board CuT = new Board(b);
    Move m = new Move(new Position(0, 1), new Position(1,0));
    ArrayList<Move> current = new ArrayList<>();
    Message message = CuT.makeMove(m, Color.WHITE, current, new HashMap<Move,Piece>(), new HashMap<Move, Piece>());
    assertEquals(message.getType(), Message.Type.ERROR);
    assertEquals(b[0][1].getPiece(), one);
    assertEquals(b[1][0].getPiece(), two);

    Move m2 = new Move(new Position(1,0), new Position(2, 1));
    message = CuT.makeMove(m2, Color.WHITE, current, new HashMap<Move, Piece>(), new HashMap<Move, Piece>());
    assertEquals(message.getType(), Message.Type.INFO);
    assertNull(b[1][0].getPiece());
    assertEquals(b[2][1].getPiece(), two);

  }

  @Test
  public void testSubmit(){
    Person red = new Person("red");
    Person white = new Person("white");
    Space[][] b = new Space[8][8];
    for (int j = 0; j < 8; j++) {
      for (int i = 0; i < 8; i++) {
        b[i][j] = new Space(i, j);
      }
    }
    Piece one = new Piece(Color.RED);
    b[1][0].setPiece(one);
    Piece two = new Piece(Color.WHITE);
    b[6][1].setPiece(two);
    Game game = new Game(red, white, b);
    red.startGame(game);
    white.startGame(game);
    Move move = new Move(new Position(1,0), new Position(0,1));
    Message message = game.takeTurn(move);
    assertEquals(message.getType(), Message.Type.INFO);
    Message next = game.submit();
    assertEquals(message.getType(), Message.Type.INFO);
    Move move1 = new Move(new Position(6,1), new Position(7,0));
    Message whiteTurn = game.takeTurn(move1);
    assertEquals(whiteTurn.getType(), Message.Type.INFO);
    Message whiteSub = game.submit();
    assertEquals(whiteSub.getType(), Message.Type.INFO);
  }

  @Test
  public void testKing(){
    Person red = new Person("red");
    Person white = new Person("white");
    Space[][] b = new Space[8][8];
    for (int j = 0; j < 8; j++) {
      for (int i = 0; i < 8; i++) {
        b[i][j] = new Space(i, j);
      }
    }
    Piece one = new Piece(Color.RED);
    one.kingMe();
    b[1][2].setPiece(one);
    Piece two = new Piece(Color.WHITE);
    two.kingMe();
    b[6][5].setPiece(two);
    Game game = new Game(red, white, b);
    red.startGame(game);
    white.startGame(game);
    Move r = new Move(new Position(1,2), new Position(2,1));
    Message a = game.takeTurn(r);
    assertEquals(a.getType(), Message.Type.INFO);
    Message c = game.submit();
    assertEquals(c.getType(), Message.Type.INFO);
    Move w = new Move(new Position(6, 5), new Position(5,4));
    Message d = game.takeTurn(w);
    assertEquals(d.getType(), Message.Type.INFO);
    Message e = game.submit();
    assertEquals(d.getType(), Message.Type.INFO);

    Move r2 = new Move(new Position(2,1), new Position(3, 2));
    Message f = game.takeTurn(r2);
    assertEquals(f.getType(), Message.Type.INFO);
    Message g = game.submit();
    Move w2 = new Move(new Position(5,4), new Position(4,5));
    Message h = game.takeTurn(w2);
    assertEquals(h.getType(), Message.Type.INFO);
    Message i = game.submit();
    assertEquals(i.getType(), Message.Type.INFO);
  }

  @Test
  public void MultiForward(){
    Person red = new Person("red");
    Person white = new Person("white");
    Space[][] b = new Space[8][8];
    for (int j = 0; j < 8; j++) {
      for (int i = 0; i < 8; i++) {
        b[i][j] = new Space(i, j);
      }
    }
    Piece pr = new Piece(Color.RED);
    Piece j1 = new Piece(Color.WHITE);
    Piece j2 = new Piece(Color.WHITE);
    Piece j3 = new Piece(Color.WHITE);
    Piece pw = new Piece(Color.WHITE);
    Piece j4 = new Piece(Color.RED);
    Piece j5 = new Piece(Color.RED);
    Piece j6 = new Piece(Color.RED);
    b[6][7].setPiece(pr);
    b[5][6].setPiece(j1);
    b[3][6].setPiece(j2);
    b[1][6].setPiece(j3);
    b[1][0].setPiece(pw);
    b[2][1].setPiece(j4);
    b[4][1].setPiece(j5);
    b[6][1].setPiece(j6);

    Move m1 = new Move(new Position(6,7), new Position(4,5));
    Move m2 = new Move(new Position(4,5), new Position(2,7));
    Move m3 = new Move(new Position(2,7), new Position(0,5));
    Move m4 = new Move(new Position(1,0), new Position(3,2));
    Move m5 = new Move(new Position(3,2), new Position(5,0));
    Move m6 = new Move(new Position(5,0), new Position(7,2));

    Game game = new Game(red, white, b);
    red.startGame(game);
    white.startGame(game);
    Message i1 = game.takeTurn(m1);
    assertEquals(i1.getType(), Message.Type.INFO);
    Message s1 = game.submit();
    assertEquals(s1.getType(), Message.Type.ERROR);
    Message i2 = game.takeTurn(m2);
    assertEquals(i2.getType(), Message.Type.INFO);
    Message i3 = game.takeTurn(m3);
    assertEquals(i3.getType(), Message.Type.INFO);
    Message b1 = game.backMove();
    assertEquals(b1.getType(), Message.Type.INFO);
    Message i4 = game.takeTurn(m3);
    assertEquals(i4.getType(), Message.Type.INFO);
    Message s2 = game.submit();
    assertEquals(s2.getType(), Message.Type.INFO);

    Message i5 = game.takeTurn(m4);
    assertEquals(i5.getType(), Message.Type.INFO);
    Message s3 = game.submit();
    assertEquals(s3.getType(), Message.Type.ERROR);
    Message i6 = game.takeTurn(m5);
    assertEquals(i6.getType(), Message.Type.INFO);
    Message i7 = game.takeTurn(m6);
    assertEquals(i7.getType(), Message.Type.INFO);
    Message b2 = game.backMove();
    assertEquals(b2.getType(), Message.Type.INFO);
    Message i8 = game.takeTurn(m6);
    assertEquals(i8.getType(), Message.Type.INFO);
    Message s4 = game.submit();
    assertEquals(s4.getType(), Message.Type.INFO);

    Message b3 = game.backMove();
    assertEquals(b3.getType(), Message.Type.ERROR);

  }

  @Test
  public void MultiKing(){
    Person red = new Person("red");
    Person white = new Person("white");
    Space[][] b = new Space[8][8];
    for (int j = 0; j < 8; j++) {
      for (int i = 0; i < 8; i++) {
        b[i][j] = new Space(i, j);
      }
    }
    Piece pr = new Piece(Color.WHITE);
    pr.kingMe();
    Piece j1 = new Piece(Color.RED);
    Piece j2 = new Piece(Color.RED);
    Piece j3 = new Piece(Color.RED);
    Piece pw = new Piece(Color.RED);
    pw.kingMe();
    Piece j4 = new Piece(Color.WHITE);
    Piece j5 = new Piece(Color.WHITE);
    Piece j6 = new Piece(Color.WHITE);
    b[6][7].setPiece(pr);
    b[5][6].setPiece(j1);
    b[3][6].setPiece(j2);
    b[1][6].setPiece(j3);
    b[1][0].setPiece(pw);
    b[2][1].setPiece(j4);
    b[4][1].setPiece(j5);
    b[6][1].setPiece(j6);

    Move m1 = new Move(new Position(6,7), new Position(4,5));
    Move m2 = new Move(new Position(4,5), new Position(2,7));
    Move m3 = new Move(new Position(2,7), new Position(0,5));
    Move m4 = new Move(new Position(1,0), new Position(3,2));
    Move m5 = new Move(new Position(3,2), new Position(5,0));
    Move m6 = new Move(new Position(5,0), new Position(7,2));

    Game game = new Game(red, white, b);
    red.startGame(game);
    white.startGame(game);

    Message i5 = game.takeTurn(m4);
    assertEquals(i5.getType(), Message.Type.INFO);
    Message s3 = game.submit();
    assertEquals(s3.getType(), Message.Type.ERROR);
    Message i6 = game.takeTurn(m5);
    assertEquals(i6.getType(), Message.Type.INFO);
    Message i7 = game.takeTurn(m6);
    assertEquals(i7.getType(), Message.Type.INFO);
    Message b2 = game.backMove();
    assertEquals(b2.getType(), Message.Type.INFO);
    Message i8 = game.takeTurn(m6);
    assertEquals(i8.getType(), Message.Type.INFO);
    Message s4 = game.submit();
    assertEquals(s4.getType(), Message.Type.INFO);

    Message i1 = game.takeTurn(m1);
    assertEquals(i1.getType(), Message.Type.INFO);
    Message s1 = game.submit();
    assertEquals(s1.getType(), Message.Type.ERROR);
    Message i2 = game.takeTurn(m2);
    assertEquals(i2.getType(), Message.Type.INFO);
    Message i3 = game.takeTurn(m3);
    assertEquals(i3.getType(), Message.Type.INFO);
    Message b1 = game.backMove();
    assertEquals(b1.getType(), Message.Type.INFO);
    Message i4 = game.takeTurn(m3);
    assertEquals(i4.getType(), Message.Type.INFO);
    Message s2 = game.submit();
    assertEquals(s2.getType(), Message.Type.INFO);



    Message b3 = game.backMove();
    assertEquals(b3.getType(), Message.Type.ERROR);
  }

  @Test
  public void GenerateSimple(){
    Space[][] b = new Space[8][8];
    for (int j = 0; j < 8; j++) {
      for (int i = 0; i < 8; i++) {
        b[i][j] = new Space(i, j);
      }
    }

    Piece ws = new Piece(Color.WHITE);
    Piece wk = new Piece(Color.WHITE);
    wk.kingMe();
    Piece rs = new Piece(Color.RED);
    Piece rk = new Piece(Color.RED);
    rk.kingMe();

    b[0][1].setPiece(ws);
    b[1][6].setPiece(wk);
    b[7][2].setPiece(rs);
    b[6][5].setPiece(rk);

    Board board = new Board(b);

    ArrayList<Move> red = board.generateSimple(Color.RED);
    assertEquals(red.size(), 6);

    ArrayList<Move> white = board.generateSimple(Color.WHITE);
    assertEquals(white.size(), 6);
  }

  @Test
  public void RedWin(){
    Person red = new Person("red");
    Person white = new Person("white");
    Space[][] b = new Space[8][8];
    for (int j = 0; j < 8; j++) {
      for (int i = 0; i < 8; i++) {
        b[i][j] = new Space(i, j);
      }
    }

    Piece rs = new Piece(Color.RED);
    Piece ws = new Piece(Color.WHITE);
    b[4][1].setPiece(rs);
    b[3][2].setPiece(ws);

    Game game = new Game(red, white, b);
    Move winMove = new Move(new Position(4,1), new Position(2,3));
    red.startGame(game);
    white.startGame(game);
    game.takeTurn(winMove);
    game.submit();
    assertTrue(game.isGameOver());

  }

  @Test
  public void invalidMove(){
    Person red = new Person("red");
    Person white = new Person("white");
    Space[][] b = new Space[8][8];
    for (int j = 0; j < 8; j++) {
      for (int i = 0; i < 8; i++) {
        b[i][j] = new Space(i, j);
      }
    }

    Piece r1 = new Piece(Color.RED);
    Piece r2 = new Piece(Color.RED);
    Piece w1 = new Piece(Color.WHITE);
    Piece w2 = new Piece(Color.WHITE);

    b[4][1].setPiece(r1);
    b[4][3].setPiece(r2);
    b[3][2].setPiece(w1);
    b[2][3].setPiece(w2);

    Game game = new Game(red, white, b);
    Move invalid = new Move(new Position(4,1), new Position(2,3));
    Move valid = new Move(new Position(4,3), new Position(2,1));
    Move invalid2 = new Move(new Position(2,1), new Position(1,2));
    red.startGame(game);
    white.startGame(game);

    Message m = game.takeTurn(invalid);
    assertEquals(m.getType(), Message.Type.ERROR);
    m = game.takeTurn(valid);
    assertEquals(m.getType(), Message.Type.INFO);
    m = game.takeTurn(invalid2);
    assertEquals(m.getType(), Message.Type.ERROR);

  }

  @Test
  public void noMoreMoves(){
    Person red = new Person("red");
    Person white = new Person("white");
    Space[][] b = new Space[8][8];
    for (int j = 0; j < 8; j++) {
      for (int i = 0; i < 8; i++) {
        b[i][j] = new Space(i, j);
      }
    }

    Piece w = new Piece(Color.WHITE);
    Piece r1 = new Piece(Color.RED);
    Piece r2 = new Piece(Color.RED);
    b[0][7].setPiece(w);
    b[1][6].setPiece(r1);
    b[3][6].setPiece(r2);

    Game g = new Game(red, white, b);
    red.startGame(g);
    white.startGame(g);

    Move move = new Move(new Position(3,6), new Position(2,5));
    Message m = g.takeTurn(move);
    assertEquals(m.getType(), Message.Type.INFO);
    g.submit();
    assertTrue(g.isGameOver());
    assertEquals(g.getWinner(), g.getRedPlayer());

  }

}
