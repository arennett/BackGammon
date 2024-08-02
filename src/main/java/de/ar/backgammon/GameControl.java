package de.ar.backgammon;

import de.ar.backgammon.dices.DicesControl;
import de.ar.backgammon.dices.DicesStack;
import de.ar.backgammon.dices.PipSequence;
import de.ar.backgammon.dices.SequenceStack;
import de.ar.backgammon.model.BoardModel;
import de.ar.backgammon.model.BoardModelIf;
import de.ar.backgammon.model.BoardModelReaderIf;
import de.ar.backgammon.model.BoardModelWriterIf;
import de.ar.backgammon.moves.Move;
import de.ar.backgammon.moves.MovesGenerator;
import de.ar.backgammon.moves.MovesGeneratorIf;
import de.ar.backgammon.points.BPoint;
import de.ar.backgammon.validation.MoveValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static de.ar.backgammon.ConstIf.MAX_PIP;

public class GameControl {
    private static final Logger logger = LoggerFactory.getLogger(GameControl.class);


    private boolean running = false;


    private final Game game;
    private final BoardModelIf boardModel;
    private final BoardPanel boardPanel;
    private final BoardModelReaderIf bmReader;
    private final BoardModelWriterIf bmWriter;
    private final DicesControl dicesControl;

    private ButtonPanel buttonPanel;
    private ButtonPanelControl bpControl;

    private MoveValidator moveValidator;

    public GameControl(Game game, BoardModelIf boardModel,
                       BoardPanel boardPanel,
                       BoardModelReaderIf bmReader,
                       BoardModelWriterIf bmWriter,
                       DicesControl dicesControl) {
        this.game = game;
        this.boardModel = boardModel;
        this.boardPanel = boardPanel;
        this.bmReader = bmReader;
        this.bmWriter = bmWriter;
        this.dicesControl = dicesControl;
        this.moveValidator = new MoveValidator(boardModel, dicesControl.getDicesStack());
    }

    /**
     * user move request by dragging one or more pieces to a target point
     * after throwing the dices
     * in set mode no dices throwing necessary
     *
     * @param move requested move
     * @return true if moved
     */
    public boolean moveRequest(Move move) {
        if (move.to== BoardModel.POINT_IDX_OFF) {
            // if off move delegate to the players offpoint
            BPoint bfrom= boardModel.getPoint(move.from);
            if (bfrom.getPieceColor()==BColor.RED) {
                move.to=BoardModel.POINT_IDX_OFF_RED;
            }else{
                move.to=BoardModel.POINT_IDX_OFF_WHITE;
            }
        }
        logger.debug("moveRequest: {} ",move);
        if (bpControl.isSetMode()) {
            move(move);
            buttonPanel.updateComponents();
            return true;
        }
        if (!running) {
            game.message_error("please start game!");
            return false;
        }
        if (dicesControl.getDicesStack().getState() != DicesStack.State.UPDATED) {
            game.message_error("throw the dices!");
            return false;
        }

        boolean ret= move(move);
        buttonPanel.updateComponents();
        return ret;

    }



    /**
     * moves one or more pieces to a target point
     * after validating the move
     *
     * @param move
     * @return true if pieces where moved
     */
    private boolean move(Move move) {
        int spc = boardModel.getStartPointSelectedPiecesCount();
        if (move.isBarMove()) {
            spc = 1;
        }

        if (!bpControl.isSetMode()) {
            if (!validateMove(move)) {

                return false;
            }
        } else {
            //setmode
            sub_move(move, spc);
            return true;
        }

        // get valid sequences from sequenceControl depending on the move range
        // if psArray.size == 2 and sequences have equal sum
        // and at least one ps has blots, the user has to choose a pointSequence
        // otherwise we take the first sequence

        ArrayList<PipSequence> psArray
                = dicesControl.getDicesStack().getSequenceStack().getValidSequences(move, spc, getTurn());
        boolean hasBlot = false;


        for (PipSequence ps : psArray) {
            hasBlot = dicesControl.getDicesStack().getSequenceStack().psHasBlots(ps, move,spc,getTurn());
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
                    SequenceStack.BlotArray ba0 = dicesControl.getDicesStack().getSequenceStack().getBlotArray(
                                                  psArray.get(0), move,spc,getTurn());
                    if (!ba0.isEmpty()) {
                        logger.debug("blots detected on: {}", "" + ba0);
                    }

                    SequenceStack.BlotArray ba1 = dicesControl.getDicesStack().getSequenceStack().getBlotArray(
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
                    sub_move(new Move(pos, subto), spc);
                    pos = subto;
                }else{
                    break;
                }

            }
        } else {
            // no sequence
            assert move.getRange(getTurn()) <= MAX_PIP;
            sub_move(move, spc);
        }

