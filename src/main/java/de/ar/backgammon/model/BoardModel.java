package de.ar.backgammon.model;

import de.ar.backgammon.BColor;
import de.ar.backgammon.BPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Vector;

public class BoardModel implements BoardModelIf {
    static Logger logger = LoggerFactory.getLogger(BoardModel.class);
    static int MAX_POINTS = 24;

    public static int POINT_IDX_BAR_WHITE = 0;
    public static int POINT_IDX_BAR_RED = 25;

    public static int POINT_IDX_OFF = 99;

    public static int POINT_IDX_OFF_RED = 26;
    public static int POINT_IDX_OFF_WHITE = 27;


    Vector<BPoint> points = new Vector<>();

    private BColor turn = BColor.WHITE;
    int dice1 = 0;
    int dice2 = 0;

    ;


    Bar bar;
    int startPointSelectedIdx = -1;
    int pointSelectedIdx = -1;
    private int pieceSelectedIdx = 0;
    private int startPieceSelectedIdx;


    public BoardModel() {

        initModel();
    }

    private void initModel() {
        for (int i = 0; i < MAX_POINTS + 4; i++) {
            BPoint point = new BPoint(i, this);
            points.add(point);
            setPoint(i, 0, null);
        }
        bar = new Bar(this);


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
        for (BPoint point : points) {
            point.setPieceColor(null);
            point.setPieceCount(0);
        }
        bar.clear();

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
}
