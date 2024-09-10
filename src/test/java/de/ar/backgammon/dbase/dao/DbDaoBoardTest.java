package de.ar.backgammon.dbase.dao;

import de.ar.backgammon.BException;
import de.ar.backgammon.dbase.DbConnect;
import de.ar.backgammon.dbase.DbCreate;
import de.ar.backgammon.dbase.entity.Board;
import de.ar.backgammon.dbase.entity.DbGame;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DbDaoBoardTest {

    DbDaoGame dbDaoGame;
    DbDaoBoard dbDaoBoard;
    Connection conn;
    @BeforeAll
    static void setup() throws BException {
        DbCreate.getInstance().deleteDbase();
        DbCreate.getInstance().createDbase();
    }

    @BeforeEach
    void setupBeforeEach() throws BException {
        conn=DbConnect.getInstance().getConnection();
        dbDaoGame = new DbDaoGame(conn);
        dbDaoBoard = new DbDaoBoard(conn);
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
        board.setBarWhite(1);
        board.setBarRed(2);
        board.setOffRed(3);
        board.setOffWhite(4);
        board.setDice1(1);
        board.setDice2(2);

        Board bx=null;
        for (int i=0;i<1000;i++){
            bx =dbDaoBoard.insert(board);
        }

        assertEquals(999, bx.getSeqNr());
   }

    @Test
    void readLast() throws BException {
        Board board = dbDaoBoard.readLast();
        assertEquals(1000,board.getId());
    }

    @Test
    void count() throws BException {
        assertEquals(1000,dbDaoBoard.count());
    }

}