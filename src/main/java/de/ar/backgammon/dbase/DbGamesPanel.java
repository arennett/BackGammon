package de.ar.backgammon.dbase;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Date;

public class DbGamesPanel extends JPanel  {
    JTable gTable;



    TableModel model;
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
        JScrollPane jScrollPane = new JScrollPane(gTable);
        //jScrollPane.setPreferredSize(new Dimension(100,100));
        add(jScrollPane, BorderLayout.CENTER);

    }
    public TableModel getModel() {
        return model;
    }

    public void setModel(TableModel model) {
        this.model = model;
    }

    public JTable getTable() {
        return gTable;
    }
}
