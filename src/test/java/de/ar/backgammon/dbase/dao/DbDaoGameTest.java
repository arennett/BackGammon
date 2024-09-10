package de.ar.backgammon.dbase.dao;

import de.ar.backgammon.BException;
import de.ar.backgammon.dbase.DbConnect;
import de.ar.backgammon.dbase.DbCreate;
import de.ar.backgammon.dbase.entity.DbGame;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DbDaoGameTest {
    DbDaoGame dbDaoGame;
    Connection conn;
    @BeforeAll
    static void setup() throws BException {
        DbCreate.getInstance().deleteDbase();
        DbCreate.getInstance().createDbase();
    }

    @BeforeEach
    void setupBeforeEach() throws BException {
        conn= DbConnect.getInstance().getConnection();
        dbDaoGame = new DbDaoGame(conn);

    }
    @AfterEach
    void setupAfterEach() throws SQLException {
        conn.commit();
        conn.close();
    }

    @Test
    void insert() throws BException {
        DbGame game1 = dbDaoGame.insert();
        DbGame game2 = dbDaoGame.insert();
        DbGame game3 = dbDaoGame.insert();
        assertTrue (game3.getId()!=game2.getId()
                && game3.getId()!=game1.getId()
                && game2.getId()!=game1.getId());
    }

    @Test
    void readLast() throws BException {
        DbGame game = dbDaoGame.readLast();
        assertEquals(3,game.getId());
    }

    @Test
    void count() throws BException {
        assertEquals(3,dbDaoGame.count());
    }
}