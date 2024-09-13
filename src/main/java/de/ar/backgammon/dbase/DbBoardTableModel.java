package de.ar.backgammon.dbase;

import de.ar.backgammon.BException;
import de.ar.backgammon.dbase.dao.DbDaoBoard;
import de.ar.backgammon.dbase.entity.DbBoard;
import de.ar.backgammon.dbase.entity.DbGame;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.sql.Connection;
import java.util.ArrayList;

public class DbBoardTableModel implements TableModel {
    private final DbDaoBoard daoBoard;
    private ArrayList<DbBoard> boardsList;


    public DbBoardTableModel(){
        DbConnect dbConnect=DbConnect.getInstance();
        Connection conn= null;
        try {
            conn = dbConnect.getConnection();
        } catch (BException e) {
            throw new RuntimeException(e);
        }
        this.boardsList=new ArrayList<>();
        this.daoBoard = new DbDaoBoard(conn);
    }

    public void update(DbGame dbgame){
        try {
            boardsList =daoBoard.readBoards(dbgame);
        } catch (BException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getRowCount() {
       return boardsList.size();
    }

    @Override
    public int getColumnCount() {
        return DbBoard.getColumnCount();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return DbBoard.getColumnName(columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return DbBoard.getColumnClass(columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return boardsList.get(rowIndex).getColumnValue(columnIndex);
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
