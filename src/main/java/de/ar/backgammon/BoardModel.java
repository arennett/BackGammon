package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Vector;

public class BoardModel implements BoardModelIf {
    static Logger logger = LoggerFactory.getLogger(BoardModel.class);
    static int MAX_POINTS=24;
    Vector<BPoint> points =new Vector<>();
    BPoint bar;
    int pointSelectedIdx = -1;

    public BoardModel(){

        initModel();
    }

    private void initModel() {
        for (int i=0;i<MAX_POINTS;i++){
            BPoint point =new BPoint(i);
            points.add(point);
            setPoint(i,0,null);
        }
        bar = new BPoint(-1);
    }

    @Override
    public void setPoint(int pidx, int pcount, BColor bcolor) {
        BPoint bpoint= points.elementAt(pidx);
        bpoint.setPieceColor(bcolor);
        bpoint.setPieceCount(pcount);
    }

    @Override
    public BPoint getPoint(int i) {
        return points.elementAt(i);
    }

    public BPoint getPointSelected() {
        return points.elementAt(this.getPointSelectedIdx());
    }

    @Override
    public void setBar(int pcount, BColor bcolor) {
        bar.setPieceColor(bcolor);
        bar.setPieceCount(pcount);
    }

    @Override
    public void clear() {
        for (BPoint point:points){
            point.setPieceColor(null);
            point.setPieceCount(0);
        }
        bar.setPieceColor(null);
        bar.setPieceCount(0);

    }

      @Override
    public int getPointSelectedIdx() {
        return pointSelectedIdx;
    }

    @Override
    public void setPointSelectedIdx(int pointIdxPressed) {
        pointSelectedIdx=pointIdxPressed;
    }




}
