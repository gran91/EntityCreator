/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package management.ui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.util.*;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import main.Ressource;
import org.oxbow.swingbits.table.filter.TableRowFilterSupport;
import ui.panel.model.PanelButton;

public class UITableLanguage extends JPanel {

    private Object[][] allLng;
    private PanelButton pbutton = new PanelButton(PanelButton.BT_ADD_DEL);
    private JTable table;
    private MyTableModel model;
    private JScrollPane scrollPane;
    private UIManageLanguage mainPanel;

    public UITableLanguage(UIManageLanguage m) {
        super(new BorderLayout());
        mainPanel = m;
        pbutton.bAdd.setActionCommand("ADD_LABEL");
        pbutton.bAdd.addActionListener(mainPanel);
        pbutton.bDel.setActionCommand("DEL_LABEL");
        pbutton.bDel.addActionListener(mainPanel);
        add(pbutton, BorderLayout.NORTH);
        model = new MyTableModel();
        table = new JTable(model);
        TableRowFilterSupport.forTable(table).searchable(true).apply();
        table.setPreferredScrollableViewportSize(new Dimension(1000, 70));
        table.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(table);
        initColumnSizes(table);
        table.setRowHeight(20);
        allLng = i18n.Language.getAllLabel();

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Integer.class, centerRenderer);

//        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
//        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
//        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initColumnSizes(JTable table) {
        model = (MyTableModel) table.getModel();
        TableColumn column = null;
        Component comp = null;
        int headerWidth = 0;
        int cellWidth = 0;
        Object[] longValues = model.initValues;
        TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
        /*
         * for (int i = 0; i < table.getColumnCount(); i++) { column =
         * table.getColumnModel().getColumn(i); comp =
         * headerRenderer.getTableCellRendererComponent(null,
         * column.getHeaderValue(), false, false, 0, 0); headerWidth =
         * comp.getPreferredSize().width; comp =
         * table.getDefaultRenderer(model.getColumnClass(i)).getTableCellRendererComponent(table,
         * longValues[i], false, false, 0, i); cellWidth =
         * comp.getPreferredSize().width; System.out.println("Initializing width
         * of column " + i + ". " + "headerWidth = " + headerWidth + ";
         * cellWidth = " + cellWidth);
         * column.setPreferredWidth(Math.max(headerWidth, cellWidth)); }
         */
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

    class MyTableModel extends AbstractTableModel {

        private ArrayList<String> existLng = new ArrayList<String>();
        private ArrayList<String> existLngName = new ArrayList<String>();
        private ArrayList<String> allCodeLng = new ArrayList<String>();
        private ArrayList<String> allLng = new ArrayList<String>();
        private String[] columnNames = null;
        private Object[][] data = {{"Nom", "Libellé", "Type", new Integer(20), new Boolean(true), new Boolean(true), new Boolean(true), new Boolean(false), "", "", new Boolean(false)}};
        public final Object[] initValues = null;
        private int lastId = -1;
        private ArrayList<Object> listId = new ArrayList<Object>();

        public MyTableModel() {
            existLng();
            retrieveAllLng();
            loadData();
            buildColumn();
        }

        private void buildColumn() {
            columnNames = new String[existLng.size() + 1];
            columnNames[0] = "ID";
            for (int i = 0; i < existLng.size(); i++) {
                columnNames[i + 1] = existLngName.get(i);
            }
        }

        public void addColumn(String s) {
            existLngName.add(s);
            existLng.add(s.substring(0, 2));
            buildColumn();

            int indice = 0, nbRow = this.getRowCount(), nbCol = this.getColumnCount();
            Object temp[][] = this.data;
            this.data = new Object[nbRow][nbCol];
            for (Object[] value : temp) {
                value = Arrays.copyOf(value, nbCol);
                value[nbCol - 1] = value[1];
                this.data[indice++] = value;
            }
            temp = null;
            this.fireTableDataChanged();
            fireTableStructureChanged();
            if (i18n.Language.createLanguageFile(s.substring(0, 2))) {
                for (int i = 0; i < nbRow; i++) {
                    i18n.Language.setLabel(getValueAt(i, 0).toString(), s.substring(0, 2), getValueAt(i, nbCol - 1).toString());
                }
            }
        }

        private void loadData() {
            listId.clear();
            if (existLng.isEmpty()) {
                data = new Object[1][1];
            } else {
                Object[][] s = i18n.Language.getAllLabel(existLng.get(1));
                java.util.Arrays.sort(s, new tools.IntegerComparator(1));
                listId = new ArrayList<Object>(Arrays.asList(tools.Tools.extractColumn(s, 1)));
                data = new Object[s.length][existLng.size() + 1];
                lastId = (Integer) s[s.length - 1][1];
                for (int i = 0; i < existLng.size(); i++) {
                    s = i18n.Language.getAllLabel(existLng.get(i));
                    java.util.Arrays.sort(s, new tools.IntegerComparator(1));
                    for (int j = 0; j < s.length; j++) {
                        if (i == 0) {
                            data[j][0] = s[j][1];
                        }
                        data[j][i + 1] = s[j][0];
                    }
                }
            }
            this.fireTableDataChanged();
        }

