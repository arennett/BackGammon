package de.ar.backgammon.dbase;

import de.ar.backgammon.*;
import de.ar.backgammon.dbase.dao.DbDaoBoard;
import de.ar.backgammon.dbase.dao.DbDaoGame;
import de.ar.backgammon.dbase.entity.Board;
import de.ar.backgammon.dbase.entity.DbGame;
import de.ar.backgammon.model.BoardModelIf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class DbasePanelControl {
    private static final Logger logger = LoggerFactory.getLogger(DbasePanelControl.class);
    private final Game game;
     private final BoardModelIf bmodel;
    private boolean recordingOn;

    DbDaoBoard dbDaoBoard;
    DbDaoGame dbDaoGame;
    DbGame dbgame;

    public DbasePanelControl(Game game,
                             BoardModelIf bmodel
                            ){
        this.game = game;
        this.bmodel = bmodel;
        dbDaoBoard=new DbDaoBoard(null);
        dbDaoGame =new DbDaoGame(null);
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
        conn = DbConnect.getInstance().getConnection();
        dbDaoGame.setConnection(conn);
        dbgame=dbDaoGame.insert();
        DbConnect.getInstance().commit();
        DbConnect.getInstance().close();
        logger.debug("writeNewGame <{}>",dbgame.getId());
    }

    public void writeBoard() throws BException {
        if (!recordingOn){
            return;
        }
        assert dbgame != null;
        Connection conn = null;
        conn = DbConnect.getInstance().getConnection();
        dbDaoBoard.setConnection(conn);
        Board board = new Board();
        board.setGameId(dbgame.getId());
        board.setTurn(bmodel.getTurn().ordinal());
        board.setBarRed(bmodel.getBarPoint(BColor.RED).getPieceCount());
        board.setBarWhite(bmodel.getBarPoint(BColor.WHITE).getPieceCount());
        board.setOffRed(bmodel.getOffPoint(BColor.RED).getPieceCount());
        board.setOffWhite(bmodel.getOffPoint(BColor.WHITE).getPieceCount());
        board.setDice1(bmodel.getDices().dice1);
        board.setDice2(bmodel.getDices().dice2);
        Board wboard=dbDaoBoard.insert(board);
        DbConnect.getInstance().commit();
        DbConnect.getInstance().close();
        logger.debug("writeBoard <{}>", wboard.getId());
    }

}
