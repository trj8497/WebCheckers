package com.webcheckers.model;

/**
 * The object to represent a single space on the board. This space has an associated
 * index number, and may possible have a piece associated with it.
 *
 * @author Elyse Castles
 * @author Jasmine Liang
 */
public class Space {

    //Attributes
    private int row;
    private int cellIdx;
    private Piece piece;
    private Boolean viability;

    //Constructors

    /**
     * Set the spaces index. Constructor for when there is no piece to be
     * put on this space.
     *
     * @param idx: index of space
     * @param row: the row number
     */
    public Space(int row, int idx) {
        this.cellIdx = idx;
        this.row = row;
        if ((row % 2 == 0) && (idx % 2 != 0)) {
            this.viability = true;
        } else if ((row % 2 != 0) && (idx % 2 == 0)) {
            this.viability = true;
        } else {
            this.viability = false;
        }
    }

    /**
     * Set the space's index and a piece that is currently occupying this space.
     *
     * @param idx:   index of space
     * @param piece: piece on space
     */
    public Space(int idx, Piece piece, int row) {
        this.row = row;
        this.cellIdx = idx;
        this.piece = piece;
        this.viability = true;
    }

    //Public methods

    /**
     * Returns this cells index.
     *
     * @return index
     */
    public int getCellIdx() {
        return this.cellIdx;
    }

    /**
     * Returns if this is a valid square or not.
     *
     * @return True if the square is valid, false otherwise.
     */
    public boolean isValid() {
        return this.viability && piece == null;
    }

    /**
     * Returns the piece on this space.
     *
     * @return The piece on this space or null if there is no piece.
     */
    public Piece getPiece() {
        if (this.piece != null) {
            return this.piece;
        }
        return null;
    }

    /**
     * Returns space's row index
     *
     * @return row
     */
    public int getRow() {
        return row;
    }

    /**
     * Set a spaces to hold a passed in piece
     *
     * @param p piece
     */
    public void setPiece(Piece p) {
        this.piece = p;
    }

}
