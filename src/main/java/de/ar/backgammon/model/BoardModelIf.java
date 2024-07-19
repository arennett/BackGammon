package de.ar.backgammon.model;

import de.ar.backgammon.BColor;
import de.ar.backgammon.BPoint;

import java.util.ArrayList;

public interface BoardModelIf {

    int getStartPointSelectedPiecesCount();

    void setPoint(int pidx, int psize, BColor bcolor);

    BPoint getPoint(int i);

    void clear();

    int getPiecesCount(BColor bcolor);

    int getStartPointSelectedIdx();
    int getPointSelectedIdx();

    void setStartPointSelectedIdx(int pointIdx);
    void setPointSelectedIdx(int pointIdx);

    int getPieceSelectedIdx();
    void setPieceSelectedIdx(int pieceSelectedIdx);

    BColor getTurn();

    void setTurn(BColor bColor);

    int getDice1();

    void setDice1(int dice1);

    int getDice2();

    void setDice2(int dice2);

    boolean isAllPiecesAtHome(BColor pieceColor);

    int getHomePiecesCount(BColor pieceColor);

    ArrayList<Integer> getHomePointMaxDuo(BColor bcolor);

     BPoint getOffPoint(BColor bColor);

    BPoint getBarPoint(BColor bColor);
}
