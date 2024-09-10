package de.ar.backgammon.dbase.dao;

import de.ar.backgammon.BException;
import de.ar.backgammon.dbase.DbConnect;
import de.ar.backgammon.dbase.DbCreate;
import de.ar.backgammon.dbase.entity.Board;
import de.ar.backgammon.dbase.entity.DbPoint;
import de.ar.backgammon.dbase.entity.DbGame;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

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
        dbDaoGame  = new DbDaoGame(conn);
        dbDaoBoard = new DbDaoBoard(conn);
        dbDaoPoint = new DbDaoPoint(conn);
   }
    @AfterEach
    void setupAfterEach() throws SQLException {
        conn.commit();
        conn.close();
    }

    @Test
    void insert() throws BException {
        DbGame game = dbDaoGame.insert();
        Board board = new Board();
        board.setTurn(0);
        Board bx=null;
        for (int i=0;i<10;i++){
            bx =dbDaoBoard.insert(board);
        }
        Board lastBoard = dbDaoBoard.readLast();
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
}