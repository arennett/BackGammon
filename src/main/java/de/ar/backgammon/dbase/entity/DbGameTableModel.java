package de.ar.backgammon.dbase.entity;

import de.ar.backgammon.BException;
import de.ar.backgammon.dbase.DbConnect;
import de.ar.backgammon.dbase.dao.DbDaoGame;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.sql.Connection;
import java.util.ArrayList;

public class DbGameTableModel implements TableModel {
    private final DbDaoGame daoGame;
    private final DbGame dbGame;
    private ArrayList<DbGame> gameList;

    public DbGameTableModel(){
        DbConnect dbConnect=DbConnect.getInstance();
        Connection conn= null;
        try {
            conn = dbConnect.getConnection();
        } catch (BException e) {
            throw new RuntimeException(e);
        }
        this.daoGame = new DbDaoGame(conn);
        dbGame= new DbGame();
        update();
    }

    public void update(){
        try {
            gameList=daoGame.readAll();
        } catch (BException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getRowCount() {
       return gameList.size();
    }

    @Override
    public int getColumnCount() {
        return dbGame.getColumnCount();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return dbGame.getColumnName(columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return dbGame.getColumnClass(columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return gameList.get(rowIndex).getColumnValue(columnIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }
}
