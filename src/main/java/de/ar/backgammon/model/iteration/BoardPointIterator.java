package de.ar.backgammon.model.iteration;

import de.ar.backgammon.BColor;
import de.ar.backgammon.model.BoardModel;
import de.ar.backgammon.model.BoardModelIf;

public class BoardPointIterator extends PointIterator{
    public BoardPointIterator(BoardModelIf boardModel, BColor bColor) {
        super(boardModel, bColor, BoardModel.POINT_IDX_FIRST_BOARD_POINT, BoardModel.POINT_IDX_LAST_BOARD_POINT);
    }
}
