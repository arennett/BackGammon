package de.ar.backgammon.dbase.entity;

import java.sql.Timestamp;

public class DbGame {


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

    public int getColumnCount(){
        return 3;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex){
            case 0:
                return "ID";
            case 1:
                return "Sqltime";
            case 2:
                return "NextSeqNr";
        }
        return"";
    }

    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex){
            case 0:
                return Integer.class;
            case 1:
                return Timestamp.class;
            case 2:
                return Integer.class;
        }
        return null;
    }

    public Object getColumnValue(int columnIndex) {
        switch (columnIndex){
            case 0:
                return getId();
            case 1:
                return getSqltime();
            case 2:
                return getNextBoardSeqNr();
        }
        return null;
    }
}

