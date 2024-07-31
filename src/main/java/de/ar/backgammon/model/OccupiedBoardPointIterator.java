package de.ar.backgammon.model;

import de.ar.backgammon.BColor;

public class OccupiedBoardPointIterator extends OccupiedPointIterator{
    public OccupiedBoardPointIterator(BoardModelIf boardModel, BColor bColor) {
        super(boardModel, bColor,BoardModel.POINT_IDX_FIRST_BOARD_POINT,BoardModel.POINT_IDX_LAST_BOARD_POINT);
    }
}
