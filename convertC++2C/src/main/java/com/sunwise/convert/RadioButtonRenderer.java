package com.sunwise.convert;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class RadioButtonRenderer implements TableCellRenderer {
    public JRadioButton btn = new JRadioButton();

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {

        if (value == null)
            return null;
        btn.setSelected((Boolean) value);
        return btn;
    }
}
