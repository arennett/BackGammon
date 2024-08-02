package de.ar.backgammon.model.iteration;

import de.ar.backgammon.BColor;
import de.ar.backgammon.model.BoardModel;
import de.ar.backgammon.model.BoardModelIf;

/**
 * iterates over all ocuppied board points for the commited color
 */
public class OccupiedBoardPointIterator extends OccupiedPointIterator{
    public OccupiedBoardPointIterator(BoardModelIf boardModel, BColor bColor) {
        super(boardModel, bColor, BoardModel.POINT_IDX_FIRST_BOARD_POINT,BoardModel.POINT_IDX_LAST_BOARD_POINT);
    }
}
