package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Vector;

import static de.ar.backgammon.BoardModel.MAX_POINTS;
import static de.ar.backgammon.ConstIf.MAX_PIECES_ON_POINT;

public class GameControl {
    private static final Logger logger = LoggerFactory.getLogger(GameControl.class);


    private boolean running = false;


    private final Game game;
    private final BoardModelIf boardModel;
    private final BoardPanel boardPanel;
    private final BoardModelReaderIf bmReader;
    private final BoardModelWriterIf bmWriter;
    private final DicesControl dicesControl;

    private final SequenceControl sequenceControl;
    private ButtonPanel buttonPanel;
    private ButtonPanelControl bpControl;

    ArrayList<PipSequence> psArray = new ArrayList<>();


    public GameControl(Game game, BoardModelIf boardModel,
                       BoardPanel boardPanel,
                       BoardModelReaderIf bmReader,
                       BoardModelWriterIf bmWriter,
                       DicesControl dicesControl,
                       SequenceControl pointSequenceControl) {
        this.game = game;
        this.boardModel = boardModel;
        this.boardPanel = boardPanel;
        this.bmReader = bmReader;
        this.bmWriter = bmWriter;
        this.dicesControl = dicesControl;
        this.sequenceControl = pointSequenceControl;
    }

    /**
     * user move request by dragging one or more pieces to a target point
     * after throwing the dices
     * in set mode no dices throwing necessary
     *
     * @param from index of from point
     * @param to   index of to point
     * @return
     */
    public boolean moveRequest(int from, int to) {
        if (to==BoardModel.POINT_IDX_OFF) {
            BPoint bfrom= boardModel.getPoint(from);
            if (bfrom.getPieceColor()==BColor.RED) {
                to=BoardModel.POINT_IDX_OFF_RED;
            }else{
                to=BoardModel.POINT_IDX_OFF_WHITE;
            }
        }
        logger.debug("moveRequest from: {} to: {} ", from, to);
        if (bpControl.isSetMode()) {
            move(from, to);
            buttonPanel.updateComponents();
            return true;
        }
        if (!running) {
            game.message_error("please start game!");
            return false;
        }
        if (dicesControl.getDicesState() != DicesControl.DicesState.THROWN) {
            game.message_error("throw the dices!");
            return false;
        }

        boolean ret= move(from, to);
        buttonPanel.updateComponents();
        return ret;

    }

    public int getRange(int from, int to) {
        int range = 0;
        if (getTurn() == BColor.WHITE) {
            range = to - from;
        } else {
            range = from - to;
        }
        assert range > 0;
        return range;
    }



    /**
     * moves one or more pieces to a target point
     * after validating the move
     *
     * @param from
     * @param to
     * @return true if pieces where moved
     */
    private boolean move(int from, int to) {
        int spc = boardModel.getStartPointSelectedPiecesCount();
        if (isBarMove(from)) {
            spc = 1;
        }

        if (!bpControl.isSetMode()) {
            if (!validateMove(from, to)) {

                return false;
            }
        } else {
            //setmode
            sub_move(from, to, spc);
            return true;
        }

        // psArray is set by move validation
        // if psArray.size == 2 and sequences have equal sum
        // and at least one ps has blots, the user has to choose a pointSequence
        // otherwise we take the first sequence
        boolean hasBlot = false;

        assert psArray != null;


        for (PipSequence ps : psArray) {
            hasBlot = sequenceControl.psHasBlots(ps, from, to);
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
                    SequenceControl.BlotArray ba0 = sequenceControl.getBlotArray(psArray.get(0), from, to);
                    if (!ba0.isEmpty()) {
                        logger.debug("blots detected on: {}", "" + ba0);
                    }

                    SequenceControl.BlotArray ba1 = sequenceControl.getBlotArray(psArray.get(1), from, to);
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
            int pos = from;
            for (int p : psSelect) {
                int subto = pos + p * direction;
                sub_move(pos, subto, spc);
                pos = subto;
            }
        } else {
            // no sequence
            sub_move(from, to, spc);
        }

        if (dicesControl.getDicesState() == DicesControl.DicesState.READY) {
            switch_turn();
        }

        boardPanel.repaint();
        return true;

    }

