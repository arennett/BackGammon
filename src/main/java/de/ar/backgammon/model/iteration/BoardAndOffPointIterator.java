package de.ar.backgammon.model.iteration;

import de.ar.backgammon.BColor;
import de.ar.backgammon.model.BoardModel;
import de.ar.backgammon.model.BoardModelIf;
import de.ar.backgammon.points.BPoint;

import java.util.Iterator;

/**
 * this iterator iterates overs all board point and the according off point
 * i.e. white  1..24 plus BoardModel.POINT_IDX_OFF_WHITE
 * i.e. red    24..1 plus BoardModel.POINT_IDX_OFF_RED
 *  *
 */
public class BoardAndOffPointIterator implements Iterator<BPoint> {
    private final BoardModelIf boardModel;
    private final BColor bColor;
    BoardPointIterator pitBoard;
    boolean offPointIterated= false;
    private int offIndex;

    public BoardAndOffPointIterator(BoardModelIf boardModel, BColor bColor) {
        this.boardModel = boardModel;
        this.bColor = bColor;
        pitBoard = new BoardPointIterator(boardModel,bColor);
    }

    @Override
    public boolean hasNext() {
        if (pitBoard.hasNext()){
            return true;
        }else if (!offPointIterated){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public BPoint next() {
        if (pitBoard.hasNext()){
            return pitBoard.next();
        }else if (!offPointIterated){
            offPointIterated=true;
            BPoint offPoint = boardModel.getOffPoint(bColor);
            this.offIndex=offPoint.getIndex();
            return offPoint;

        }
        return null;
    }

    public void setIdx(int index) {
        if (index >= BoardModel.POINT_IDX_FIRST_BOARD_POINT &&
                index >= BoardModel.POINT_IDX_LAST_BOARD_POINT){
            pitBoard.setIdx(index);
        }else{
            this.offIndex=index;
        }
    }

    public int getIdx(){
        if (pitBoard.hasNext()){
            return pitBoard.getIdx();
        }else{
            return this.offIndex;
        }
    }

}
