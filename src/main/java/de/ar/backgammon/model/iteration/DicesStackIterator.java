package de.ar.backgammon.model.iteration;

import de.ar.backgammon.BColor;
import de.ar.backgammon.dices.DicesStack;
import de.ar.backgammon.dices.PipSequence;
import de.ar.backgammon.model.BoardModel;
import de.ar.backgammon.model.BoardModelIf;
import de.ar.backgammon.points.BPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

public class DicesStackIterator implements Iterator<BPoint> {

    private final DicesStack dstack;
    private final BoardModelIf boardModel;
    private final BColor bColorDirection;
    private int pointIdx;

    private ArrayList<Integer> pips;
    private ArrayList<Integer> pointsIdx;

    private boolean end_of_iteration;

    private int idx = 0;

    public DicesStackIterator(DicesStack dstack ,BoardModelIf boardModel, BColor bColorDirection, int pointIdx){
        this.dstack = dstack;
        this.boardModel = boardModel;
        this.bColorDirection = bColorDirection;
        this.pointIdx = pointIdx;
        initIterator();
    }

    private void initIterator() {
        pips = new ArrayList<>();
        pointsIdx = new ArrayList<>();
        
        HashSet<Integer> positionHash = new HashSet<>();
        end_of_iteration=false;

        for (int pip:dstack.getDices()){
            positionHash.add(pip);
        }
        for (PipSequence ps:dstack.getSequenceStack().getPipSequences()){
            positionHash.add(ps.getSum());
        }
        pips.addAll(positionHash);
        Collections.sort(pips);

        if (bColorDirection == BColor.WHITE) {
            for (int pip:pips) {
                int pos=pointIdx + pip;
                if (pos > BoardModel.POINT_IDX_LAST_BOARD_POINT){
                    pos = BoardModel.POINT_IDX_OFF_WHITE;
                }
                pointsIdx.add(pos);
            }

        }else {
            for (int pip:pips) {
                int pos=pointIdx-pip;
                if (pos < BoardModel.POINT_IDX_FIRST_BOARD_POINT){
                    pos = BoardModel.POINT_IDX_OFF_RED;
                }
                pointsIdx.add(pos);
            }

        }

        int test = 0;
    }

    @Override
    public boolean hasNext() {
        if (idx < pointsIdx.size() && !end_of_iteration){
            if (pointsIdx.get(idx) >= BoardModel.POINT_IDX_FIRST_BOARD_POINT
            &&  pointsIdx.get(idx) <= BoardModel.POINT_IDX_LAST_BOARD_POINT) {
                return true;
            }else if (pointsIdx.get(idx)==BoardModel.POINT_IDX_OFF_RED
                    || pointsIdx.get(idx)==BoardModel.POINT_IDX_OFF_WHITE){
                end_of_iteration =true;
                return true;
            }

        }
        return false;
    }

    @Override
    public BPoint next() {
        int pos= pointsIdx.get(idx);
        BPoint point =boardModel.getPoint(pos);
        idx++;
        return point;
    }

    public void setIdx(int index) {
        pointIdx=index;
    }
}
