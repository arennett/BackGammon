package de.ar.backgammon.dbase.point;

import de.ar.backgammon.BColor;

import java.sql.Timestamp;

public class DbPoint {
    int id;
    int boardId;
    int idx;
    int color;
    int count;

    public static String getColumnName(int columnIndex) {
        switch (columnIndex){
            case 0: return "id";
            case 1: return "boardId";
            case 2: return "idx";
            case 3: return "color";
            case 4: return "count";
        }
        return null;
    }

    public static int getColumnCount() {
        return 5;
    }

    public static Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex){
            case 0: return Integer.class;
            case 1: return Integer.class;
            case 2: return Integer.class;
            case 3: return String.class;
            case 4: return Integer.class;
        }
        return null;
    }

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


    public Object getColumnValue(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return getId();
            case 1:
                return getBoardId();
            case 2:
                return getSqltime();
            case 3:
                return getIdx();
            case 4:
                return BColor.getBColorByIdx(getColor()).getShortString();
            case 5:
                return getCount();
            default:
                return "";
        }

    }
}
