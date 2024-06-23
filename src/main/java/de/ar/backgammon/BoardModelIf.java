package de.ar.backgammon;

public interface BoardModelIf {



    int getStartPointSelectedPiecesCount();

    void setPoint(int pidx, int psize, BColor bcolor);

    BPoint getPoint(int i);


    void setBar(int pcount, BColor bcolor);

    void clear();

    int getStartPointSelectedIdx();
    int getPointSelectedIdx();

    void setStartPointSelectedIdx(int pointIdx);
    void setPointSelectedIdx(int pointIdx);

    int getPieceSelectedIdx();
    void setPieceSelectedIdx(int pieceSelectedIdx);
}
