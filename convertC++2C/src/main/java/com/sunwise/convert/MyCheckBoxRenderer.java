package com.sunwise.convert;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class MyCheckBoxRenderer extends JCheckBox implements TableCellRenderer {

    public MyCheckBoxRenderer () {
        this.setBorderPainted(true);
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
        // TODO Auto-generated method stub
        return this;
    }
}
