package de.ar.backgammon.dbase.entity;

import java.sql.Timestamp;

public class Board {
    int id;
    int gameId;
    int seqNr;
    int turn;
    int barRed;
    int barWhite;
    int OffRed;
    int OffWhite;

    public int getBarRed() {
        return barRed;
    }

    public void setBarRed(int barRed) {
        this.barRed = barRed;
    }

    public int getBarWhite() {
        return barWhite;
    }

    public void setBarWhite(int barWhite) {
        this.barWhite = barWhite;
    }

    public int getOffRed() {
        return OffRed;
    }

    public void setOffRed(int offRed) {
        OffRed = offRed;
    }

    public int getOffWhite() {
        return OffWhite;
    }

    public void setOffWhite(int offWhite) {
        OffWhite = offWhite;
    }

    public int getDice1() {
        return dice1;
    }

    public void setDice1(int dice1) {
        this.dice1 = dice1;
    }

    public int getDice2() {
        return dice2;
    }

    public void setDice2(int dice2) {
        this.dice2 = dice2;
    }

    int dice1;
    int dice2;




    private Timestamp sqltime;

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }



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

