package de.ar.backgammon.dbase.dao;

import de.ar.backgammon.BException;
import de.ar.backgammon.dbase.DbCreate;
import de.ar.backgammon.dbase.entity.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DbDaoGameTest {
    DbDaoGame dbDaoGame;
    @BeforeAll
    static void setup() throws BException {
        DbCreate.getInstance().deleteDbase();
        DbCreate.getInstance().createDbase();
    }

    @BeforeEach
    void setup_each(){
        dbDaoGame = new DbDaoGame();
    }


    @Test
    void insert() throws BException {
        Game game1 = dbDaoGame.insert();
        Game game2 = dbDaoGame.insert();
        Game game3 = dbDaoGame.insert();
        assertTrue (game3.getId()!=game2.getId()
                && game3.getId()!=game1.getId()
                && game2.getId()!=game1.getId());
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