        game.message("piece moved : "+move);
        if (dicesControl.getDicesStack().getState() == DicesStack.State.EMPTY) {
            switch_turn();
        }else{
            if (!isMovePossible()){
                game.message_error(" no further moves possible, please switch turn");
            }else{

                game.message_append("further moves possible...");
            }
        }
        boardPanel.repaint();

        return true;

    }

    /**
     * a submove range is maximum one pip long. If the main move is
     * longer than a maximum pip (6) a pip sequence devides the move
     * into sub moves
     *
     * @param move
     * @param spc count of pieces moved
     */
    public void sub_move(Move move, int spc) {


        logger.debug("sub move {}",move);

        //if not is setmode delegate point 0/25 (bar points) to offpoints
        if (!bpControl.isSetMode()) {
            assert move.getRange(getTurn()) <= MAX_PIP;
            if (move.to == BoardModel.POINT_IDX_BAR_WHITE) {
                move.to = BoardModel.POINT_IDX_OFF_RED;
            } else if (move.to == BoardModel.POINT_IDX_BAR_RED) {
                move.to = BoardModel.POINT_IDX_OFF_WHITE;
            }
        }


        BPoint bpFrom = boardModel.getPoint(move.from);
        BPoint bpTo = boardModel.getPoint(move.to);

        BColor colorFrom =bpFrom.getPieceColor();
        BColor colorTo =bpTo.getPieceColor();
        bpFrom.setPieceCount(bpFrom.getPieceCount() - spc,colorFrom);

        if (bpTo.getPieceCount() == 1 && colorTo != colorFrom) {
            // blot
            boardModel.getBarPoint(colorTo).addCount(1);
            logger.debug("moved {} (blot)", move);
            bpTo.setPieceCount(spc,colorFrom);
        } else {
            // no blot
            bpTo.setPieceCount(bpTo.getPieceCount() + spc,colorFrom);
            logger.debug("moved {}", move);
        }

        if (!bpControl.isSetMode()) {
            // try to remove points from stack
            dicesControl.removePipsFromStack(move.getRange(getTurn()), spc);
        }
    }


    /**
     *
     * @param move validation
     * @return
     */
    public boolean validateMove(Move move) {
        boolean ret=moveValidator.isValid(move,getTurn(),boardModel.getStartPointSelectedPiecesCount());
        game.message_error(moveValidator.err.userMessage);
        return ret;
    }




    public void switch_turn() {
        if (getTurn() == BColor.RED) {
            boardModel.setTurn(BColor.WHITE);
        } else {
            boardModel.setTurn(BColor.RED);
        }
        game.message("Turn is "+getTurn());
        dicesControl.getDicesStack().getDices().clear();
        dicesControl.clear();
        logger.debug("################## TURN IS {} ############################",getTurn());
        buttonPanel.updateComponents();
        if (isMovePossible()){
            game.message_error(" no further moves possible, please switch turn");
        }

    }

    /**
     * check for possible moves
     * @return
     * TODO check for the whole board */
    boolean isMovePossible() {
        boolean valid = false;
        MovesGeneratorIf movesGenerator=new MovesGenerator(boardModel,
                new MoveValidator(boardModel,dicesControl.getDicesStack()));
        ArrayList<Move> moves=movesGenerator.getValidMoves(getTurn());
        valid = !moves.isEmpty();
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
            dicesControl.updateComponents();
            dicesThrown();
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
            //dicesControl.saveDices();
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
            game.message_error("no possible moves found for "+getTurn()+ ", please switch turn");
        }else{
            game.message("move is possible");
        }
    }

    public BoardModelIf getBoardModel() {
        return boardModel;
    }

    public boolean allPiecesAtHome() {
        return boardModel.isAllPiecesAtHome(getTurn());
    }
}
