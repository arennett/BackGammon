package de.ar.backgammon.dbase.entity;

import java.sql.Timestamp;

public class Board {
    int id;
    int gameId;
    int seqNr;
    int turn;

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    private Timestamp sqltime;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getSeqNr() {
        return seqNr;
    }

    public void setSeqNr(int seqNr) {
        this.seqNr = seqNr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getSqltime() {
        return sqltime;
    }

    public void setSqltime(Timestamp sqltime) {
        this.sqltime = sqltime;
    }
}

