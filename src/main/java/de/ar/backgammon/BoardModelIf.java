package de.ar.backgammon;

public interface BoardModelIf {

    void setPoint(int pidx, int psize, BColor bcolor);

    BPoint getPoint(int i);


    void setBar(int pcount, BColor bcolor);

    void clear();
}
