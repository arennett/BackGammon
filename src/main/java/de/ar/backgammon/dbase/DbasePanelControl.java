package de.ar.backgammon.dbase;

import de.ar.backgammon.*;
import de.ar.backgammon.dbase.board.DbDaoBoard;
import de.ar.backgammon.dbase.game.DbDaoGame;
import de.ar.backgammon.dbase.point.DbDaoPoint;
import de.ar.backgammon.dbase.board.DbBoard;
import de.ar.backgammon.dbase.game.DbGame;
import de.ar.backgammon.dbase.point.DbPoint;
import de.ar.backgammon.model.BoardModel;
import de.ar.backgammon.model.BoardModelIf;
import de.ar.backgammon.points.BPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class DbasePanelControl {
    private static final Logger logger = LoggerFactory.getLogger(DbasePanelControl.class);
    private final Game game;
     private final BoardModelIf bmodel;
    private boolean recordingOn;

    public void setDbasePanel(DbasePanel dbasePanel) {
        this.dbasePanel = dbasePanel;
    }

    DbasePanel dbasePanel;
    DbDaoBoard dbDaoBoard;
    DbDaoGame dbDaoGame;
    DbDaoPoint dbDaoPoint;

    DbGame dbgame;

    public DbasePanelControl(Game game,
                             BoardModelIf bmodel
                            ){
        this.game = game;
        this.bmodel = bmodel;
        dbDaoBoard=new DbDaoBoard();
        dbDaoGame =new DbDaoGame();
        dbDaoPoint=new DbDaoPoint();
    }


    public void setRecordingOn(boolean on) {
        if (on){
            logger.debug("db recording is on");
        }else{
            logger.debug("db recording is off");
        }
        this.recordingOn = on;
    }

    public void writeNewGame() throws BException{
        if (!recordingOn){
            return;
        }
        Connection conn = null;
        dbgame=dbDaoGame.insert();
        DbConnect.getInstance().commit();
        DbConnect.getInstance().close();
        dbasePanel.updateTables();
        logger.debug("writeNewGame <{}>",dbgame.getId());
    }

    public void writeBoard() throws BException {
        if (!recordingOn){
            return;
        }
        assert dbgame != null;

        DbBoard board = new DbBoard();
        board.setGameId(dbgame.getId());
        board.setTurn(bmodel.getTurn().ordinal());
        board.setBarRed(bmodel.getBarPoint(BColor.RED).getPieceCount());
        board.setBarWhite(bmodel.getBarPoint(BColor.WHITE).getPieceCount());
        board.setOffRed(bmodel.getOffPoint(BColor.RED).getPieceCount());
        board.setOffWhite(bmodel.getOffPoint(BColor.WHITE).getPieceCount());
        board.setDice1(bmodel.getDices().dice1);
        board.setDice2(bmodel.getDices().dice2);
        DbBoard wboard=dbDaoBoard.insert(board);
        writePoints(wboard.getId());
        DbConnect.getInstance().commit();
        DbConnect.getInstance().close();
        logger.debug("writeBoard <{}>", wboard.getId());
    }

    private void writePoints(int boardId) throws BException {
        for (int idx = BoardModel.POINT_IDX_FIRST_BOARD_POINT; idx < BoardModel.POINT_IDX_LAST_BOARD_POINT;idx++){
            BPoint bPoint=bmodel.getPoint(idx);
            if (!bPoint.isEmpty()) {
                DbPoint dbPoint = new DbPoint();
                dbPoint.setBoardId(boardId);
                dbPoint.setIdx(idx);
                dbPoint.setCount(bPoint.getPieceCount());
                dbPoint.setColor(bPoint.getPieceColor().ordinal());
                dbDaoPoint.insert(dbPoint);
            }
        }
    }

}
