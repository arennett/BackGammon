package de.ar.backgammon.dbase.entity;

import de.ar.backgammon.dbase.DateCellRenderer;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Date;

public class DbGamePanel extends JPanel {
    JTable gTable;
    TableModel gModel;
    public DbGamePanel(){
        gModel=new DbGameTableModel();
        initUI();
   }

    public void initUI() {
        setBorder(BorderFactory.createTitledBorder("Games"));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300,150));
        gTable=new JTable();
        gTable.setDefaultRenderer(Date.class,new DateCellRenderer());
        gTable.setModel(gModel);
        gTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        TableColumnModel colModel=gTable.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(25);
        colModel.getColumn(1).setPreferredWidth(150);
        JScrollPane jScrollPane = new JScrollPane(gTable);
        //jScrollPane.setPreferredSize(new Dimension(100,100));
        add(jScrollPane, BorderLayout.CENTER);

    }
}
