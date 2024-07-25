package de.ar.backgammon.model;

import de.ar.backgammon.BColor;
import de.ar.backgammon.points.BPoint;

import java.util.Iterator;

/**
 * iterates through all points of a homeboard
 */
class HomeBoardIterator implements Iterator<BPoint> {
    private final BoardModel boardModel;
    private final BColor bColor;
    int idx = 0;

    /**
     * iterates through all points of bColor's homeboard from pip 1 to 6
     * it doesn't check the piececolor of the point
     * @param boardModel
     * @param bColor
     */
    public HomeBoardIterator(BoardModel boardModel, BColor bColor) {
        this.boardModel = boardModel;

        this.bColor = bColor;
        if (bColor == BColor.RED) {
            idx = 0;
        } else {
            idx = 25;
        }

    }

    @Override
    public boolean hasNext() {
        if (bColor == BColor.RED) {
            return idx + 1 <= 6;
        } else {
            return idx - 1 >= 19;
        }
    }

    @Override
    public BPoint next() {
        BPoint point = null;
        if (hasNext()) {
            if (bColor == BColor.RED) {
                idx++;
            } else {
                idx--;
            }
            point = boardModel.getPoint(idx);
        }

        return point;
    }
}
