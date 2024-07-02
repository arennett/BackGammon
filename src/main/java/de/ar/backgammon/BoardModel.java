package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Vector;

public class BoardModel implements BoardModelIf {
    static Logger logger = LoggerFactory.getLogger(BoardModel.class);
    static int MAX_POINTS=24;
    Vector<BPoint> points =new Vector<>();
    class Bar extends HashMap<BColor,Integer> {
        public Bar(){
           clear();
        }

        public void clear(){
            super.clear();
            put(BColor.RED,0);
            put(BColor.WHITE,0);
        }

        public void addCount(int pcount, BColor bcolor) {
            replace(bcolor,bar.get(bcolor)+pcount);
        }

        public void setCount(int pcount, BColor bcolor) {
            replace(bcolor,pcount);
        }
    }
    Bar bar;
    int startPointSelectedIdx = -1;
    int pointSelectedIdx = -1;
    private int pieceSelectedIdx=0;
    private int startPieceSelectedIdx;


    public BoardModel(){

        initModel();
    }

    private void initModel() {
        for (int i=0;i<MAX_POINTS;i++){
            BPoint point =new BPoint(i,this);
            points.add(point);
            setPoint(i,0,null);
        }
        bar=new Bar();
    }

    /* the nr of selected pieces by start of a move */
    @Override
    public int getStartPointSelectedPiecesCount() {
       BPoint bPoint = getPoint(startPointSelectedIdx);
       int count = bPoint.getPieceCount()-startPieceSelectedIdx;
       return count;
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


    @Override
    public Bar getBar() {
        return bar;
    }

    @Override
    public void clear() {
        for (BPoint point:points){
            point.setPieceColor(null);
            point.setPieceCount(0);
        }
        bar.clear();

    }

    public int getPieceSelectedIdx() {
        return pieceSelectedIdx;
    }

    @Override
    public void setPieceSelectedIdx(int pieceSelectedIdx) {
        this.pieceSelectedIdx=pieceSelectedIdx;
    }

    @Override
    public int getPointSelectedIdx() {
        return pointSelectedIdx;
    }

    @Override
    public void setPointSelectedIdx(int pointSelectedIdx) {
        this.pointSelectedIdx = pointSelectedIdx;

    }

    @Override
    public int getStartPointSelectedIdx() {
        return startPointSelectedIdx;
    }

    /**
     * set the start point and piece index for a move
     * @param pointIdx
     */
    @Override
    public void setStartPointSelectedIdx(int pointIdx) {
        startPointSelectedIdx =pointIdx;
        startPieceSelectedIdx =pieceSelectedIdx;
        if(startPointSelectedIdx==-1){
            startPieceSelectedIdx=-1;
        }
        logger.debug("set start point selected idx: {} start piece idx {} "
                ,startPointSelectedIdx,startPieceSelectedIdx);
    }


    public int getStartPieceSelectedIdx() {
        return startPieceSelectedIdx;
    }

    public void setStartPieceSelectedIdx(int startPieceSelectedIdx) {
        this.startPieceSelectedIdx = startPieceSelectedIdx;
    }
}
