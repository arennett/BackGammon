package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameControl {
    private static final Logger logger = LoggerFactory.getLogger(BoardPanel.class);

    private boolean isRunning = false;
    private BColor turn = BColor.WHITE;

    private Game game;
    private BoardModelIf boardModel;
    private BoardPanel boardPanel;
    private final BoardModelReaderIf bmReader;
    private ButtonPanel buttonPanel;


    public GameControl(Game game,BoardModelIf boardModel,BoardPanel boardPanel,BoardModelReaderIf bmReader){
        this.game = game;
        this.boardModel  = boardModel;
        this.boardPanel  = boardPanel;
        this.bmReader = bmReader;
        this.buttonPanel = buttonPanel;
    }

    public void moveRequest(int from,int to){
        BPoint bpFrom = boardModel.getPoint(from);
        if (!bpFrom.isEmpty()){
            if (bpFrom.getPieceColor()==turn){
                BPoint bpTo = boardModel.getPoint(to);
                if (bpTo.isEmpty() || bpTo.getPieceColor()==turn) {
                    move(bpFrom,bpTo);
                }
            }
        }

    }

    private void move(BPoint bpFrom, BPoint bpTo) {
        bpFrom.setPieceCount(bpFrom.getPieceCount()-1);
        bpTo.setPieceCount(bpTo.getPieceCount()+1);
        bpTo.setPieceColor(bpFrom.getPieceColor());
        logger.debug("moved from {} to {}",bpFrom,bpTo);
        switch_turn();
        boardPanel.repaint();
        buttonPanel.updateComponents();
    }

    private void switch_turn() {
        if (turn==BColor.RED){
            turn = BColor.WHITE;
        }else {
            turn = BColor.RED;
        }
    }


    public void setButtonPanel(ButtonPanel buttonPanel) {
        this.buttonPanel=buttonPanel;
    }

    public void start()  {

        boardModel.clear();
        try {
            bmReader.readSetupMap(boardModel);
            game.message("Game Started");
        } catch (Exception ex) {
            logger.error("start failed",ex);
            game.error("start failed!",ex);
        }finally {
            boardPanel.repaint();
        }

    }

    public void setPointSelectedIdx(int pointIdxPressed) {
        boardModel.setPointSelectedIdx(pointIdxPressed);
        boardPanel.repaint();
    }
}
