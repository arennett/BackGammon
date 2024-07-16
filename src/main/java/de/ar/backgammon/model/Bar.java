package de.ar.backgammon.model;

import de.ar.backgammon.BColor;
import de.ar.backgammon.BPoint;

public class Bar {

    private final BoardModel boardModel;
    BPoint barRed;
    BPoint barWhite;

    BPoint barOff;


    public Bar(BoardModel bmodel) {
        this.boardModel = bmodel;
        barRed = boardModel.points.get(BoardModel.POINT_IDX_BAR_RED);
        barWhite = boardModel.points.get(BoardModel.POINT_IDX_BAR_WHITE);
        clear();
    }

    public void clear() {
        barRed.clear();
        barWhite.clear();
    }

    public BPoint getBarRed() {
        return barRed;
    }

    public BPoint getBarWhite() {
        return barWhite;
    }

    public int getCount(BColor color) {
        if (color == BColor.RED) {
            return barRed.getPieceCount();
        } else {
            return barWhite.getPieceCount();
        }
    }

    public void addCount(int pcount, BColor color) {
        if (color == BColor.RED) {
            barRed.setPieceCount(barRed.getPieceCount() + pcount);
        } else {
            barWhite.setPieceCount(barWhite.getPieceCount() + pcount);
        }

    }

    public void setCount(int pcount, BColor color) {
        if (color == BColor.RED) {
            barRed.setPieceCount(pcount);
            barRed.setPieceColor(BColor.RED);
        } else {
            barWhite.setPieceCount(pcount);
            barWhite.setPieceColor(BColor.WHITE);
        }
    }

    public BPoint get(BColor color) {
        if (color == BColor.RED) {
            return barRed;
        } else {
            return barWhite;
        }
    }
}
