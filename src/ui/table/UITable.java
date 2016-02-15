/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.table;

import static com.l2fprod.gui.plaf.skin.SkinUtils.setFont;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import model.entities.DefaultEntitiesModel;
import model.entities.IEntitiesModel;
import org.jdesktop.swingx.JXTable;
import org.oxbow.swingbits.table.filter.TableRowFilterSupport;
import ui.popup.UIPopup;
import ui.table.model.DefaultTableModel;

/**
 *
 * @author JCHAUT
 */
public class UITable extends JXTable implements Observer {

    protected IEntitiesModel model;
    protected DefaultTableModel modelTable;
    protected TableCellRenderer tableCellRenderer;
    protected String[] columnNames;
    protected UIPopup popup;
    private int[] sizeColumn = null;

    public UITable(IEntitiesModel m) {
        super();
        model = m;
    }

    public UITable(IEntitiesModel m, DefaultTableModel mtable) {
        super();
        model = m;
        modelTable = mtable;
    }

    public void loadTable() {
        this.setModel(modelTable);
        TableRowFilterSupport.forTable(this).searchable(true).apply();
        this.getTableHeader().setReorderingAllowed(false);
        this.setSortable(true);
        this.setColumnControlVisible(false);
        Font font = this.getFont();
        setFont(font);
        this.getTableHeader().setFont(font);
        this.getTableHeader().setForeground(new Color(66, 134, 185));
        this.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                setBackground((row % 2 == 0) ? Color.WHITE : Color.LIGHT_GRAY);
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });

        formatColumns();
        setSelectionBackground(new Color(66, 134, 185));
        setSelectionForeground(Color.LIGHT_GRAY);
        setShowHorizontalLines(true);
        setShowVerticalLines(true);
        setGridColor(new Color(66, 134, 185));
        setRowSelectionAllowed(true);
        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    public void formatColumns() {
        if (!model.getLink().isEmpty() && model.getIdLink() == -1) {
            getColumnModel().getColumn(0).setMinWidth(0);
            getColumnModel().getColumn(0).setPreferredWidth(0);
            getColumnModel().getColumn(0).setMaxWidth(0);
        }

        getColumnModel().getColumn(this.getColumnCount() - 1).setMinWidth(0);
        getColumnModel().getColumn(this.getColumnCount() - 1).setPreferredWidth(0);
        getColumnModel().getColumn(this.getColumnCount() - 1).setMaxWidth(0);

    }

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public IEntitiesModel getDataModel() {
        return model;
    }

    public void setDataModel(IEntitiesModel model) {
        this.model = model;
    }

    public DefaultTableModel getModelTable() {
        return modelTable;
    }

    public void setModelTable(DefaultTableModel modelTable) {
        this.modelTable = modelTable;
    }

    public TableCellRenderer getTableCellRenderer() {
        return tableCellRenderer;
    }

    public void setTableCellRenderer(TableCellRenderer tableCellRenderer) {
        this.tableCellRenderer = tableCellRenderer;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String[] columnNames) {
        this.columnNames = columnNames;
    }

    public int[] getSizeColumn() {
        return sizeColumn;
    }

    public void setSizeColumn(int[] sizeColumn) {
        this.sizeColumn = sizeColumn;
    }

    public void setModel(DefaultEntitiesModel model) {
        this.model = model;
    }

    public UIPopup getPopup() {
        return popup;
    }

    public void setPopup(UIPopup popup) {
        this.popup = popup;
    }
}
