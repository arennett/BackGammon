package de.ar.backgammon.moves;

import java.util.ArrayList;

/** a move set has one to 4 single moves
 * a move set is played out by the computer player while a game turn
 * */

public class MoveSet extends ArrayList<Move> {
    static public int MAX_SIZE = 4;
    boolean finished = false;

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }


    @Override
    public boolean add(Move move) {
        assert(this.size() < MAX_SIZE);
        return super.add(move);
    }
}
