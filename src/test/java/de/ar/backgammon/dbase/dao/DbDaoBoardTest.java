package de.ar.backgammon.dbase.dao;

import de.ar.backgammon.BException;
import de.ar.backgammon.dbase.DbCreate;
import de.ar.backgammon.dbase.entity.Board;
import de.ar.backgammon.dbase.entity.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DbDaoBoardTest {

    DbDaoGame dbDaoGame;
    DbDaoBoard dbDaoBoard;
    @BeforeAll
    static void setup() throws BException {
        DbCreate.getInstance().deleteDbase();
        DbCreate.getInstance().createDbase();
    }

    @BeforeEach
    void setup_each(){
        dbDaoGame = new DbDaoGame();
        dbDaoBoard = new DbDaoBoard();
    }


    @Test
    void insert() throws BException {
        Game game = dbDaoGame.insert();
        Board board = new Board();

    }

    @Test
    void readLast() throws BException {
        Game game = dbDaoGame.readLast();
        assertEquals(3,game.getId());
    }

    @Test
    void count() throws BException {
        assertEquals(3,dbDaoGame.count());
    }

}