    /**
     * a submove range is maximum one pip long. If the main move is
     * longer than a maximum pip (6) a pip sequence devides the move
     * into sub moves
     *
     * @param from
     * @param to
     * @param spc
     */
    public void sub_move(int from, int to, int spc) {
        BPoint bpFrom = boardModel.getPoint(from);
        BPoint bpTo = boardModel.getPoint(to);

        bpFrom.setPieceCount(bpFrom.getPieceCount() - spc);

        if (bpTo.getPieceCount() == 1 && bpTo.getPieceColor() != bpFrom.getPieceColor()) {
            // blot
            boardModel.getBar().addCount(1, bpTo.getPieceColor());
            logger.debug("moved from {} to {} (blot)", bpFrom, bpTo);
            bpTo.setPieceCount(spc);
        } else {
            // no blot
            bpTo.setPieceCount(bpTo.getPieceCount() + spc);
            logger.debug("moved from {} to {}", bpFrom, bpTo);
        }
        if (bpTo == boardModel.getBar().getBarRed() || bpTo == boardModel.getBar().getBarWhite()) {
            // do nothing
        } else {
            bpTo.setPieceColor(bpFrom.getPieceColor());
        }

        if (!bpControl.isSetMode()) {
            // try to remove points from stack
            dicesControl.removePipsFromStack(getRange(from, to), spc);
        }
    }

    public boolean isBarMove(int from) {
        boolean isBarMove = from == BoardModel.POINT_IDX_BAR_WHITE || from == BoardModel.POINT_IDX_BAR_RED;
        return isBarMove;
    }
    public boolean isOffMove(int from,int to) {
        boolean isOffMove =   from > BoardModel.POINT_IDX_BAR_WHITE
                            && from < BoardModel.POINT_IDX_BAR_RED
                            && ( to == BoardModel.POINT_IDX_OFF_WHITE
                               ||to ==BoardModel.POINT_IDX_OFF_RED );
        return isOffMove;
    }




    private boolean validateMove(int from, int to) {
        boolean ret = false;

        BPoint bpFrom = boardModel.getPoint(from);
        BPoint bpTo = boardModel.getPoint(to);

        if (bpFrom.getPieceColor() != getTurn()){
            game.message_error("wrong color. Turn is "+getTurn().getString());

            return false;
        }

        int spc;
        if (isBarMove(from)) {
            spc = 1;

        }else{
            spc = boardModel.getStartPointSelectedPiecesCount();
        }



        if (spc < 1) {
            game.message_error("no pieces selected");
            return false;
        }
        if (spc > 4) {
            game.message_error("you can only select up to 4 pieces");
            return false;
        }

        if (isOffMove(from,to)){
            logger.debug ("offmove detected");
            if (!boardModel.isAllPiecesAtHome(bpFrom.getPieceColor())){
                game.message_error("not all pieces at home");
                return false;
            }
        }

        if (getTurn() == BColor.RED && !boardModel.getBar().getBarRed().isEmpty()){
            if (bpFrom!=boardModel.getBar().getBarRed()){
                game.message_error("you have pieces on the bar");
                return false;
            }
        }
        if (getTurn() == BColor.WHITE && !boardModel.getBar().getBarWhite().isEmpty()){
            if (bpFrom!=boardModel.getBar().getBarWhite()){
                game.message_error("you have pieces on the bar");
                return false;
            }
        }

        ret = isValidPoint(bpTo, spc);

        if (!ret) {
            // unvalid point
            return false;
        }

        if (getTurn() == BColor.WHITE) {
            ret = to > from;

        } else {
            ret = to < from;
        }
        if (!ret) {
            game.message_error("wrong direction");
            return false;
        }

        //check dices

        int range = getRange(from, to);

        psArray = sequenceControl.getValidSequences(from, to, spc);
        if (psArray.isEmpty()) {
            // maybe only one pip on stack
            logger.debug("no valid sequences found");
            if (range <= 6) {
                ret = dicesControl.checkIfMoveIsOnStack(range, spc);
                if (!ret) {
                    game.message_error("not allowed, look at the dices");
                    return false;
                }
            } else {
                game.message_error("not allowed, look at the dices");
                return false;
            }
        }


        return true;
    }

