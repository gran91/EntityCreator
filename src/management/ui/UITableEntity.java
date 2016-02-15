/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package management.ui;

import java.awt.BorderLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import main.Ressource;
import model.entity.XMLEntityModel;
import org.oxbow.swingbits.table.filter.TableRowFilterSupport;
import ui.panel.model.PanelButton;
import ui.tools.AutoComboBox;

public class UITableEntity extends JPanel implements ActionListener {

    private Object[][] allLng;
    private String type[] = {"Alpha", "Alpha sans num", "Alpha sans spé", "Alpha sans num et spé", "Numérique", "IP", "Fichier", "Répertoire"};
    private String[] entities;
    private HashMap<Integer, String> listNameField = new HashMap<Integer, String>();
    private PanelButton pbutton = new PanelButton(PanelButton.BT_ADD_DEL);
    private JTable table;
    private MyTableModel model;
    private Object[] blankTab = new Object[]{""};

    public UITableEntity() {
        super(new BorderLayout());
        pbutton.bAdd.addActionListener(this);
        pbutton.bDel.addActionListener(this);
        add(pbutton, BorderLayout.NORTH);
        model = new MyTableModel();
        table = new JTable(model);
        TableRowFilterSupport.forTable(table).searchable(true).apply();
        table.setPreferredScrollableViewportSize(new Dimension(1000, 70));
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        initColumnSizes(table);
        table.setRowHeight(20);
        listNameField.put(0, model.initValues[0].toString());
        allLng = i18n.Language.getAllLabel();
        java.util.Arrays.sort(allLng, new tools.AlphaComparator(0));
        listEntities();
        setComboboxColumn(table.getColumnModel().getColumn(1), tools.Tools.extractColumn(allLng, 0));
        setComboboxColumn(table.getColumnModel().getColumn(2), type);
        setComboboxColumn(table.getColumnModel().getColumn(9), entities);
        setComboboxColumn(table.getColumnModel().getColumn(10), blankTab);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void listEntities() {
        String[] temp = new File(Ressource.XML_MODEL).list();
        if (temp == null) {
            entities = new String[1];
            entities[0] = "";
        } else {
            entities = new String[temp.length + 1];
            entities[0] = "";
            for (int i = 1; i <= temp.length; i++) {
                entities[i] = temp[i - 1].substring(0, temp[i - 1].length() - 4);
            }
        }

    }

    public Object[][] transformData() {
        Object[][] o = new Object[table.getRowCount()][table.getColumnCount()];
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                if (j == 1) {
                    o[i][j] = i18n.Language.getIdLabel(model.getData()[i][j].toString());
                } else if (j == 2) {
                    int id = -1;
                    ArrayList listType = new ArrayList(Arrays.asList(type));
                    if ((id = listType.indexOf(model.getData()[i][j])) != -1) {
                        o[i][j] = id;
                    } else {
                        o[i][j] = 0;
                    }
                } else if (j >= 4 && j <= 8 || j == 11) {
                    o[i][j] = tools.Tools.convertBooleanToInt((Boolean) model.getData()[i][j]);
                } else if (j == 10 && !model.getData()[i][j].toString().isEmpty()) {
                    XMLEntityModel xmlMod = new XMLEntityModel(model.getData()[i][j - 1].toString());
                    int id = -1;
                    if ((id = xmlMod.getLstElmtEntity().indexOf(model.getData()[i][j])) != -1) {
                        o[i][j] = id;
                    } else {
                        o[i][j] = 0;
                    }
                } else {
                    o[i][j] = model.getData()[i][j];
                }
            }
        }
        return o;
    }

    private void initColumnSizes(JTable table) {
        MyTableModel model = (MyTableModel) table.getModel();
        TableColumn column = null;
        Component comp = null;
        int headerWidth = 0;
        int cellWidth = 0;
        Object[] longValues = model.initValues;
        TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
        for (int i = 0; i < 11; i++) {
            column = table.getColumnModel().getColumn(i);
            comp = headerRenderer.getTableCellRendererComponent(null, column.getHeaderValue(), false, false, 0, 0);
            headerWidth = comp.getPreferredSize().width;
            comp = table.getDefaultRenderer(model.getColumnClass(i)).getTableCellRendererComponent(table, longValues[i], false, false, 0, i);
            cellWidth = comp.getPreferredSize().width;
            System.out.println("Initializing width of column " + i + ". " + "headerWidth = " + headerWidth + "; cellWidth = " + cellWidth);
            column.setPreferredWidth(Math.max(headerWidth, cellWidth));
        }
    }

    private void setComboboxColumn(TableColumn comboboxColumn, Object[] data) {
        AutoComboBox c = new AutoComboBox(new ArrayList(Arrays.asList(data)));
        c.setDataList(new ArrayList(Arrays.asList(data)));
        c.setMaximumRowCount(3);
        comboboxColumn.setCellEditor(new MyCellEditor(c));
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");
        comboboxColumn.setCellRenderer(renderer);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == pbutton.bAdd) {
            Object[] o1 = new Object[model.initValues.length];
            System.arraycopy(model.initValues, 0, o1, 0, model.initValues.length);
            model.addRow(o1);
        }
        if (o == pbutton.bDel) {
            int[] n = table.getSelectedRows();
            model.removeRow(table.getSelectedRow());
        }
    }

    public Object[][] getAllLng() {
        return allLng;
    }

    public void setAllLng(String[][] allLng) {
        this.allLng = allLng;
    }

    public MyTableModel getModel() {
        return model;
    }

    public void setModel(MyTableModel model) {
        this.model = model;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public String[] getType() {
        return type;
    }

    public void setType(String[] type) {
        this.type = type;
    }

    public String[] getEntities() {
        return entities;
    }

    public void setEntities(String[] entities) {
        this.entities = entities;
    }

    class MyCellEditor extends DefaultCellEditor {

        Map<String, ArrayList<String>> choicesMap;

        public MyCellEditor(JComboBox combo) {
            super(combo);
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            JComboBox combo = (JComboBox) super.getTableCellEditorComponent(table, value, isSelected, row, column);
            DefaultComboBoxModel model = null;
            if (column == 9) {
                listEntities();
                model = new DefaultComboBoxModel(entities);
            } else if (column == 10) {
                XMLEntityModel xmlMod = new XMLEntityModel(table.getValueAt(row, column - 1).toString());
                Object[] o = tools.Tools.convertArrayListToArray(xmlMod.getLstElmtEntity(), null);
                if (o == null) {
                    o = blankTab;
                }
                model = new DefaultComboBoxModel(o);
            } else if (column == 1) {
                model = new DefaultComboBoxModel(tools.Tools.extractColumn(allLng, 0));
            } else {
                return combo;
            }
            combo.setModel(model);
            return combo;
        }
    }

    class MyTableModel extends AbstractTableModel {

        private String[] columnNames = {"Nom", "Libellé", "Type", "Longueur", "Obligatoire", "Existance", "Editable", "Case", "Table", "Lien", "Colonne Lien", "Lien editable"};
        private Object[][] data = {{"Nom", "Libellé", "Type", new Integer(20), new Boolean(true), new Boolean(true), new Boolean(true), new Boolean(false), new Boolean(true), "", "", new Boolean(false)}};
        public final Object[] initValues = {"Nom", "Libellé", "Type", new Integer(20), new Boolean(true), new Boolean(true), new Boolean(true), new Boolean(false), new Boolean(true), "", "", new Boolean(false)};

        //Méthode permettant de retirer une ligne du tableau
        public void removeRow(int position) {
            int indice = 0, indice2 = 0;
            int nbRow = this.getRowCount() - 1;
            int nbCol = this.getColumnCount();
            Object temp[][] = new Object[nbRow][nbCol];

            for (Object[] value : this.data) {
                if (indice != position) {
                    temp[indice2++] = value;
                }
                System.out.println("Indice = " + indice);
                indice++;
            }
            this.data = temp;
            temp = null;
            this.fireTableDataChanged();
        }

        //Permet d'ajouter une ligne dans le tableau
        public void addRow(Object[] d) {
            int indice = 0, nbRow = this.getRowCount(), nbCol = this.getColumnCount();
            Object temp[][] = this.data;
            this.data = new Object[nbRow + 1][nbCol];
            for (Object[] value : temp) {
                this.data[indice++] = value;
            }
            this.data[indice] = d;
            temp = null;

            this.fireTableDataChanged();
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        public boolean isCellEditable(int row, int col) {
            if ((col == 10 || col == 11) && data[row][9].toString().isEmpty()) {
                return false;
            } else if (col == 7 && !data[row][9].toString().isEmpty() && !data[row][10].toString().isEmpty()) {
                return false;
            } else {
                return true;
            }
        }

        public Object[][] getData() {
            return data;
        }

        public void setValueAt(Object value, int row, int col) {
//            System.out.println("Setting value at " + row + "," + col + " to " + value + " (an instance of " + value.getClass() + ")");
            Object temp = data[row][col];
            data[row][col] = value;

            if (col == 9) {
                setValueAt(new Boolean(false), row, 8);
                setValueAt("", row, col + 1);
            }

            fireTableCellUpdated(row, col);
            System.out.println("New value of data:");
            printDebugData();
        }

        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();
            for (int i = 0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j = 0; j < numCols; j++) {
                    System.out.print("  " + data[i][j]);
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    }
}
