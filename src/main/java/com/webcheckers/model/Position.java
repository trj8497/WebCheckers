package com.webcheckers.model;

/**
 * @author Elyse Castles
 */
public class Position {

    private int row;
    //0 to 7
    private int cell;
    //0 to 7

    /**
     * Create a new position attribute
     *
     * @param row
     * @param cell
     */
    public Position(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    /**
     * Return row idx
     *
     * @return row
     */
    public int getRow() {
        return row;
    }

    /**
     * Return cell idx
     *
     * @return cell
     */
    public int getCell() {
        return cell;
    }

}
