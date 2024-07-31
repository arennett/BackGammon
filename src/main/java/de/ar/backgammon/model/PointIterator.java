package de.ar.backgammon.model;

import de.ar.backgammon.BColor;
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


    private final int minPointIdx;
    private final int maxPointIdx;
    int idx = 0;

    public PointIterator(BoardModelIf boardModel, BColor bColor,int minPointIdx,int maxPointIdx) {
        this.boardModel = boardModel;
        this.bColor = bColor;
        this.minPointIdx = minPointIdx;
        this.maxPointIdx = maxPointIdx;
        if (bColor == BColor.WHITE) {
            idx = minPointIdx-1;
        } else {
            idx = this.maxPointIdx +1;
        }

    }


    /**
     * create a pointiterator with the current index
     * @param pit
     */
    public PointIterator(PointIterator pit) {
        this.idx= pit.idx;
        this.minPointIdx =pit.getMinPointIdx();
        this.maxPointIdx =pit.getMaxPointIdx();
        this.boardModel=pit.boardModel;
        this.bColor=pit.bColor;
    }

    @Override
    public boolean hasNext() {
        if (bColor == BColor.WHITE) {
            return idx + 1 <= maxPointIdx;
        } else {
            return idx - 1 >= minPointIdx;
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

    public void setbColor(BColor bColor) {
        this.bColor = bColor;
    }

    public int getMinPointIdx() {
        return minPointIdx;
    }

    public int getMaxPointIdx() {
        return maxPointIdx;
    }

    @Override
    public String toString() {
        return "bcolor: " +bColor+" idx: "+idx;
    }
}
