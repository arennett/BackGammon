package de.ar.backgammon.dbase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Date;

public class DbBoardsPanel extends JPanel {
    JTable bdTable;
    DbBoardTableModel model;



    public DbBoardsPanel(){
        model =new DbBoardTableModel();
        initUI();

   }

    public void initUI() {
        setBorder(BorderFactory.createTitledBorder("Boards"));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500,150));
        bdTable =new JTable();
        bdTable.setDefaultRenderer(Date.class,new DateCellRenderer());
        bdTable.setModel(model);
        bdTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        TableColumnModel colModel= bdTable.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(100);
        colModel.getColumn(1).setPreferredWidth(50);
        colModel.getColumn(2).setPreferredWidth(50);
        JScrollPane jScrollPane = new JScrollPane(bdTable);
        add(jScrollPane, BorderLayout.CENTER);

    }

    public void updateTables(int gameId) {
        model.update(gameId);
        model.fireTableDataChanged();
    }

    public DbBoardTableModel getModel() {
        return model;
    }

    public void setModel(DbBoardTableModel model) {
        this.model = model;
    }

    public JTable getTable() {
        return bdTable;
    }
}
