package de.ar.backgammon.dbase;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCellRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value instanceof Date) {

            // Use SimpleDateFormat class to get a formatted String from Date object.
            String strDate = new SimpleDateFormat(" MM/dd/yyyy HH:mm:ss.SSS").format((Date) value);

            // Sorting algorithm will work with model value. So you dont need to worry
            // about the renderer's display value.
            this.setText(strDate);
        }

        return this;
    }
}