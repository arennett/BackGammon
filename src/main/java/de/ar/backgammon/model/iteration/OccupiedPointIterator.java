package de.ar.backgammon.model.iteration;

import de.ar.backgammon.BColor;
import de.ar.backgammon.model.BoardModelIf;
import de.ar.backgammon.points.BPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

/**
 * iterates in move direction
 * from start board to the homeboard of a player(bColor)
 * it iterates only over piece occupied point of the given color
 * the bar is discounted
 */
public class OccupiedPointIterator implements Iterator<BPoint> {
    private static final Logger logger = LoggerFactory.getLogger(OccupiedPointIterator.class);
    protected final BoardModelIf boardModel;
    protected final BColor bColor;
    int idx = 0;
    PointIterator pit;

    public OccupiedPointIterator(BoardModelIf boardModel, BColor bColor,int minPointIdx,int maxPointIdx) {
        this.boardModel = boardModel;
        this.bColor = bColor;
        pit=new PointIterator(boardModel,bColor,minPointIdx,maxPointIdx);
    }

    /**
     * get next occupied point of bcolor
     * @param pit a point iterator which has the current start index
     * @return
     */
    private BPoint getNextOccPoint(PointIterator pit) {
        while((pit.hasNext())) {
            BPoint point =pit.next();
            if (!point.isEmpty() && point.getPieceColor()==bColor) {
                return point;
            }
        }
        return null;
    }

    @Override
    public boolean hasNext() {
        PointIterator testPit= new PointIterator(pit);
        BPoint point=getNextOccPoint(testPit);
        return point!=null;
    }

    @Override
    public BPoint next() {
        BPoint point = null;
        if (hasNext()){
            point=getNextOccPoint(pit);
            logger.debug("next occupied: {}" ,point);
        }

        return point;
    }
}
