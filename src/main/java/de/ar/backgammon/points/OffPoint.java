package de.ar.backgammon.points;

import de.ar.backgammon.BColor;
import de.ar.backgammon.model.BoardModelIf;

public class OffPoint extends BPoint{
    private final BColor bcolor;

    public OffPoint(int index, BoardModelIf bModel, BColor color) {
        super(index, bModel);
        this.bcolor = color;
    }

    @Override
    public void setPieceCount(int pieceCount, BColor pieceColor) {
        super.setPieceCount(pieceCount, bcolor);
    }
    public void setPieceCount(int pieceCount) {
        super.setPieceCount(pieceCount, bcolor);
    }

    @Override
    public void addCount(int count,BColor color) {
        super.addCount(count,bcolor);
    }
    public void addCount(int count) {
        super.addCount(count, bcolor);
    }
}
