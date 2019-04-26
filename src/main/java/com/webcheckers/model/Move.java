package com.webcheckers.model;

/**
 * @author Elyse Castles
 */
public class Move {

    private Position start;
    private Position end;

    /**
     * Make a new move object
     *
     * @param s start position
     * @param e end position
     */
    public Move(Position s, Position e) {
        this.start = s;
        this.end = e;
    }

    /**
     * Get the starting position
     *
     * @return position
     */
    public Position getStart() {
        return start;
    }

    /**
     * Get the ending position
     *
     * @return position
     */
    public Position getEnd() {
        return end;
    }

    /**
     * Move is the same if starting position and
     * ending position are the same.
     *
     * @param o move object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Move)) {
            return false;
        }
        Move move = (Move) o;
        return
                (this.start.getRow() == move.getStart().getRow() && this.start.getCell() == move.getStart().getCell()
                        && this.end.getRow() == move.getEnd().getRow() && this.end.getCell() == move.getEnd().getCell());
    }

}
