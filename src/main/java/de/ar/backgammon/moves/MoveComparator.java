package de.ar.backgammon.moves;

import de.ar.backgammon.model.BoardModel;

import java.util.Comparator;

public class MoveComparator implements Comparator<Move> {
    @Override
    public int compare(Move o1, Move o2) {
        // TODO comparator incomplete

        if (o1.isWhiteDirection()){   // white direction
            if (o1.from > o2.from) {
                return +1;
            } else if (o1.from < o2.from) {
                return -1;
            } else {
                return 0;
            }
        }else{      // red direction
            if (o1.from > o2.from) {
                return -1;
            } else if (o1.from < o2.from) {
                return +1;
            } else {
                return 0;
            }
        }

    }


}
