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
    private final DicesControl dicesControl;
    private ButtonPanel buttonPanel;


    public GameControl(Game game,BoardModelIf boardModel,BoardPanel boardPanel,BoardModelReaderIf bmReader,DicesControl dicesControl){
        this.game = game;
        this.boardModel  = boardModel;
        this.boardPanel  = boardPanel;
        this.bmReader = bmReader;
        this.dicesControl = dicesControl;
        this.buttonPanel = buttonPanel;
    }

    public boolean moveRequest(int from,int to){
        if (dicesControl.getDicesState()!= DicesControl.DicesState.THROWN){
            game.message_error("any dices thrown");
            return false;
        }
        BPoint bpFrom = boardModel.getPoint(from);
        {
                BPoint bpTo = boardModel.getPoint(to);
                if (bpTo.isEmpty() || bpTo.getPieceColor()==turn) {
                    return move(from, to);
                }

        }
        return false;
    }

    private boolean move(int from, int to) {
        int spc= boardModel.getStartPointSelectedPiecesCount();
        if (!validateMove(from,to)){
            return false;
        }
        BPoint bpFrom=boardModel.getPoint(from);
        BPoint bpTo=boardModel.getPoint(to);

        bpFrom.setPieceCount(bpFrom.getPieceCount()-spc);
        bpTo.setPieceCount(bpTo.getPieceCount()+spc);
        bpTo.setPieceColor(bpFrom.getPieceColor());
        logger.debug("moved from {} to {}",bpFrom,bpTo);
        int distance = 0;
        if(turn==BColor.WHITE){
            distance=to-from;
        }else{
            distance=from-to;
        }
        dicesControl.removePoints(distance,spc);
        if(dicesControl.getDicesState()== DicesControl.DicesState.READY){
            switch_turn();
        }

        boardPanel.repaint();
        return true;

    }

    private boolean validateMove(int from, int to) {
        boolean ret=false;
        BPoint bpFrom=boardModel.getPoint(from);
        BPoint bpTo=boardModel.getPoint(to);
        if (bpFrom.getPieceColor()!=turn){
            game.message_error(turn.getString()+" is expected");
            return false;
        }
        int spc=boardModel.getStartPointSelectedPiecesCount();
        if (spc < 1){
            game.message_error("no pieces selected");
            return false;
        }
        if (spc > 4){
            game.message_error("you can only select up to 4 pieces");
            return false;
        }

        if(bpTo.getPieceCount()+spc > MAX_PIECES_ON_POINT){
            game.message_error("you can only put up to 5 pieces on a field");
            return false;
        }

        if (turn==BColor.WHITE){
            ret = to > from;
        }else{
            ret = to < from;
        }

        if (!ret) {
            game.message_error("wrong direction");
            return false;
        }
        //check dices
        int distance = 0;
        if(turn==BColor.WHITE){
            distance=to-from;
        }else{
            distance=from-to;
        }
        for (int i=0; i<spc;i++){
            logger.debug("dice point request: {} {}",i,distance);
        }
        ret=dicesControl.checkPoints(distance,spc);

        if (!ret) {
            game.message_error("not allowed, look at the dices");
            return false;
        }

        return true;
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
            dicesControl.clear();
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
