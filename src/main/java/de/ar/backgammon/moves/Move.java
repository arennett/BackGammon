package de.ar.backgammon.moves;

import de.ar.backgammon.BColor;
import de.ar.backgammon.model.BoardModel;

public class Move {

    public int from;
    public int to;

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


    public int getRange(BColor turn) {

        int range = 0;

        if (isOffMove()){
            if (turn == BColor.WHITE) {
                range = BoardModel.POINT_IDX_LAST_BOARD_POINT+1 - from;
            }else {
                range = from;
            }
        }else {
            if (turn == BColor.WHITE) {
                range = to - from;
            } else {
                range = from - to;
            }
        }
        assert range > 0;
        return range;
    }

}
