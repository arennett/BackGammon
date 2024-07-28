package de.ar.backgammon.model;

import de.ar.backgammon.BColor;
import de.ar.backgammon.points.BPoint;

import java.util.Iterator;

/**
 * iterates in move direction
 * from start board to the homeboard of a player(bColor)
 * the bar is discounted
 */
public class BoardMoveIterator implements Iterator<BPoint> {
    private final BoardModelIf boardModel;
    private final BColor bColor;
    int idx = 0;

    public BoardMoveIterator(BoardModelIf boardModel, BColor bColor) {
        this.boardModel = boardModel;

        this.bColor = bColor;
        if (bColor == BColor.WHITE) {
            idx = BoardModel.POINT_IDX_FIRST_BOARD_POINT-1;
        } else {
            idx = BoardModel.POINT_IDX_LAST_BOARD_POINT+1;
        }

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
