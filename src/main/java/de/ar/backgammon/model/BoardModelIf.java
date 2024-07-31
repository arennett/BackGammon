package de.ar.backgammon.model;

import de.ar.backgammon.BColor;
import de.ar.backgammon.dices.Dices;
import de.ar.backgammon.dices.DicesStack;
import de.ar.backgammon.points.BPoint;
import de.ar.backgammon.points.BarPoint;
import de.ar.backgammon.points.OffPoint;

import java.util.ArrayList;

public interface BoardModelIf {

    int getStartPointSelectedPiecesCount();

    void setPoint(int pidx, int psize, BColor bcolor);

    BPoint getPoint(int i);

    void clear();

    int getPiecesCount(BColor bcolor);

    int getStartPointSelectedIdx();

    void setStartPointSelectedIdx(int pointIdx);

    int getPointSelectedIdx();

    void setPointSelectedIdx(int pointIdx);

    int getPieceSelectedIdx();

    void setPieceSelectedIdx(int pieceSelectedIdx);

    BColor getTurn();

    void setTurn(BColor bColor);

    boolean isAllPiecesAtHome(BColor pieceColor);

    int getHomePiecesCount(BColor pieceColor);

    ArrayList<Integer> getHomePointMaxDuo(BColor bcolor);

    OffPoint getOffPoint(BColor bColor);

    BarPoint getBarPoint(BColor bColor);

    Dices getDices();

    void setDices(Dices dices);

    void setDice1(int count);
    void setDice2(int count);

    DicesStack getDicesStack();
}
