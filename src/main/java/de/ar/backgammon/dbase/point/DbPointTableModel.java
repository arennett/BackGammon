package de.ar.backgammon.dbase.point;

import de.ar.backgammon.BException;
import de.ar.backgammon.dbase.board.DbBoard;
import de.ar.backgammon.dbase.board.DbDaoBoard;

import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class DbPointTableModel extends DefaultTableModel {
    private final DbDaoPoint daoPoint;
    private ArrayList<DbPoint> pointsList;


    public DbPointTableModel(){
        this.pointsList=new ArrayList<>();
        this.daoPoint = new DbDaoPoint();
    }

    public void update(int game_id){
        try {
            pointsList =daoPoint.readPoints(game_id);
        } catch (BException e) {
           throw new RuntimeException(e);
        }
    }

    @Override
    public int getRowCount() {
        if(pointsList==null){
            return 0;
        }
       return pointsList.size();
    }

    @Override
    public int getColumnCount() {
        return DbPoint.getColumnCount();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return DbPoint.getColumnName(columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return DbPoint.getColumnClass(columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return pointsList.get(rowIndex).getColumnValue(columnIndex);
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
