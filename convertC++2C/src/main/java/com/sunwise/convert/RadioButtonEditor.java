package com.sunwise.convert;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class RadioButtonEditor extends DefaultCellEditor implements ItemListener {
    public JRadioButton btn = new JRadioButton();

//    public RadioButtonEditor(JComboBox comboBox) {
//        super(comboBox);
//    }

    public RadioButtonEditor(JCheckBox checkBox) {
        super(checkBox);
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

        if (value == null)
            return null;

        btn.addItemListener(this);

        if (((Boolean) value).booleanValue())
            btn.setSelected(true);
        else
            btn.setSelected(false);

        return btn;
    }

    public Object getCellEditorValue() {
        if (btn.isSelected() == true)
            return new Boolean(true);
        else
            return new Boolean(false);
    }

    public void itemStateChanged(ItemEvent e) {
        super.fireEditingStopped();
    }

}
