package de.ar.backgammon.model;

import de.ar.backgammon.BColor;
import de.ar.backgammon.BException;
import de.ar.backgammon.SeqSelectDialog;
import de.ar.backgammon.dices.Dices;
import de.ar.backgammon.dices.DicesStack;
import de.ar.backgammon.dices.PipSequence;
import de.ar.backgammon.dices.SequenceStack;
import de.ar.backgammon.model.iteration.HomeBoardIterator;
import de.ar.backgammon.moves.Move;
import de.ar.backgammon.points.BPoint;
import de.ar.backgammon.points.BarPoint;
import de.ar.backgammon.points.OffPoint;
import de.ar.backgammon.validation.MoveValidator;
import de.ar.backgammon.validation.MoveValidatorIf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import static de.ar.backgammon.ConstIf.MAX_PIP;

public class BoardModel implements BoardModelIf {
    static Logger logger = LoggerFactory.getLogger(BoardModel.class);
    static int MAX_POINTS = 24;
    public static int POINT_IDX_FIRST_BOARD_POINT = 1;
    public static int POINT_IDX_LAST_BOARD_POINT = MAX_POINTS;

    public static int POINT_IDX_BAR_WHITE = POINT_IDX_FIRST_BOARD_POINT-1;
    public static int POINT_IDX_BAR_RED = POINT_IDX_LAST_BOARD_POINT+1;

    public static int POINT_IDX_OFF_RED = MAX_POINTS+2;
    public static int POINT_IDX_OFF_WHITE = MAX_POINTS+3;
    public static int POINT_IDX_OFF = 99;
    private  MoveValidatorIf moveValidator;


    Vector<BPoint> points = new Vector<>();

    private BColor turn = BColor.WHITE;
    Dices dices = new Dices(0,0);
    DicesStack dicesStack;


    int startPointSelectedIdx = -1;
    int pointSelectedIdx = -1;
    private int pieceSelectedIdx = 0;
    private int startPieceSelectedIdx;
    BoardModelWriterIf bwriter = new BoardModelWriter();
    BoardModelReaderIf breader = new BoardModelReader();

    public BoardModel() {
        initModel();
    }

