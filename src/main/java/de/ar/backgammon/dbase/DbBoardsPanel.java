package de.ar.backgammon.dbase;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Date;

public class DbBoardsPanel extends JPanel {
    JTable bdTable;
    TableModel model;



    public DbBoardsPanel(){
        model =new DbBoardTableModel();
        initUI();
   }

    public void initUI() {
        setBorder(BorderFactory.createTitledBorder("Boards"));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300,150));
        bdTable =new JTable();
        bdTable.setDefaultRenderer(Date.class,new DateCellRenderer());
        bdTable.setModel(model);
        bdTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        TableColumnModel colModel= bdTable.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(25);
        colModel.getColumn(1).setPreferredWidth(150);
        colModel.getColumn(2).setPreferredWidth(150);
        JScrollPane jScrollPane = new JScrollPane(bdTable);
        //jScrollPane.setPreferredSize(new Dimension(100,100));
        add(jScrollPane, BorderLayout.CENTER);

    }

    public TableModel getModel() {
        return model;
    }

    public void setModel(TableModel model) {
        this.model = model;
    }
}
