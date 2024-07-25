package de.ar.backgammon.model;

import de.ar.backgammon.BColor;
import de.ar.backgammon.points.BPoint;
import de.ar.backgammon.points.BarPoint;
import de.ar.backgammon.points.OffPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Vector;

public class BoardModel implements BoardModelIf {
    static Logger logger = LoggerFactory.getLogger(BoardModel.class);
    static int MAX_POINTS = 24;
    static int MAX_MODEL_POINTS = MAX_POINTS+4; //+2 barpoints +2 offpoints

    public static int POINT_IDX_FIRST_BOARD_POINT = 1;
    public static int POINT_IDX_LAST_BOARD_POINT = MAX_POINTS;

    public static int POINT_IDX_BAR_WHITE = POINT_IDX_FIRST_BOARD_POINT-1;
    public static int POINT_IDX_BAR_RED = POINT_IDX_LAST_BOARD_POINT+1;

    public static int POINT_IDX_OFF_RED = MAX_POINTS+2;
    public static int POINT_IDX_OFF_WHITE = MAX_POINTS+3;
    public static int POINT_IDX_OFF = 99;


    Vector<BPoint> points = new Vector<>();

    private BColor turn = BColor.WHITE;
    int dice1 = 0;
    int dice2 = 0;

    int startPointSelectedIdx = -1;
    int pointSelectedIdx = -1;
    private int pieceSelectedIdx = 0;
    private int startPieceSelectedIdx;


    public BoardModel() {

        initModel();
    }

    private void initModel() {

        //0
        BarPoint wbar = new BarPoint(POINT_IDX_BAR_WHITE,this,BColor.WHITE);
        points.add(wbar);

        //1-24
        for (int i = 1; i <= MAX_POINTS; i++) {
            BPoint point = new BPoint(i, this);
            points.add(point);
            setPoint(i, 0, null);

        }
        //25-27
        BarPoint rbar = new BarPoint(POINT_IDX_BAR_RED,this,BColor.RED);
        points.add(rbar);
        OffPoint roff = new OffPoint(POINT_IDX_OFF_RED,this,BColor.RED);
        points.add(roff);
        OffPoint woff = new OffPoint(POINT_IDX_OFF_WHITE,this,BColor.WHITE);
        points.add(woff);
    }

    /* the nr of selected pieces by start of a move */
    @Override
    public int getStartPointSelectedPiecesCount() {
        BPoint bPoint = getPoint(startPointSelectedIdx);
        int count = bPoint.getPieceCount() - startPieceSelectedIdx;
        return count;
    }

    @Override
    public void setPoint(int pidx, int pcount, BColor bcolor) {
        BPoint bpoint = points.elementAt(pidx);
        bpoint.setPieceCount(pcount,bcolor);
    }

    @Override
    public BPoint getPoint(int i) {
        return points.elementAt(i);
    }



    @Override
    public void clear() {
        for (BPoint point : points) {
            point.setPieceCount(0,null);
        }
    }



    @Override
    public int getPiecesCount(BColor bcolor) {
        int sum = 0;
        for (int i = POINT_IDX_BAR_WHITE; i <= POINT_IDX_BAR_RED; i++) {
            BPoint point = getPoint(i);
            if (point.getPieceColor() == bcolor) {
                sum += point.getPieceCount();
            }

        }
        return sum;
    }

    public int getPieceSelectedIdx() {
        return pieceSelectedIdx;
    }

    @Override
    public void setPieceSelectedIdx(int pieceSelectedIdx) {
        this.pieceSelectedIdx = pieceSelectedIdx;
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
     *
     * @param pointIdx
     */
    @Override
    public void setStartPointSelectedIdx(int pointIdx) {
        startPointSelectedIdx = pointIdx;
        startPieceSelectedIdx = pieceSelectedIdx;
        if (startPointSelectedIdx == -1) {
            startPieceSelectedIdx = -1;
        }
        logger.debug("set start point selected idx: {} start piece idx {} "
                , startPointSelectedIdx, startPieceSelectedIdx);
    }


    public int getStartPieceSelectedIdx() {
        return startPieceSelectedIdx;
    }

    public void setStartPieceSelectedIdx(int startPieceSelectedIdx) {
        this.startPieceSelectedIdx = startPieceSelectedIdx;
    }

    public BColor getTurn() {
        return turn;
    }

    public void setTurn(BColor turn) {
        this.turn = turn;
    }

    @Override
    public int getDice1() {
        return dice1;
    }

    @Override
    public void setDice1(int dice1) {
        this.dice1 = dice1;
    }

    @Override
    public int getDice2() {
        return dice2;
    }

    @Override
    public void setDice2(int dice2) {
        this.dice2 = dice2;
    }

    @Override
    public boolean isAllPiecesAtHome(BColor pieceColor) {
        int piecesOnBoard = getPiecesCount(pieceColor);
        int piecesAtHome = getHomePiecesCount(pieceColor);
        return  piecesAtHome==piecesOnBoard;
    }

    @Override
    public int getHomePiecesCount(BColor pieceColor) {
        int count = 0;
        HomeBoardIterator hit = new HomeBoardIterator(this, pieceColor);
        while (hit.hasNext()) {
            BPoint point = hit.next();
            if (point.getPieceColor()==pieceColor){
                count += point.getPieceCount();
            }

        }
        logger.debug("Homeboard {} has {} pieces",pieceColor,count);
        return count;
    }

    @Override
    public ArrayList<Integer> getHomePointMaxDuo(BColor bcolor){
        int maxPoint1=-1;
        int maxPoint2=-1;

        HomeBoardIterator hit = new HomeBoardIterator(this,bcolor);
        int pip=0; //1..6
        while (hit.hasNext()){
            BPoint point = hit.next();
            pip++;
            if (point.getPieceColor()==bcolor&& !point.isEmpty()){
                maxPoint2=maxPoint1;
                maxPoint1=pip;
                if(point.getPieceCount()>1){
                    maxPoint2=pip;
                }
            }
        }
        ArrayList<Integer> arr =new ArrayList<>();
        arr.add(maxPoint1);
        arr.add(maxPoint2);
        return arr;

    }

    @Override
    public OffPoint getOffPoint(BColor bColor) {
        assert bColor != null;
        if(bColor==BColor.RED){
            return (OffPoint) getPoint(BoardModel.POINT_IDX_OFF_RED);
        }else{
            return (OffPoint) getPoint(BoardModel.POINT_IDX_OFF_WHITE);
        }
    }

    public BarPoint getBarPoint(BColor bColor) {
        assert bColor != null;
        if(bColor==BColor.RED){
            return (BarPoint) getPoint(BoardModel.POINT_IDX_BAR_RED);
        }else{
            return (BarPoint) getPoint(BoardModel.POINT_IDX_BAR_WHITE);
        }
    }
}
