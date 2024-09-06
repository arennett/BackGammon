package de.ar.backgammon.dbase.entity;

import java.sql.Timestamp;

public class Game {


    /*
        CREATE TABLE "game" (
        "id"	INTEGER,
        "sqltime"	TIMESTAMP NOT NULL DEFAULT (STRFTIME('%Y-%m-%d %H:%M:%f', 'NOW')),
        "next_board_seqnr" INTEGER NOT NULL DEFAULT (0),
         PRIMARY KEY("id" AUTOINCREMENT)
        );

         */
    int id;
    private Timestamp sqltime;

    public int getNextBoardSeqNr() {
        return nextBoardSeqNr;
    }

    public void setNextBoardSeqNr(int nextBoardSeqNr) {
        this.nextBoardSeqNr = nextBoardSeqNr;
    }

    private int nextBoardSeqNr;

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

