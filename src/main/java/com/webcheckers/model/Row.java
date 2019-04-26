package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The object to represent a single Row of a game board.
 *
 * @author Elyse Castles
 * @author Jasmine Liang
 */
public class Row implements Iterable<Space> {

    //Attributes
    private int index;
    private ArrayList<Space> spaces;

    //Constructor

    /**
     * Initialize row object
     *
     * @param index row index
     */
    public Row(int index, Space[] row) {
        this.index = index;
        this.spaces = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            spaces.add(row[i]);
        }
    }

    //Public methods

    /**
     * Returns index of this row.
     *
     * @return index
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Returns iterator for spaces in this row.
     *
     * @return spaces iterator
     */
    @Override
    public Iterator<Space> iterator() {
        return this.spaces.iterator();
    }

}
