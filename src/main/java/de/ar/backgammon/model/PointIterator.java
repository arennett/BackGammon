package de.ar.backgammon.model;

import de.ar.backgammon.BColor;
import de.ar.backgammon.GameControl;
import de.ar.backgammon.points.BPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

/**
 * iterates in move direction
 * from start board to the homeboard of a player(bColor)
 * the bar is discounted
 */
public class PointIterator implements Iterator<BPoint> {
    private static final Logger logger = LoggerFactory.getLogger(PointIterator.class);
    protected  BoardModelIf boardModel;
    protected  BColor bColor;
    int idx = 0;

    public PointIterator(BoardModelIf boardModel, BColor bColor) {
        this.boardModel = boardModel;

        this.bColor = bColor;
        if (bColor == BColor.WHITE) {
            idx = BoardModel.POINT_IDX_FIRST_BOARD_POINT-1;
        } else {
            idx = BoardModel.POINT_IDX_LAST_BOARD_POINT+1;
        }

    }


    /**
     * create a pointiterator with the current index
     * @param pit
     */
    public PointIterator(PointIterator pit) {
        this.idx= pit.idx;
        this.boardModel=pit.boardModel;
        this.bColor=pit.bColor;
    }

    @Override
    public boolean hasNext() {
        if (bColor == BColor.WHITE) {
            return idx + 1 <= BoardModel.POINT_IDX_LAST_BOARD_POINT;
        } else {
            return idx - 1 >= BoardModel.POINT_IDX_FIRST_BOARD_POINT;
        }
    }

    @Override
    public BPoint next() {
        BPoint point = null;
        if (hasNext()) {
            if (bColor == BColor.WHITE) {
                idx++;
            } else {
                idx--;
            }
            point = boardModel.getPoint(idx);
        }

        return point;
    }


    public BoardModelIf getBoardModel() {
        return boardModel;
    }

    public BColor getbColor() {
        return bColor;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int index) {
        this.idx=index;
    }

    @Override
    public String toString() {
        return "bcolor: " +bColor+" idx: "+idx;
    }
}
