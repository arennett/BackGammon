package de.ar.backgammon;

import de.ar.backgammon.model.BoardModel;
import de.ar.backgammon.model.BoardModelIf;

public class Move {

    int from;
    int to;

    public Move(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public String toString(){
        return "(" +from +") >> (" +to +")";
    }

    public boolean isBarMove() {
        boolean isBarMove = from == BoardModel.POINT_IDX_BAR_WHITE || from == BoardModel.POINT_IDX_BAR_RED;
        return isBarMove;
    }
    public boolean isOffMove() {
        boolean isOffMove =   from > BoardModel.POINT_IDX_BAR_WHITE
                && from < BoardModel.POINT_IDX_BAR_RED
                && ( to == BoardModel.POINT_IDX_OFF_WHITE
                ||to ==BoardModel.POINT_IDX_OFF_RED );
        return isOffMove;
    }


}