    private void initModel() {
        logger.debug("init model...");
        dicesStack=new DicesStack(this);
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

        dicesStack.loadDices(dices);

        logger.debug("model inited");
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
        //logger.debug("set start point selected idx: {} start piece idx {} "
        //        , startPointSelectedIdx, startPieceSelectedIdx);
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
    public boolean isAllPiecesAtHome(BColor pieceColor) {
        int piecesOnBoard = getPiecesCount(pieceColor);
        int piecesAtHome = getHomePiecesCount(pieceColor);
        return  piecesAtHome > 0 && piecesAtHome==piecesOnBoard;
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
        //logger.debug("Homeboard {} has {} pieces",pieceColor,count);
        return count;
    }

    /**
     * returns all pips of the homeboard from min to max
     * @param bcolor
     * @return
     */
    @Override
    public ArrayList<Integer> getMinHomePipArray(BColor bcolor){

        ArrayList<Integer> arrFromMinToMax =new ArrayList<>();

        HomeBoardIterator hit = new HomeBoardIterator(this,bcolor);
        int pip=0; //1..6
        while (hit.hasNext()){
            BPoint point = hit.next();
            pip++;
            if (!point.isEmpty() && point.getPieceColor()==bcolor){
                for (int i=0;i < point.getPieceCount();i++){
                    arrFromMinToMax.add(pip);
                }
            }
        }

        return arrFromMinToMax;

    }
    @Override

    /**
     * returns all pips of the homeboard from max to min
     * @param bcolor
     * @return
     */
    public ArrayList<Integer> getMaxHomePipArray(BColor bColor){

        ArrayList<Integer> arrFromMinToMax = getMinHomePipArray(bColor);
        ArrayList<Integer> arrFromMaxToMin = new ArrayList<>();
        for (int i = arrFromMinToMax.size()-1;i >= 0;i--){
            arrFromMaxToMin.add(arrFromMinToMax.get(i));
        }
        return arrFromMaxToMin;

    }

    @Override
    public void copy(BoardModel toModel) throws IOException, BException {
            bwriter.write("tempModel", this);
            breader.readModel(toModel,"tempModel");
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

    @Override
    public Dices getDices() {
        return dices;
    }

    @Override
    public void setDices(Dices dices) {
        this.dices=dices;
        dicesStack.loadDices(dices);
    }

    @Override
    public void setDice1(int pip) {
        dices.dice1=pip;
        dicesStack.loadDices(dices);
    }

    @Override
    public void setDice2(int pip) {
        dices.dice2=pip;
        dicesStack.loadDices(dices);

    }

    @Override
    public DicesStack getDicesStack() {
        return this.dicesStack;
    }


    /**
     * move one or more pieces from position to position
     * @param move
     * @param _spc number of pieces to move
     * @param setMode true means no validation
     * @return
     */
    @Override
    public boolean move(Move move, int _spc, boolean setMode) {
        logger.debug("### start move ### {} ###",move);
        int spc =_spc;
        if (move.isBarMove()) {
            spc = 1;
        }

        if (!setMode) {
            if (!moveValidator.isValid(move,spc)) {

                return false;
            }
        } else {
            //setmode
            subMove(move, spc,setMode);
            return true;
        }

        // get valid sequences from sequenceControl depending on the move range
        // if psArray.size == 2 and sequences have equal sum
        // and at least one ps has blots, the user has to choose a pointSequence
        // otherwise we take the first sequence

        ArrayList<PipSequence> psArray
                = getDicesStack().getSequenceStack().getValidSequences(move, spc, getTurn());
        boolean hasBlot = false;


        for (PipSequence ps : psArray) {
            hasBlot = getDicesStack().getSequenceStack().psHasBlots(ps, move,spc,getTurn());
            if (hasBlot) {
                break;
            }
        }


        PipSequence psSelect = null;

        //default
        if (!psArray.isEmpty()) {
            psSelect = psArray.get(0);
        }

        // two move sequences are possible

        if (psArray.size() == 2) {
            psSelect = psArray.get(0);
            if (psArray.get(0).getSum() == psArray.get(1).getSum()) {
                if (hasBlot) {
                    SequenceStack.BlotArray ba0 = getDicesStack().getSequenceStack().getBlotArray(
                            psArray.get(0), move,spc,getTurn());
                    if (!ba0.isEmpty()) {
                        logger.debug("blots detected on: {}", "" + ba0);
                    }

                    SequenceStack.BlotArray ba1 = getDicesStack().getSequenceStack().getBlotArray(
                            psArray.get(1), move,spc,getTurn());
                    if (!ba1.isEmpty()) {
                        logger.debug("blots detected on: {}", "" + ba1);
                    }

                    if (!ba0.equals(ba1)) {
                        logger.debug("user sequence selection requested !");
                        SeqSelectDialog sq = new SeqSelectDialog();
                        sq.setSequences(psArray);
                        sq.setVisible(true);
                        logger.debug("OPTION : {}", sq.getOption());
                        if (sq.getOption() < 0) {
                            return false;
                        } else {
                            psSelect = psArray.get(sq.getOption());
                        }
                    } else {
                        logger.debug("equal BlotArrays ba0: {}", ba0);
                        logger.debug("equal BlotArrays ba1: {}", ba1);

                    }

                }
            }

        }

        int direction = 0;
        if (getTurn() == BColor.WHITE) {
            direction = 1;
        } else {
            direction = -1;
        }

        if (psSelect != null) {
            int pos = move.from;
            int pips=0;
            for (int pip : psSelect) {
                pips+=pip*spc;
                if (pips <= psSelect.getSum()){
                    int subto = pos + pip * direction;
                    subMove(new Move(pos, subto), spc,setMode);
                    pos = subto;
                }else{
                    break;
                }

            }
        } else {
            // no sequence
            assert move.getRange(getTurn()) <= MAX_PIP;
            subMove(move, spc,setMode);
        }


        logger.debug("### end move ### {} ###",move);
        return true;

    }


    @Override
    public void subMove(Move move, int spc,boolean setMode) {

        logger.debug("sub move {}", move);

        //if not is setmode delegate point 0/25 (bar points) to offpoints
        if (!setMode) {
            assert move.getRange(getTurn()) <= MAX_PIP;
            if (move.to == BoardModel.POINT_IDX_BAR_WHITE) {
                move.to = BoardModel.POINT_IDX_OFF_RED;
            } else if (move.to == BoardModel.POINT_IDX_BAR_RED) {
                move.to = BoardModel.POINT_IDX_OFF_WHITE;
            }
        }


        BPoint bpFrom = this.getPoint(move.from);
        BPoint bpTo = this.getPoint(move.to);

        BColor colorFrom = bpFrom.getPieceColor();
        BColor colorTo = bpTo.getPieceColor();
        bpFrom.setPieceCount(bpFrom.getPieceCount() - spc, colorFrom);

        if (bpTo.getPieceCount() == 1 && colorTo != colorFrom) {
            // blot
            this.getBarPoint(colorTo).addCount(1);
            logger.debug("moved {} (blot)", move);
            bpTo.setPieceCount(spc, colorFrom);
        } else {
            // no blot
            bpTo.setPieceCount(bpTo.getPieceCount() + spc, colorFrom);
            logger.debug("moved {}", move);
        }

        if (!setMode) {
            // try to remove points from stack
            dicesStack.removePipsFromStack(move.getRange(getTurn()), spc);
        }
    }

    @Override
    public void setMoveValidator(MoveValidatorIf moveValidator) {
        this.moveValidator=moveValidator;
    }
    public MoveValidatorIf getMoveValidator() {
        return moveValidator;
    }

}
