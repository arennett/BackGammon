package de.ar.backgammon.dbase.dao;

import de.ar.backgammon.BException;
import de.ar.backgammon.dbase.DbConnect;
import de.ar.backgammon.dbase.DbCreate;
import de.ar.backgammon.dbase.board.DbDaoBoard;
import de.ar.backgammon.dbase.game.DbDaoGame;
import de.ar.backgammon.dbase.board.DbBoard;
import de.ar.backgammon.dbase.game.DbGame;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

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
        dbDaoGame = new DbDaoGame();
        dbDaoBoard = new DbDaoBoard();
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
        board.setBarWhite(1);
        board.setBarRed(2);
        board.setOffRed(3);
        board.setOffWhite(4);
        board.setDice1(1);
        board.setDice2(2);

        DbBoard bx=null;
        for (int i=0;i<1000;i++){
            bx =dbDaoBoard.insert(board);
        }

        assertEquals(999, bx.getSeqNr());
   }

    @Test
    void readLast() throws BException {
        DbBoard board = dbDaoBoard.readLast();
        assertEquals(1000,board.getId());
    }

    @Test
    void count() throws BException {
        assertEquals(1000,dbDaoBoard.count());
    }

    @Test
    void readBoards() throws BException {
        insert();
        DbGame game = dbDaoGame.readLast();

        ArrayList<DbBoard> boards=dbDaoBoard.readBoards(game.getId());
        int i=0;
        for (DbBoard board:boards){
             i++;
        }
        assertTrue(i >0);
    }
}