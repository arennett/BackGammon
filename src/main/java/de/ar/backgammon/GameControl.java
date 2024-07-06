package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static de.ar.backgammon.ConstIf.MAX_PIECES_ON_POINT;

public class GameControl {
    private static final Logger logger = LoggerFactory.getLogger(GameControl.class);


    private boolean running = false;
    private BColor turn = BColor.WHITE;

    private final Game game;
    private final BoardModelIf boardModel;
    private final BoardPanel boardPanel;
    private final BoardModelReaderIf bmReader;
    private final DicesControl dicesControl;

    private final PipSequenceControl pipSequenceControl;
    private ButtonPanel buttonPanel;
    private ButtonPanelControl bpControl;

    ArrayList<PipSequence> psArray=new ArrayList<>();


    public GameControl(Game game, BoardModelIf boardModel, BoardPanel boardPanel, BoardModelReaderIf bmReader, DicesControl dicesControl, PipSequenceControl pointSequenceControl) {
        this.game = game;
        this.boardModel = boardModel;
        this.boardPanel = boardPanel;
        this.bmReader = bmReader;
        this.dicesControl = dicesControl;
        this.pipSequenceControl = pointSequenceControl;
    }

    /**
     * user move request by dragging one or more pieces to a target point
     * after throwing the dices
     * in set mode no dices throwing necessary
     * @param from index of from point
     * @param to  index of to point
     * @return
     */
    public boolean moveRequest(int from, int to) {
        if (bpControl.isSetMode()) {
            move(from, to);
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

        return move(from, to);

    }

    /**
     * moves one or more pieces to a target point
     * after validating the move
     * @param from
     * @param to
     * @return true if pieces where moved
     */
    private boolean move(int from, int to) {
        int spc = boardModel.getStartPointSelectedPiecesCount();
        if (!bpControl.isSetMode()) {
            if (!validateMove(from, to)) {
                return false;
            }
        }

        // psArray is set by move validation
        // if psArray.size == 2 and sequences have equal sum
        // and at least one ps has blots, the user has to choose a pointSequence
        // otherwise we take the first sequence
        boolean hasBlot = false;

        assert psArray !=null;

        for (PipSequence ps : psArray) {
            hasBlot= pipSequenceControl.psHasBlots(ps,from,to);
            if (hasBlot){
                break;
            }
        }
        PipSequence psSelect = null;

        //default
        if (!psArray.isEmpty()){
            psSelect=psArray.get(0);
        }

        // two move sequences are possible

        if (psArray.size() == 2) {
            psSelect = psArray.get(0);
            if (psArray.get(0).getSum() == psArray.get(1).getSum()) {
                if (hasBlot) {
                    logger.debug("user sequence selection requested !");
                    SeqSelectDialog sq = new SeqSelectDialog();
                    sq.setSequences(psArray);
                    sq.setVisible(true);
                    logger.debug("OPTION : {}",sq.getOption());
                    if(sq.getOption() < 0){
                        return false;
                    }else {
                        psSelect=psArray.get(sq.getOption());
                    }

                }
            }

        }

        int direction = 0;
        if (turn == BColor.WHITE) {
            direction = 1;
        } else {
            direction = -1;
        }

        if (psSelect!=null) {
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
     * a submove distance is maximum one pip long. If the main move is
     * longer than a maximum pip (6) a pip sequence devides the move
     * into sub moves
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
            bpTo.setPieceCount(spc);
            boardModel.getBar().addCount(spc, bpTo.getPieceColor());
            logger.debug("moved from {} to {} (blot)", bpFrom, bpTo);
        } else {
            // no blot
            bpTo.setPieceCount(bpTo.getPieceCount() + spc);
            logger.debug("moved from {} to {}", bpFrom, bpTo);
        }
        bpTo.setPieceColor(bpFrom.getPieceColor());
        if (!bpControl.isSetMode()) {
            // try to remove points from stack
            dicesControl.removePipsFromStack(Math.abs(to - from), spc);
        }
    }

    private boolean validateMove(int from, int to) {
        boolean ret = false;
        BPoint bpFrom = boardModel.getPoint(from);
        BPoint bpTo = boardModel.getPoint(to);

        int spc = boardModel.getStartPointSelectedPiecesCount();

        if (spc < 1) {
            game.message_error("no pieces selected");
            return false;
        }
        if (spc > 4) {
            game.message_error("you can only select up to 4 pieces");
            return false;
        }

        ret = isValidPoint(bpTo,spc);

        if(!ret){
            // unvalid point
            return false;
        }

        if (turn == BColor.WHITE) {
            ret = to > from;
        } else {
            ret = to < from;
        }

        if (!ret) {
            game.message_error("wrong direction");
            return false;
        }
        //check dices
        int distance = 0;
        if (turn == BColor.WHITE) {
            distance = to - from;
        } else {
            distance = from - to;
        }

        assert distance > 0;


        psArray = pipSequenceControl.getValidSequences(from, to,  spc);
        if (psArray.isEmpty()) {
            // maybe only one pip on stack
            ret = dicesControl.checkIfMoveIsOnStack(distance, spc);
            if (!ret) {
                game.message_error("not allowed, look at the dices");
                return false;
            }
        }


        return true;
    }

    /**
     * basic validation, used for sequence and target points
     * @param point
     * @param spc
     * @return
     */
    public boolean isValidPoint(BPoint point, int spc) {
        if (point.getPieceCount() > 1) {
            if (point.getPieceColor() != getTurn()) {
                game.message_error("wrong point color");
                logger.debug("validate point: {} ,wrong point color",point);
                return false;
            }
        }
        if (point.getPieceCount() > 4) {
            if (point.getPieceCount() + spc > MAX_PIECES_ON_POINT) {
                game.message_error("you can only put up to 5 pieces on a field");
                logger.debug("validate point: {} ,max 5 pieces",point);
                return false;
            }
        }
        logger.debug("validate point: {} ok", point);
        return true;
    }

    public void switch_turn() {
        if (turn == BColor.RED) {
            turn = BColor.WHITE;
        } else {
            turn = BColor.RED;
        }
        buttonPanel.updateComponents();
    }


    /**
     * starts a new game, setup map is loaded
     */
    public void start() {

        boardModel.clear();
        try {
            bmReader.readSetupMap(boardModel);
            game.message("Game Started");
            game.message_append("turn: " + turn);
            dicesControl.clear();
            running = true;
        } catch (Exception ex) {
            logger.error("start failed", ex);
            game.error("start failed!", ex);
        } finally {
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
        return turn;
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
}
