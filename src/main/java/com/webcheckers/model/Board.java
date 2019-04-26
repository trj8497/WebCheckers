package com.webcheckers.model;

import com.webcheckers.appl.Game;
import com.webcheckers.util.Message;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

/**
 * Board Object Class:
 * Contains information pertaining to the board itself
 * and making changes to the board
 *
 * @author Elyse Castles
 */
public class Board {

    //Private attributes
    private Space[][] board;

    /**
     * Move types is used to check if the active player can
     * make another jump move
     * if 0: no move has been made or another jump can be made
     * if 1: made simple move or jump but cannot jump again
     */
    private int moveType;

    //Constructor

    /**
     * Initialize the board
     */
    public Board() {
        this.board = buildBoard();
        this.moveType = 0;
    }

    public Board(Space[][] board) {
        this.board = board;
        moveType = 0;
    }

    //Public methods

    /**
     * Build the board for a new game
     *
     * @return board
     */
    private Space[][] buildBoard() {
        Space[][] b = new Space[8][8];
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {

                //add white pieces to odd spaces on first and third row
                if (j == 0 || j == 2) {
                    if (i % 2 != 0) {
                        b[j][i] = new Space(i, new Piece(Color.WHITE), j);
                    } else {
                        b[j][i] = new Space(j, i);
                    }
                } else if (j == 1) {

                    //add white piece to even spaces on second row
                    if (i % 2 == 0) {
                        b[j][i] = new Space(i, new Piece(Color.WHITE), j);
                    } else {
                        b[j][i] = new Space(j, i);
                    }
                } else if (j == 6) {

                    //add red pieces to odd spaces on sixth row
                    if (i % 2 != 0) {
                        b[j][i] = new Space(i, new Piece(Color.RED), j);
                    } else {
                        b[j][i] = new Space(j, i);
                    }
                } else if (j == 7 || j == 5) {

                    //add red pieces to even spaces on row seven and five
                    if (i % 2 == 0) {
                        b[j][i] = new Space(i, new Piece(Color.RED), j);
                    } else {
                        b[j][i] = new Space(j, i);
                    }
                } else {

                    //add empty spaces for any row that does not start with pieces
                    b[j][i] = new Space(j, i);
                }
            }
        }
        return b;
    }

    /**
     * Return the board for test
     *
     * @return the board
     */
    public Space[][] getBoard() {
        return board;
    }

    /**
     * Return a game board
     *
     * @return BoardView
     */
    public BoardView getBoardView() {
        ArrayList<Row> rows = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            rows.add(new Row(i, board[i]));
        }
        return new BoardView(rows);
    }

    /**
     * Function determines if all red or all white pieces
     * have been captured, and game is over.
     *
     * @return boolean
     */
    public boolean isGameOver(Game game) {
        int red = 0;
        int white = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].getPiece() != null) {
                    if (board[i][j].getPiece().getColor() == Color.RED) {
                        red++;
                    } else {
                        white++;
                    }
                }

            }
        }

        if (red == 0) {
            game.setWinner(game.getWhitePlayer());
        } else if (white == 0) {
            game.setWinner(game.getRedPlayer());
        }


        return red == 0 || white == 0 || noMoreMoves(game);

    }

    private boolean noMoreMoves(Game game) {
        if (game.getActiveColor() == Color.RED) {
            ArrayList<Move> moves = generateSimple(Color.RED);
            moves.addAll(canRedJumpForward(Color.RED));
            moves.addAll(canWhiteJumpForward(Color.RED));
            if (moves.isEmpty()) {
                game.setWinner(game.getWhitePlayer());
                return true;
            }
        } else {
            ArrayList<Move> moves = generateSimple(Color.WHITE);
            moves.addAll(canWhiteJumpForward(Color.WHITE));
            moves.addAll(canRedJumpForward(Color.WHITE));
            if (moves.isEmpty()) {
                game.setWinner(game.getRedPlayer());
                return true;
            }
        }
        return false;
    }

    /**
     * Function un-dos the last move that was made.
     *
     * @return Message
     */
    public Message backMove(ArrayList<Move> currentMoves, Map<Move, Piece> deletePiece, Map<Move, Piece> kinged) {
        int idx = currentMoves.size() - 1;

        if (idx != -1) { //check if there is a move to back up
            Move last = currentMoves.get(idx);
            currentMoves.remove(idx);
            Space s = board[last.getEnd().getRow()][last.getEnd().getCell()];
            Space e = board[last.getStart().getRow()][last.getStart().getCell()];
            Piece p = s.getPiece();

            if (deletePiece.containsKey(last)) { // put piece back up on the board
                int row = (s.getRow() + e.getRow()) / 2;
                int col = (s.getCellIdx() + e.getCellIdx()) / 2;
                board[row][col].setPiece(deletePiece.get(last));
                deletePiece.remove(last);
            }

            if (kinged.containsKey(last)) { // unking a piece if necessary
                p.unKingMe();
                kinged.remove(last);
            }

            changeBoard(s, e, p); // move piece back
            moveType = 0;
            return Message.info("Move backed up");

        }
        return Message.error("No moves to back up");

    }

    /**
     * Function submits and finalizes a move
     */
    public boolean submitTurn(Game game) {
        if (this.moveType == 0) {
            return false;
        }
        ArrayList<Move> unchangeableMoves = game.getUnchangeable();
        ArrayList<Move> currentMoves = game.getCurrent();
        Color activeColor = game.getActiveColor();
        Map<Move, Piece> deletePiece = game.getDelete();
        Map<Move, Piece> kinged = game.getKingged();

        unchangeableMoves.addAll(currentMoves);
        currentMoves.clear();

        if (activeColor == Color.RED) { // switch color/turns
            game.setActiveColor(Color.WHITE);
        } else {
            game.setActiveColor(Color.RED);
        }

        deletePiece.clear();
        kinged.clear();
        moveType = 0;
        return true;
    }

    /**
     * Check if move is valid
     *
     * @param move Move
     * @return Message
     */
    public Message makeMove(Move move, Color activeColor, ArrayList<Move> currentMoves, Map<Move, Piece> kinged, Map<Move, Piece> deletePiece) {
        Position start = move.getStart();
        Position end = move.getEnd();
        Space s = board[start.getRow()][start.getCell()];
        Space e = board[end.getRow()][end.getCell()];
        Piece p = s.getPiece();
        ArrayList<Move> jumps;

        if (activeColor == Color.RED && moveType != 1) {
            jumps = canRedJumpForward(activeColor);  //get jump moves if any
            jumps.addAll(canWhiteJumpForward(activeColor));


            if (jumps.isEmpty() && checkSimpleMoveRed(s, e)) { //make simple move if cannot jump
                currentMoves.add(move);
                moveType = 1;
                changeBoard(s, e, p);

                if (e.getRow() == 0 && p.getType() != Type.KING) {
                    p.kingMe();
                    kinged.put(move, p);
                }

                return Message.info("Valid simple move");
            } else if (jumps.isEmpty() && p.getType() == Type.KING && checkSimpleMoveWhite(s, e)) {
                currentMoves.add(move);
                moveType = 1;
                changeBoard(s, e, p);
                return Message.info("Valid simple move");

            } else if (!jumps.isEmpty() && checkJumps(move, jumps)) { //take valid jump move
                int row = (e.getRow() + s.getRow()) / 2;
                int col = (e.getCellIdx() + s.getCellIdx()) / 2;
                deletePiece.put(move, board[row][col].getPiece());
                board[row][col].setPiece(null);
                //can red piece jump forward again
                ArrayList<Move> nextJumps = checkRedCanJumpAgain(move, activeColor);
                if (p.getType() == Type.KING) {
                    nextJumps.addAll(checkWhiteCanJumpAgain(move, activeColor));
                }

                if (nextJumps.size() == 0) {
                    moveType = 1;
                } else {
                    moveType = 0;
                }

                currentMoves.add(move);
                changeBoard(s, e, p);

                if (e.getRow() == 0 && p.getType() != Type.KING) {
                    p.kingMe();
                    kinged.put(move, p);
                }
                return Message.info("Valid jump move has been made");

            }
            return Message.error("Invalid move or possible jump move.");

        }
        if (activeColor == Color.WHITE && moveType != 1) {
            jumps = canWhiteJumpForward(activeColor);  //get jump moves if any
            jumps.addAll(canRedJumpForward(activeColor));

            if (jumps.isEmpty() && checkSimpleMoveWhite(s, e)) { //make simple move if cannot jump
                currentMoves.add(move);
                moveType = 1;
                changeBoard(s, e, p);

                if (e.getRow() == 7 && p.getType() != Type.KING) {
                    p.kingMe();
                }
                return Message.info("Valid simple move");

            } else if (jumps.isEmpty() && p.getType() == Type.KING && checkSimpleMoveRed(s, e)) {
                currentMoves.add(move);
                moveType = 1;
                changeBoard(s, e, p);
                return Message.info("Valid simple move");

            } else if (!jumps.isEmpty() && checkJumps(move, jumps)) { //take valid jump move
                //can white piece jump forward again
                int row = (e.getRow() + s.getRow()) / 2;
                int col = (e.getCellIdx() + s.getCellIdx()) / 2;
                deletePiece.put(move, board[row][col].getPiece());
                board[row][col].setPiece(null);
                ArrayList<Move> nextJumps = checkWhiteCanJumpAgain(move, activeColor);

                if (p.getType() == Type.KING) {
                    nextJumps.addAll(checkRedCanJumpAgain(move, activeColor));
                }
                if (nextJumps.size() == 0) {
                    moveType = 1;
                } else {
                    moveType = 0;
                }
                currentMoves.add(move);
                changeBoard(s, e, p);

                if (e.getRow() == 7 && p.getType() != Type.KING) {
                    p.kingMe();
                }
                return Message.info("Valid jump move has been made");

            }
            return Message.error("Invalid move or possible jump move.");

        }
        return Message.error("That is not a valid move");

    }

    /**
     * Function changes board by moving a peice from
     * one space to another
     *
     * @param s start space
     * @param e end space
     * @param p piece to move
     */
    private void changeBoard(Space s, Space e, Piece p) {
        s.setPiece(null);
        e.setPiece(p);
    }

    /**
     * Function checks if the red player can make any forward jumps,
     * if so the list of possible jump moves is returned. This
     * function also tests if the White King pieces can make
     * a backward jump moves.
     *
     * @return ArrayList<Move> list moves
     */
    public ArrayList<Move> canRedJumpForward(Color activeColor) {
        ArrayList<Move> jumps = new ArrayList<>();


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i >= 2 && j >= 2) {
                    if (board[i][j].getPiece() != null && board[i][j].getPiece().getColor() == activeColor) {

                        if (activeColor == Color.RED || (activeColor == Color.WHITE && board[i][j].getPiece().getType() == Type.KING)) {
                            // is there a piece to jump over to left
                            if (board[i - 1][j - 1].getPiece() != null && board[i - 1][j - 1].getPiece().getColor() != activeColor) {

                                // is there an open space to land in
                                if (board[i - 2][j - 2].getPiece() == null) {
                                    jumps.add(new Move(new Position(i, j), new Position(i - 2, j - 2)));
                                }

                            }

                        }


                    }
                }

                if (i >= 2 && j < 6) {
                    if (board[i][j].getPiece() != null && board[i][j].getPiece().getColor() == activeColor) {

                        if (activeColor == Color.RED || (activeColor == Color.WHITE && board[i][j].getPiece().getType() == Type.KING)) {

                            // is there a piece to jump over to right
                            if (board[i - 1][j + 1].getPiece() != null && board[i - 1][j + 1].getPiece().getColor() != activeColor) {

                                // is there an open space to land in
                                if (board[i - 2][j + 2].getPiece() == null) {
                                    jumps.add(new Move(new Position(i, j), new Position(i - 2, j + 2)));
                                }
                            }
                        }
                    }
                }
            }
        }
        return jumps;

    }

    /**
     * Function checks if the white player can make any forward
     * jumps, if so the list of possible jump moves is returned.
     * This function also tests if the Red King pieces can make
     * any backwards jump moves.
     *
     * @return List of moves
     */
    public ArrayList<Move> canWhiteJumpForward(Color activeColor) {
        ArrayList<Move> jumps = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i < 6 && j >= 2) {
                    if (board[i][j].getPiece() != null && board[i][j].getPiece().getColor() == activeColor) {

                        if (activeColor == Color.WHITE || (activeColor == Color.RED && board[i][j].getPiece().getType() == Type.KING)) {
                            // is there a piece to jump over to left
                            if (board[i + 1][j - 1].getPiece() != null && board[i + 1][j - 1].getPiece().getColor() != activeColor) {

                                // is there an open space to land in
                                if (board[i + 2][j - 2].getPiece() == null) {
                                    jumps.add(new Move(new Position(i, j), new Position(i + 2, j - 2)));
                                }
                            }

                        }


                    }
                }

                if (i < 6 && j < 6) {
                    if (board[i][j].getPiece() != null && board[i][j].getPiece().getColor() == activeColor) {

                        if (activeColor == Color.WHITE || (activeColor == Color.RED && board[i][j].getPiece().getType() == Type.KING)) {
                            // is there a peice to jump over to right
                            if (board[i + 1][j + 1].getPiece() != null && board[i + 1][j + 1].getPiece().getColor() != activeColor) {

                                // is there an open space to land in
                                if (board[i + 2][j + 2].getPiece() == null) {
                                    jumps.add(new Move(new Position(i, j), new Position(i + 2, j + 2)));
                                }
                            }
                        }

                    }
                }
            }
        }
        return jumps;

    }

    /**
     * Function checks if a single move object is contained in
     * a list of jump moves
     *
     * @param move  move
     * @param jumps list of moves
     * @return boolean
     */
    private boolean checkJumps(Move move, ArrayList<Move> jumps) {
        for (int i = 0; i < jumps.size(); i++) {
            if (move.equals(jumps.get(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Function checks if red piece can make a valid simple
     * move forward. This method is also used to see if a
     * white king can make a valid simple move backward.
     *
     * @param s starting space
     * @param e ending space
     * @return boolean
     */
    private boolean checkSimpleMoveRed(Space s, Space e) {
        if (e.getPiece() == null) { // no piece in end space
            // if valid simple move forward
            if (s.getRow() - 1 == e.getRow() && (s.getCellIdx() - 1 == e.getCellIdx() || s.getCellIdx() + 1 == e.getCellIdx())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Function checks if a white peice can make a valid simple
     * move forawrd. This method is also used to see if a
     * red king can make a valid simple move backwards.
     *
     * @param s start space
     * @param e end space
     * @return boolean
     */
    private boolean checkSimpleMoveWhite(Space s, Space e) {
        if (e.getPiece() == null) {
            // if valid simple move forward
            if (s.getRow() + 1 == e.getRow() && (s.getCellIdx() - 1 == e.getCellIdx() || s.getCellIdx() + 1 == e.getCellIdx())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Function checks if a red piece can jump forward again
     * or if a white king can jump backwards.
     *
     * @param move move
     * @return list of possible jump moves
     */
    private ArrayList<Move> checkRedCanJumpAgain(Move move, Color activeColor) {
        Position end = move.getEnd();
        Space one;
        Space two;
        ArrayList<Move> jumps = new ArrayList<>();

        // if can jump forward still
        if (end.getRow() >= 2) {

            // check if can jump right
            if (end.getCell() < 6) {
                one = board[end.getRow() - 1][end.getCell() + 1];
                two = board[end.getRow() - 2][end.getCell() + 2];

                //if piece to jump of opposite color and open space to land on
                if (one.getPiece() != null && one.getPiece().getColor() != activeColor && two.getPiece() == null) {
                    jumps.add(new Move(new Position(end.getRow() - 1, end.getCell() + 1), new Position(end.getRow() - 2, end.getCell() + 2)));
                }
            }

            if (end.getCell() >= 2) { // if can jump left
                one = board[end.getRow() - 1][end.getCell() - 1];
                two = board[end.getRow() - 2][end.getCell() - 2];

                //if piece to jump of opposite color and open space to land on
                if (one.getPiece() != null && one.getPiece().getColor() != activeColor && two.getPiece() == null) {
                    jumps.add(new Move(new Position(end.getRow() - 1, end.getCell() - 1), new Position(end.getRow() - 2, end.getCell() - 2)));
                }
            }
        }
        return jumps;

    }

    /**
     * Function checks if a white piece can jump forward again
     * or if a red king can jump backwards.
     *
     * @param move move
     * @return list of possible jump moves
     */
    private ArrayList<Move> checkWhiteCanJumpAgain(Move move, Color activeColor) {
        Position end = move.getEnd();
        Space one;
        Space two;
        ArrayList<Move> jumps = new ArrayList<>();

        // if can jump forward still
        if (end.getRow() < 6) {

            // check if can jump right
            if (end.getCell() < 6) {
                one = board[end.getRow() + 1][end.getCell() + 1];
                two = board[end.getRow() + 2][end.getCell() + 2];

                //if piece to jump of opposite color and open space to land on
                if (one.getPiece() != null && one.getPiece().getColor() != activeColor && two.getPiece() == null) {
                    jumps.add(new Move(new Position(end.getRow() + 1, end.getCell() + 1), new Position(end.getRow() + 2, end.getCell() + 2)));
                }
            }

            if (end.getCell() >= 2) { // if can jump left
                one = board[end.getRow() + 1][end.getCell() - 1];
                two = board[end.getRow() + 2][end.getCell() - 2];

                //if piece to jump of opposite color and open space to land on
                if (one.getPiece() != null && one.getPiece().getColor() != activeColor && two.getPiece() == null) {
                    jumps.add(new Move(new Position(end.getRow() + 1, end.getCell() - 1), new Position(end.getRow() + 2, end.getCell() - 2)));
                }
            }
        }
        return jumps;

    }

    public ArrayList<Move> generateSimple(Color activeColor) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (activeColor == Color.WHITE) {
                    if (board[i][j].getPiece() != null && board[i][j].getPiece().getColor() == activeColor) {
                        if (i < 7 && j < 7 && board[i + 1][j + 1].getPiece() == null) {
                            moves.add(new Move(new Position(i, j), new Position(i + 1, j + 1)));
                        }
                        if (i < 7 && j > 0 && board[i + 1][j - 1].getPiece() == null) {
                            moves.add(new Move(new Position(i, j), new Position(i + 1, j - 1)));
                        }
                        if (board[i][j].getPiece().getType() == Type.KING) {
                            if (i > 0 && j < 7 && board[i - 1][j + 1].getPiece() == null) {
                                moves.add(new Move(new Position(i, j), new Position(i - 1, j + 1)));
                            }
                            if (i > 0 && j > 0 && board[i - 1][j - 1].getPiece() == null) {
                                moves.add(new Move(new Position(i, j), new Position(i - 1, j - 1)));
                            }
                        }
                    }
                } else {
                    if (board[i][j].getPiece() != null && board[i][j].getPiece().getColor() == activeColor) {
                        if (board[i][j].getPiece().getType() == Type.KING) {
                            if (i < 7 && j < 7 && board[i + 1][j + 1].getPiece() == null) {
                                moves.add(new Move(new Position(i, j), new Position(i + 1, j + 1)));
                            }
                            if (i < 7 && j > 0 && board[i + 1][j - 1].getPiece() == null) {
                                moves.add(new Move(new Position(i, j), new Position(i + 1, j - 1)));
                            }
                        }
                        if (i > 0 && j < 7 && board[i - 1][j + 1].getPiece() == null) {
                            moves.add(new Move(new Position(i, j), new Position(i - 1, j + 1)));
                        }
                        if (i > 0 && j > 0 && board[i - 1][j - 1].getPiece() == null) {
                            moves.add(new Move(new Position(i, j), new Position(i - 1, j - 1)));
                        }
                    }
                }
            }
        }
        return moves;
    }

    public ArrayList<Move> canAIJumpAgain(Move move) {
        ArrayList<Move> jumps = new ArrayList<>();
        int row = move.getEnd().getRow();
        int col = move.getEnd().getCell();
        Space s = board[row][col];
        if (row < 6) {
            if (col >= 2 && board[row + 1][col - 1].getPiece() != null && board[row + 1][col - 1].getPiece().getColor() == Color.RED) {
                if (board[row + 2][col - 2].getPiece() == null) {
                    jumps.add(new Move(new Position(row, col), new Position(row + 2, col - 2)));
                }
            }
            if (col < 6 && board[row + 1][col + 1].getPiece() != null && board[row + 1][col + 1].getPiece().getColor() == Color.RED) {
                if (board[row + 2][col + 2].getPiece() == null) {
                    jumps.add(new Move(new Position(row, col), new Position(row + 2, col + 2)));
                }
            }
        }
        if (s.getPiece().getType() == Type.KING && row >= 2) {
            if (col >= 2 && board[row - 1][col - 1].getPiece() != null && board[row - 1][col - 1].getPiece().getColor() == Color.RED) {
                if (board[row - 2][col - 2].getPiece() == null) {
                    jumps.add(new Move(new Position(row, col), new Position(row - 2, col - 2)));
                }
            }
            if (col < 6 && board[row - 1][col + 1].getPiece() != null && board[row - 1][col + 1].getPiece().getColor() == Color.RED) {
                if (board[row - 2][col + 2].getPiece() == null) {
                    jumps.add(new Move(new Position(row, col), new Position(row - 2, col + 2)));
                }
            }
        }
        return jumps;
    }

    public int AIMove(Move move) {
        Piece p = board[move.getStart().getRow()][move.getStart().getCell()].getPiece();
        Space s = board[move.getStart().getRow()][move.getStart().getCell()];
        Space e = board[move.getEnd().getRow()][move.getEnd().getCell()];
        changeBoard(s, e, p);
        if (e.getRow() == 7 && p.getColor() == Color.WHITE) {
            p.kingMe();
        }
        if (Math.abs(s.getRow() - e.getRow()) == 2) {
            int x = (Math.abs(s.getRow() + e.getRow())) / 2;
            int y = (Math.abs(s.getCellIdx() + e.getCellIdx())) / 2;
            board[x][y].setPiece(null);
            return 1;
        }
        return 0;
    }


}