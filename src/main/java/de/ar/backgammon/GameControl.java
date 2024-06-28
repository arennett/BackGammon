package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static de.ar.backgammon.ConstIf.MAX_PIECES_ON_POINT;

public class GameControl {
    private static final Logger logger = LoggerFactory.getLogger(BoardPanel.class);


    private boolean running = false;
    private BColor turn = BColor.WHITE;

    private Game game;
    private BoardModelIf boardModel;
    private BoardPanel boardPanel;
    private final BoardModelReaderIf bmReader;
    private final DicesControl dicesControl;
    private ButtonPanel buttonPanel;
    private ButtonPanelControl bpControl;


    public GameControl(Game game, BoardModelIf boardModel, BoardPanel boardPanel, BoardModelReaderIf bmReader, DicesControl dicesControl) {
        this.game = game;
        this.boardModel = boardModel;
        this.boardPanel = boardPanel;
        this.bmReader = bmReader;
        this.dicesControl = dicesControl;
        this.buttonPanel = buttonPanel;
    }

    public boolean moveRequest(int from, int to) {
        if(bpControl.isSetMode()){
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
        BPoint bpFrom = boardModel.getPoint(from);

        BPoint bpTo = boardModel.getPoint(to);
        return move(from, to);




    }

    private boolean move(int from, int to) {
        int spc = boardModel.getStartPointSelectedPiecesCount();
        if (!bpControl.isSetMode()) {
            if (!validateMove(from, to)) {
                return false;
            }
        }
        BPoint bpFrom = boardModel.getPoint(from);
        BPoint bpTo = boardModel.getPoint(to);
        bpFrom.setPieceCount(bpFrom.getPieceCount() - spc);
        bpTo.setPieceColor(bpFrom.getPieceColor());
        if (bpTo.getPieceCount()== 1){
            // blot
            bpTo.setPieceCount(spc);
            logger.debug("moved from {} to {} (blot)", bpFrom, bpTo);
        }else{
            // no blot
            bpTo.setPieceCount(bpTo.getPieceCount() + spc);
            logger.debug("moved from {} to {}", bpFrom, bpTo);
        }


        if (!bpControl.isSetMode()) {
            int distance = 0;
            if (turn == BColor.WHITE) {
                distance = to - from;
            } else {
                distance = from - to;
            }
            dicesControl.removePoints(distance, spc);
            if (dicesControl.getDicesState() == DicesControl.DicesState.READY) {
                switch_turn();
            }
        }
        boardPanel.repaint();
        return true;

    }

    private boolean validateMove(int from, int to) {
        boolean ret = false;
        BPoint bpFrom = boardModel.getPoint(from);
        BPoint bpTo = boardModel.getPoint(to);
        if (bpTo.getPieceCount()>1) {
                if (bpTo.getPieceColor() != turn) {
                    game.message_error("wrong color on target field");
                    return false;
                }
        }

        if (bpFrom.getPieceColor() != turn) {
            game.message_error(turn.getString() + " is expected");
            return false;
        }
        int spc = boardModel.getStartPointSelectedPiecesCount();
        if (spc < 1) {
            game.message_error("no pieces selected");
            return false;
        }
        if (spc > 4) {
            game.message_error("you can only select up to 4 pieces");
            return false;
        }

        if (bpTo.getPieceCount() + spc > MAX_PIECES_ON_POINT) {
            game.message_error("you can only put up to 5 pieces on a field");
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
        for (int i = 0; i < spc; i++) {
            logger.debug("dice point request: {} {}", i, distance);
        }
        ret = dicesControl.checkPoints(distance, spc);

        if (!ret) {
            game.message_error("not allowed, look at the dices");
            return false;
        }

        return true;
    }

    private void switch_turn() {
        if (turn == BColor.RED) {
            turn = BColor.WHITE;
        } else {
            turn = BColor.RED;
        }
        buttonPanel.updateComponents();
    }


    public void setButtonPanel(ButtonPanel buttonPanel) {
        this.buttonPanel = buttonPanel;
    }

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
