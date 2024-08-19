package de.ar.backgammon.moves;

import java.util.Comparator;

public class MoveComparator implements Comparator<Move> {
    @Override
    public int compare(Move o1, Move o2) {
        if (o1.to > o1.from){
            if (o1.from > o2.from) {
                return +1;
            } else if (o1.from + o1.to < o2.from + o2.to) {
                return -1;
            } else {
                return 0;
            }
        }else{
            if (o1.from > o2.from) {
                return -1;
            } else if (o1.from + o1.to < o2.from + o2.to) {
                return +1;
            } else {
                return 0;
            }
        }

    }


}
