package de.ar.backgammon.dbase.game;

import de.ar.backgammon.dbase.DateCellRenderer;
import de.ar.backgammon.dbase.game.DbGameTableModel;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.Date;

public class DbGamesPanel extends JPanel  {
    JTable gTable;
    JScrollPane jScrollPane;


    DbGameTableModel model;
    public DbGamesPanel(){
        model =new DbGameTableModel();
        initUI();
   }

    public void initUI() {
        setBorder(BorderFactory.createTitledBorder("Games"));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300,150));
        gTable=new JTable();
        gTable.setDefaultRenderer(Date.class,new DateCellRenderer());
        gTable.setModel(model);
        gTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        TableColumnModel colModel=gTable.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(25);
        colModel.getColumn(1).setPreferredWidth(150);
        jScrollPane = new JScrollPane(gTable);
        //jScrollPane.setPreferredSize(new Dimension(100,100));

        add(jScrollPane, BorderLayout.CENTER);

    }
    public void updateTables() {
        model.update();
        gTable.setRowSelectionInterval(gTable.getRowCount() - 1, gTable.getRowCount() - 1);
        gTable.revalidate();
        JScrollBar vertical = jScrollPane.getVerticalScrollBar();
        vertical.setValue( vertical.getMaximum());
    }


    public JTable getTable() {
        return gTable;
    }

    public DbGameTableModel getModel() {
        return model;
    }
}
