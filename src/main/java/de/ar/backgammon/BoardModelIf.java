package de.ar.backgammon;

public interface BoardModelIf {



    int getStartPointSelectedPiecesCount();

    void setPoint(int pidx, int psize, BColor bcolor);

    BPoint getPoint(int i);


    BoardModel.Bar getBar();

    void clear();

    int getBoardPiecesCount();
    int getBoardPiecesCount(BColor bcolor);

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
}
