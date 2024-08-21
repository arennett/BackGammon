package de.ar.backgammon.moves;

import de.ar.backgammon.BColor;
import de.ar.backgammon.model.BoardModel;

import static de.ar.backgammon.model.BoardModel.POINT_IDX_LAST_BOARD_POINT;

public class Move implements Comparable{

    public static boolean CmpIdxToWhiteString = false;
    private final boolean cmp;
    public int from;
    public int to;

    public Move(int from, int to) {
        this(from,to,false);
    }

    /**
     *
     * @param from
     * @param to
     * @param cmp if true red starts whith comparable index 1 ( as white)
     */
    public Move(int from, int to,boolean cmp) {
        this.cmp = cmp;
        if (cmp) {
            this.from = POINT_IDX_LAST_BOARD_POINT+1 - from;
            if (to == BoardModel.POINT_IDX_OFF_WHITE) {
                to = BoardModel.POINT_IDX_OFF_RED;
            } else {
                this.to = POINT_IDX_LAST_BOARD_POINT+1 - to;
            }
        }else{
            this.from = from;
            this.to = to;
        }
   }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public String toString(){
        if (Move.CmpIdxToWhiteString && isRedDirection()){
            return "(" +getCmpFrom() +") >> (" +getCmpTo() +")";
        }else{
            return "(" +from +") >> (" +to +")";
        }
    }
    public boolean isRedDirection(){
        return (from > to || to==BoardModel.POINT_IDX_OFF_RED);
    }
    public boolean isWhiteDirection(){
        return !isRedDirection();
    }

    /**
     * for comparing red moves to white moves
     * @return
     */
    public int getCmpFrom(){
        if (isRedDirection()) {
            return POINT_IDX_LAST_BOARD_POINT+1 - from;
        }else {
            return from;
        }
    }

    /**
     * for comparing red moves to white moves
     * @return
     */
    public int getCmpTo(){
        if (to==BoardModel.POINT_IDX_OFF_RED) {
            return BoardModel.POINT_IDX_OFF_WHITE;
        }else if (from > to) {
            return POINT_IDX_LAST_BOARD_POINT+1 - to;
        }else {
            return to;
        }
    }

    /**
     * create a comparable string for red moves 24 to 23 becomes 1 to 2
     * @return
     */


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
                range = POINT_IDX_LAST_BOARD_POINT+1 - from;
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

    @Override
    public boolean equals(Object other){
        if (other instanceof Move){
            Move m= (Move) other;
             if (this.from==m.from && this.to==m.to){
                 return true;
             }
        }else{
            return false;
        }
        return false;

    }

    @Override
    public int hashCode() {
        return from*1000+to;
    }

    @Override
    public int compareTo(Object o) {
        Move m = (Move) o;
        return this.toString().compareTo(m.toString());
    }
}
