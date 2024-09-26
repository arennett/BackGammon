package de.ar.backgammon.dbase.board;

import de.ar.backgammon.BException;
import de.ar.backgammon.dbase.board.DbDaoBoard;
import de.ar.backgammon.dbase.board.DbBoard;

import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class DbBoardTableModel extends DefaultTableModel {
    private final DbDaoBoard daoBoard;
    private ArrayList<DbBoard> boardsList;


    public DbBoardTableModel(){

        this.boardsList=new ArrayList<>();
        this.daoBoard = new DbDaoBoard();
    }

    public void update(int game_id){
        try {
           boardsList =daoBoard.readBoards(game_id);
        } catch (BException e) {
           throw new RuntimeException(e);
        }
    }

    @Override
    public int getRowCount() {
        if(boardsList==null){
            return 0;
        }
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
