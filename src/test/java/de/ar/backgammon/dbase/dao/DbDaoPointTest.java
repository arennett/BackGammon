package de.ar.backgammon.dbase.dao;

import de.ar.backgammon.BException;
import de.ar.backgammon.dbase.DbConnect;
import de.ar.backgammon.dbase.DbCreate;
import de.ar.backgammon.dbase.board.DbDaoBoard;
import de.ar.backgammon.dbase.game.DbDaoGame;
import de.ar.backgammon.dbase.point.DbDaoPoint;
import de.ar.backgammon.dbase.board.DbBoard;
import de.ar.backgammon.dbase.game.DbGame;
import de.ar.backgammon.dbase.point.DbPoint;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DbDaoPointTest {

    DbDaoGame dbDaoGame;
    DbDaoBoard dbDaoBoard;
    DbDaoPoint dbDaoPoint;
    Connection conn;
    @BeforeAll
    static void setup() throws BException {
        DbCreate.getInstance().deleteDbase();
        DbCreate.getInstance().createDbase();
    }

    @BeforeEach
    void setupBeforeEach() throws BException {
        conn= DbConnect.getInstance().getConnection();
        dbDaoGame  = new DbDaoGame();
        dbDaoBoard = new DbDaoBoard();
        dbDaoPoint = new DbDaoPoint();
   }
    @AfterEach
    void setupAfterEach() throws SQLException {
        conn.commit();
        conn.close();
    }

    @Test
    void insert() throws BException {
        DbGame game = dbDaoGame.insert();
        DbBoard board = new DbBoard();
        board.setTurn(0);
        DbBoard bx=null;
        for (int i=0;i<10;i++){
            bx =dbDaoBoard.insert(board);
        }
        DbBoard lastBoard = dbDaoBoard.readLast();
        for (int idx = 1 ; idx<=24;idx++) {
            DbPoint point = new DbPoint();
            point.setBoardId(lastBoard.getId());
            point.setIdx(idx);
            point.setColor(idx % 2);
            point.setCount(0);
            dbDaoPoint.insert(point);
        }
    }

    @Test
    void readLast() throws BException {
        DbPoint point = dbDaoPoint.readLast();
        assertEquals(24,point.getIdx());
    }

    @Test
    void count() throws BException {
        assertEquals(24,dbDaoPoint.count());
    }

    @Test
    void readPoints() throws BException {
        insert();
        DbBoard board = dbDaoBoard.readLast();

        ArrayList<DbPoint> points=dbDaoPoint.readPoints(board.getId());
        int i=0;
        for (DbPoint point:points){
            i++;
        }
        assertTrue(i >0);
    }



}