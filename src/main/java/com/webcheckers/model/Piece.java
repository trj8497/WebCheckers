package com.webcheckers.model;

/**
 * Piece Class
 * This object represents a single piece on the
 * board. The piece may be of type SINGLE or KING, and
 * be of color RED or WHITE.
 *
 * @author: Elyse Castles
 * @author: Jasmine Liang
 */
public class Piece {

    //Attributes
    private Type type;
    private Color color;

    //Constructor

    /**
     * Make new piece with and set the color and type.
     * Default type will always be SINGLE.
     *
     * @param color: color of piece (RED or WHITE)
     */
    public Piece(Color color) {
        this.type = Type.SINGLE;
        this.color = color;
    }

    //Public methods

    /**
     * Get the type of the piece (SINGLE or KING)
     *
     * @return type of piece
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Get the color of the piece (RED or WHITE)
     *
     * @return color of piece
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Make a piece a king
     */
    public void kingMe() {
        this.type = Type.KING;
    }

    /**
     * Undo a king move
     */
    public void unKingMe() {
        this.type = Type.SINGLE;
    }

}