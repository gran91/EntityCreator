/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.table.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author JCHAUT
 */
public class MultiTableCellRenderer extends JTextArea implements TableCellRenderer {

    public MultiTableCellRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setAutoscrolls(true);
        Font font = new Font(this.getFont().getFontName(), Font.BOLD, 12);
        setFont(font);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText(String.valueOf(value));
        setBackground((row % 2 == 0) ? Color.WHITE : Color.LIGHT_GRAY);
        //setToolTipText(getToolTipLine(row));

        if (table.isRowSelected(row)) {
            setBackground(new Color(66, 134, 185));
        }
        setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
        if (table.getRowHeight(row) != getPreferredSize().height) {
            table.setRowHeight(row, getPreferredSize().height);
        }
        return this;
    }
}