        //Méthode permettant de retirer une ligne du tableau
        public void removeRow(int position) {
            int indice = 0, indice2 = 0;
            int nbRow = this.getRowCount() - 1;
            int nbCol = this.getColumnCount();
            int id = Integer.parseInt(getValueAt(position, 0).toString().trim());
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
            fireTableDataChanged();
            for (int i = 1; i < getColumnCount(); i++) {
                i18n.Language.removeLabel(id, columnNames[i].substring(0, 2));
            }
        }

        //Méthode permettant de retirer une ligne du tableau
        public void removeRows(Integer[] position) {
            int indice = 0, indice2 = 0;
            int nbRow = this.getRowCount() - 1;
            int nbCol = this.getColumnCount();
            int id = -1;
            Object temp[][] = new Object[nbRow][nbCol];
            ArrayList<Integer> aPos = new ArrayList<Integer>(Arrays.asList(position));
            for (Object[] value : this.data) {
                if (!aPos.contains(indice)) {
                    temp[indice2++] = value;
                } else {
                    id = Integer.parseInt(getValueAt(indice, 0).toString().trim());
                    listId.remove(new Integer(id));
                    for (int i = 1; i < getColumnCount(); i++) {
                        i18n.Language.removeLabel(id, columnNames[i].substring(0, 2));
                    }
                }
                System.out.println("Indice = " + indice);
                indice++;
            }
            this.data = temp;
            temp = null;
            fireTableDataChanged();
        }

        //Permet d'ajouter une ligne dans le tableau
        public void addRow() {
            int indice = 0, nbRow = this.getRowCount(), nbCol = this.getColumnCount();
            Object temp[][] = this.data;
            this.data = new Object[nbRow + 1][nbCol];
            for (Object[] value : temp) {
                this.data[indice++] = value;
            }
            Object[] d = new Object[nbCol];
            lastId++;
            d[0] = lastId;
            for (int i = 1; i < d.length; i++) {
                d[i] = "";
                i18n.Language.setLabel(lastId, columnNames[i].substring(0, 2), d[i].toString());
            }
            this.data[indice] = d;
            listId.add(lastId);
            temp = null;

            this.fireTableDataChanged();
            scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
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
//            if (col == 0) {
//                return false;
//            } else {
//                return true;
//            }
            return true;
        }

        public Object[][] getData() {
            return data;
        }

        public void setValueAt(Object value, int row, int col) {
            Object temp = data[row][col];
            if (col == 0) {
                if (!listId.contains(value)) {
                    listId.remove(temp);
                    listId.add((Integer) value);
                    data[row][col] = value;
                    for (int i = 1; i < getColumnCount(); i++) {
                        i18n.Language.setLabel(value.toString(), columnNames[i].substring(0, 2), getValueAt(row, i).toString());
                        i18n.Language.removeLabel(temp.toString(), columnNames[i].substring(0, 2));

                    }
                }
                loadData();
            } else {
                data[row][col] = value;
                i18n.Language.setLabel(getValueAt(row, 0).toString(), columnNames[col].substring(0, 2), getValueAt(row, col).toString());
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

        private void existLng() {
            existLng.clear();
            existLngName.clear();
            File f = new File(Ressource.XML_LNG);
            File[] lstFile = null;
            if (f.exists() && f.isDirectory()) {
                lstFile = f.listFiles();
            } else {
                f.mkdirs();
            }

            if (lstFile != null) {
                for (int i = 0; i < lstFile.length; i++) {
                    existLng.add(lstFile[i].getName().substring(8, 10));
                    Locale l = new Locale(lstFile[i].getName().substring(8, 10));
                    existLngName.add(lstFile[i].getName().substring(8, 10) + " (" + l.getDisplayLanguage() + ")");
                }
            }
        }

        private void retrieveAllLng() {
            existLng();
            Locale[] lstLocal = Locale.getAvailableLocales();
            allCodeLng.add("");
            allLng.add("");
            for (int i = 0; i < lstLocal.length; i++) {
                if (!allCodeLng.contains(lstLocal[i].getLanguage().toUpperCase()) && !existLng.contains(lstLocal[i].getLanguage().toUpperCase())) {
                    allCodeLng.add(lstLocal[i].getLanguage().toUpperCase());
                    allLng.add(lstLocal[i].getLanguage().toUpperCase() + " (" + lstLocal[i].getDisplayLanguage() + ")");
                }
            }
            Collections.sort(allCodeLng);
            Collections.sort(allLng);
        }

        public ArrayList<String> getAllCodeLng() {
            return allCodeLng;
        }

        public void setAllCodeLng(ArrayList<String> allCodeLng) {
            this.allCodeLng = allCodeLng;
        }

        public ArrayList<String> getAllLng() {
            return allLng;
        }

        public void setAllLng(ArrayList<String> allLng) {
            this.allLng = allLng;
        }

        public String[] getColumnNames() {
            return columnNames;
        }

        public void setColumnNames(String[] columnNames) {
            this.columnNames = columnNames;
        }

        public ArrayList<String> getExistLng() {
            return existLng;
        }

        public void setExistLng(ArrayList<String> existLng) {
            this.existLng = existLng;
        }

        public ArrayList<String> getExistLngName() {
            return existLngName;
        }

        public void setExistLngName(ArrayList<String> existLngName) {
            this.existLngName = existLngName;
        }
    }
}