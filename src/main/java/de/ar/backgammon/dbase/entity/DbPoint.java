package de.ar.backgammon.dbase.entity;

import java.sql.Timestamp;

public class DbPoint {
    int id;
    int boardId;
    int idx;
    int color;
    int count;

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }


    private Timestamp sqltime;

    public Timestamp getSqltime() {
        return sqltime;
    }

    public void setSqltime(Timestamp sqltime) {
        this.sqltime = sqltime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
