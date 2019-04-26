package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The object to represent the board as a list of Row objects.
 *
 * @author Elyse Castles
 * @author Jasmine Liang
 */
public class BoardView implements Iterable<Row> {

    //Attributes
    private ArrayList<Row> board;

    //Constructor

    /**
     * Builds a board of rows.
     */
    public BoardView(ArrayList<Row> rows) {
        board = rows;

    }

    //Public methods

    /**
     * The board iterator.
     *
     * @return Iterator over rows in board.
     */
    @Override
    public Iterator<Row> iterator() {
        return board.iterator();
    }
}