    /**
     * basic validation, used for sequence and target points
     *
     * @param point
     * @param spc
     * @return
     */
    public boolean isValidPoint(BPoint point, int spc) {
        if (point.getPieceCount() > 1) {
            if (point.getPieceColor() != getTurn()) {
                game.message_error("wrong point color");
                logger.debug("validate point: {} ,wrong point color", point);
                return false;
            }
        }

        if (point.getPieceCount() + spc > MAX_PIECES_ON_POINT) {
            game.message_error("you can only put up to 5 pieces on a field");
            logger.debug("validate point: {} ,max 5 pieces", point);
            return false;
        }

        if (point == boardModel.getBar().getBarRed() || point == boardModel.getBar().getBarWhite()) {
            game.message_error("this is not a valid point");
            logger.debug("validate point: bar point not valid", point);
            return false;
        }


        logger.debug("validate point: {} ok", point);
        return true;
    }

    public void switch_turn() {
        if (getTurn() == BColor.RED) {
            boardModel.setTurn(BColor.WHITE);
        } else {
            boardModel.setTurn(BColor.RED);
        }

        buttonPanel.updateComponents();
    }

    /**
     * check for possible moves
     * @return
     */
    boolean isMovePossible() {
        boolean valid = false;
        BPoint barPoint= boardModel.getBar().get(getTurn());
        if (!boardModel.getBar().get(getTurn()).isEmpty()) {
            Vector<Integer> dices=dicesControl.getDices();
            for (int dice:dices) {
                if (getTurn()==BColor.WHITE) {
                    valid = validateMove(barPoint.getIndex(), barPoint.getIndex()+dice);
                }else{
                    valid = validateMove(barPoint.getIndex(), barPoint.getIndex()-dice);
                }
                if (valid) {
                    break;
                }
            }
        }else{
            valid=true;
        }
        return valid;
    }

    /**
     * starts a new game, setup map is loaded
     */
    public void start() {
        boardModel.clear();
        try {
            bmReader.readSetupMap(boardModel);
            game.message("Game Started");
            game.message_append("turn: " + getTurn());
            dicesControl.clear();
            running = true;
        } catch (Exception ex) {
            logger.error("start failed", ex);
            game.error("start failed!", ex);
        } finally {
            buttonPanel.updateComponents();
            boardPanel.repaint();
        }

    }

    /**
     * load the saved map
     */
    public void loadModel() {
        boardModel.clear();
        try {
            bmReader.readSaveMap(boardModel);
            game.message("game loaded");
            game.message_append("turn: " + getTurn());
            dicesControl.updateStack();
            running = true;
        } catch (Exception ex) {
            logger.error("loadModel failed", ex);
            game.error("loadModel failed!", ex);
        } finally {
            buttonPanel.updateComponents();
            boardPanel.repaint();
        }
    }





    /*setter and getter*******************************************************************/

    public void setButtonPanel(ButtonPanel buttonPanel) {
        this.buttonPanel = buttonPanel;
    }


    public void setStartPointSelectedIdx(int pointIdxPressed) {
        boardModel.setStartPointSelectedIdx(pointIdxPressed);
        boardPanel.repaint();
    }

    public void setPointSelectedIdx(int pointIdxSelected) {
        boardModel.setPointSelectedIdx(pointIdxSelected);
        boardPanel.repaint();
    }

    public BColor getTurn() {
        return boardModel.getTurn();
    }

    public void setPieceSelectedIdx(int pieceIdxSelected) {
        boardModel.setPieceSelectedIdx(pieceIdxSelected);
        boardPanel.repaint();
    }

    public boolean isRunning() {
        return running;
    }

    public void setButtonPanelControl(ButtonPanelControl bpControl) {

        this.bpControl = bpControl;
    }


    public void saveModel() {
        try {
            bmWriter.writeSaveMap(boardModel);
            game.message("game saved");
            running = true;
        } catch (Exception ex) {
            logger.error("saveModel failed", ex);
            game.error("saveModel failed!", ex);
        }
    }

    /**
     * call back from dices control after dices thrown
     */
    public void dicesThrown() {
        // validate for possible moves
        // if not
        // switch back
        if (!isMovePossible()) {
            game.message_error("no possible moves found, switch turn");
            switch_turn();
        }else{
            game.message("move is possible");
        }
    }

    public BoardModelIf getBoardModel() {
        return boardModel;
    }
}
