package de.ar.backgammon.model.iteration;

import de.ar.backgammon.BColor;
import de.ar.backgammon.model.BoardModelIf;
import de.ar.backgammon.points.BPoint;

import java.util.Iterator;

/**
 * iterates through all points of a homeboard
 */
public class HomeBoardIterator implements Iterator<BPoint> {
    private final BoardModelIf boardModel;
    private final PointIterator pointIterator;
    private final BColor bColor;
    int idx = 0;

    /**
     * iterates through all points of bColor's homeboard from  1 to 6
     * for red and 24 to 19 for white.
     * it doesn't check the piececolor of the point
     * for red player we iterate internally whith a white point iterator
     * and vice versa
     * @param boardModel
     * @param bColor
     */
    public HomeBoardIterator(BoardModelIf boardModel, BColor bColor) {
        this.boardModel = boardModel;
        this.bColor = bColor;
        // inverse color !!!
        BColor pitColor=BColor.getOtherColor(bColor);
        if (pitColor==BColor.WHITE){
            pointIterator = new PointIterator(boardModel,pitColor,1,6);
        }else{
            pointIterator = new PointIterator(boardModel,pitColor,19,24);
        }
    }

    @Override
    public boolean hasNext() {
       return pointIterator.hasNext();
    }

    @Override
    public BPoint next() {
        return pointIterator.next();
    }
}
