package de.ar.backgammon.dbase.entity;

import de.ar.backgammon.BColor;

import java.sql.Timestamp;

public class DbBoard {
    int id;
    int gameId;
    private Timestamp sqltime;
    int seqNr;
    int turn;
    int barRed;
    int barWhite;
    int OffRed;
    int OffWhite;
    int dice1;
    int dice2;

    public static String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "id";
            case 1:
                return "gameId";
            case 2:
                return "sqltime";
            case 3:
                return "seqNr";
            case 4:
                return "turn";
            case 5:
                return "barRed";
            case 6:
                return "barWhite";
            case 7:
                return "OffRed";
            case 8:
                return "OffWhite";
            case 9:
                return "dice1";
            case 10:
                return "dice2";
            default:
                return "";
        }


    }

    public static Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return Integer.class;
            case 2:
                return Timestamp.class;
            case 3:
                return Integer.class;
            case 4:
                return String.class;
            case 5:
                return Integer.class;
            case 6:
                return Integer.class;
            case 7:
                return Integer.class;
            case 8:
                return Integer.class;
            case 9:
                return Integer.class;
            case 10:
                return Integer.class;
            default:
                return null;
        }


    }


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

    public static int getColumnCount() {
        return 11;
    }

    public Object getColumnValue(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return getId();
            case 1:
                return getGameId();
            case 2:
                return getSqltime();
            case 3:
                return getSeqNr();
            case 4:
                return BColor.getBColorByIdx(getTurn()).toString();
            case 5:
                return getBarRed();
            case 6:
                return getBarWhite();
            case 7:
                return getOffRed();
            case 8:
                return getOffWhite();
            case 9:
                return getDice1();
            case 10:
                return getDice2();
            default:
                return "";
        }

    }
}

