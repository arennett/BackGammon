package de.ar.backgammon.dbase;

import de.ar.backgammon.BException;
import de.ar.backgammon.dbase.DbConnect;
import de.ar.backgammon.dbase.dao.DbDaoGame;
import de.ar.backgammon.dbase.entity.DbGame;

import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.Connection;
import java.util.ArrayList;

public class DbGameTableModel extends DefaultTableModel {
    private final DbDaoGame daoGame;

    private ArrayList<DbGame> gameList;

    public DbGameTableModel(){
        this.daoGame = new DbDaoGame();
        gameList=new ArrayList<>();
        update();
    }

    public void update(){
        try {
            gameList=daoGame.readAll();
            fireTableDataChanged();
        } catch (BException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getRowCount() {
        if (gameList==null){
            return 0;
        }
       return gameList.size();
    }

    @Override
    public int getColumnCount() {
        return DbGame.getColumnCount();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return DbGame.getColumnName(columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return DbGame.getColumnClass(columnIndex);
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
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }

    public int getGameId(int rowId) {
        if (getRowCount()>0){
            return gameList.get(rowId).getId();
        }else{
            return -1;
        }
    }
}
