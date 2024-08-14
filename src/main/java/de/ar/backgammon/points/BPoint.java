package de.ar.backgammon.points;

import de.ar.backgammon.BColor;
import de.ar.backgammon.model.BoardModel;
import de.ar.backgammon.model.BoardModelIf;

/**
 * A BPoint represents the field where the pieces on
 */
public class BPoint {
    private final int index;
    private final BoardModelIf bModel;
    private BColor pieceColor;
    private int pieceCount = 0;
    
    public BPoint(int index,BoardModelIf bModel){
        this.index = index;
        this.bModel = bModel;
    }

    public boolean isSelected() {
        return bModel.getPointSelectedIdx()==index;
    }

    /**
     *
     * @return the nr of selected pieces for the current selection
     */
    public int getSelectedPiecesCount() {
        if (isSelected() && !isEmpty()) {
            int psidx=bModel.getPieceSelectedIdx();
            if(psidx >= 0){
                int count = pieceCount-psidx;
                return count;
            }
        }
        return 0;
    }

    public int getPieceCount() {
        return pieceCount;
    }

    public void setPieceCount(int pieceCount,BColor pieceColor) {
        this.pieceCount = pieceCount;
        this.pieceColor=pieceColor;
        if(pieceCount > 0){
            assert pieceColor!=null;
        }
    }

    public BColor getPieceColor() {
        return pieceColor;
    }


    public int getIndex() {
        return index;
    }

    public boolean isEmpty() {
        return getPieceCount() < 1;
    }

    @Override
    public String toString() {
        String pcol=!isEmpty()?getPieceColor().toString():"";

        return "P[" + getIndex()+ "](#"+pcol+"#"+pieceCount+")";
    }

    public void clear() {
        setPieceCount(0,null);

    }

    public boolean isBarPoint() {
        return getIndex()== BoardModel.POINT_IDX_BAR_WHITE || getIndex()==BoardModel.POINT_IDX_BAR_RED;
    }

    public boolean isOffPoint(){
        return getIndex()== BoardModel.POINT_IDX_OFF_WHITE || getIndex()==BoardModel.POINT_IDX_OFF_RED;
    }

    public void addCount(int i,BColor color) {
        pieceCount+=i;
        pieceColor=color;
        if(pieceCount > 0){
            assert pieceColor!=null;
        }
    }
}
