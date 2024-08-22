package de.ar.backgammon;

import de.ar.backgammon.compute.ComputerIf;
import de.ar.backgammon.dices.DicesControl;
import de.ar.backgammon.dices.DicesStack;
import de.ar.backgammon.dices.PipSequence;
import de.ar.backgammon.dices.SequenceStack;
import de.ar.backgammon.model.BoardModel;
import de.ar.backgammon.model.BoardModelIf;
import de.ar.backgammon.model.BoardModelReaderIf;
import de.ar.backgammon.model.BoardModelWriterIf;
import de.ar.backgammon.moves.Move;
import de.ar.backgammon.moves.MoveListGenerator;
import de.ar.backgammon.moves.MoveListGeneratorIf;
import de.ar.backgammon.moves.MoveSet;
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
    private final ComputerIf comp;

    public GameControl(Game game, BoardModelIf boardModel,
                       BoardPanel boardPanel,
                       BoardModelReaderIf bmReader,
                       BoardModelWriterIf bmWriter,
                       DicesControl dicesControl,
                       ComputerIf comp) {
        this.game = game;
        this.boardModel = boardModel;
        this.boardPanel = boardPanel;
        this.bmReader = bmReader;
        this.bmWriter = bmWriter;
        this.dicesControl = dicesControl;
        this.moveValidator = new MoveValidator(boardModel);
        this.comp = comp;
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
        if (bpControl.isComp(getTurn())){
            game.message_error(getTurn().toString()+" is played by computer");
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
        boolean moved=boardModel.move(move,spc,bpControl.isSetMode());

        if(!moved){
            game.message_error("piece not moved : "+move);
            return false;
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
        buttonPanel.updateComponents();

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
        boardModel.subMove(move,spc,bpControl.isSetMode());
    }


    /**
     *
     * @param move validation
     * @return
     */
    public boolean validateMove(Move move) {
        boolean ret=moveValidator.isValid(move,boardModel.getStartPointSelectedPiecesCount());
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


        if(bpControl.isComp(getTurn())){
            dicesControl.getDicesStack().throwDices();
            if (!isMovePossible()){
                game.message_error(" no further moves possible, switch turn");
                switch_turn();
                return;
            }
            MoveSet moveSet=comp.compute();
            if(moveSet != null){
                if (moveSet.move(boardModel)){
                    boardPanel.repaint();
                    switch_turn();
                };
            }
        }

    }

    /**
     * check for possible moves
     * @return
     */
    boolean isMovePossible() {
        boolean valid = false;
        MoveListGeneratorIf movesGenerator=new MoveListGenerator(boardModel);
        ArrayList<Move> moves=movesGenerator.getValidMoves();
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
            if(bpControl.isComp(getTurn())){
                dicesControl.getDicesStack().throwDices();
                MoveSet moveSet=comp.compute();
                if(moveSet != null){
                    if (moveSet.move(boardModel)){
                        switch_turn();
                    };
                }
            }
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
