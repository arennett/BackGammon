package de.ar.backgammon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static de.ar.backgammon.ConstIf.MAX_PIECES_ON_POINT;

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

    public boolean moveRequest(int from,int to){
        BPoint bpFrom = boardModel.getPoint(from);
        if (boardModel.getStartPointSelectedPiecesCount() > 0){
            if (bpFrom.getPieceColor()==turn){
                BPoint bpTo = boardModel.getPoint(to);
                if (bpTo.isEmpty() || bpTo.getPieceColor()==turn) {
                    return move(from,to);
                }
            }
        }
        return false;
    }

    private boolean move(int from, int to) {
        if (!validateMove(from,to)){
            return false;
        }
        BPoint bpFrom=boardModel.getPoint(from);
        BPoint bpTo=boardModel.getPoint(to);
        int spc= boardModel.getStartPointSelectedPiecesCount();
        bpFrom.setPieceCount(bpFrom.getPieceCount()-spc);
        bpTo.setPieceCount(bpTo.getPieceCount()+spc);
        bpTo.setPieceColor(bpFrom.getPieceColor());
        logger.debug("moved from {} to {}",bpFrom,bpTo);
        switch_turn();
        boardPanel.repaint();
        return true;

    }

    private boolean validateMove(int from, int to) {
        boolean ret=false;
        BPoint bpFrom=boardModel.getPoint(from);
        BPoint bpTo=boardModel.getPoint(to);

        if(bpTo.getPieceCount()>= MAX_PIECES_ON_POINT){
            return false;
        }

        if (bpFrom.getPieceColor()==BColor.WHITE){
            ret = to > from;
        }else{
            ret = to < from;
        }

        return ret;
    }

    private void switch_turn() {
        if (turn==BColor.RED){
            turn = BColor.WHITE;
        }else {
            turn = BColor.RED;
        }
        buttonPanel.updateComponents();
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
}
