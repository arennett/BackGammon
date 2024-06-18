package de.ar.backgammon;

public class BPoint {
    private final int index;
    BColor pieceColor;
    
    public BPoint(int index){

        this.index = index;
    }

    public int getPieceCount() {
        return pieceCount;
    }

    public void setPieceCount(int pieceCount) {
        this.pieceCount = pieceCount;
    }

    int pieceCount = 0;

    public BColor getPieceColor() {
        return pieceColor;
    }

    public void setPieceColor(BColor pieceColor) {
        this.pieceColor = pieceColor;
    }


    public int getIndex() {
        return index;
    }

    public boolean isEmpty() {
        return getPieceCount() < 1;
    }
}
