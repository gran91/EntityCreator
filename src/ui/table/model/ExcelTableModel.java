/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.table.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import zio.Excel;

/**
 *
 * @author JCHAUT
 */
class ExcelTableModel extends AbstractTableModel {

    private Object[][] data;
    private String[] title;
    private ArrayList<Integer> noChangeCol;
    private Excel xls;

    //Constructeur
    public ExcelTableModel(Object[][] data, String[] title) {
        this.data = data;
        this.title = title;
    }

    //Retourne le titre de la colonne à l'indice spécifié
    public String getColumnName(int col) {
        return this.title[col];
    }

    //Retourne le nombre de colonnes
    public int getColumnCount() {
        return this.title.length;
    }

    //Retourne le nombre de lignes
    public int getRowCount() {
        return this.data.length;
    }

    //Retourne la valeur à l'emplacement spécifié
    public Object getValueAt(int row, int col) {
        return this.data[row][col];
    }

    //Définit la valeur à l'emplacement spécifié
    public void setValueAt(Object value, int row, int col) {
        if (noChangeCol != null) {
            if(noChangeCol.contains(col))this.data[row][col] = value;
        }
    }

    //Retourne la classe de la donnée de la colonne
    public Class getColumnClass(int col) {
        return this.data[0][col].getClass();
    }

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
    public void addRow(Object[] data) {
        int indice = 0, nbRow = this.getRowCount(), nbCol = this.getColumnCount();
        Object temp[][] = this.data;
        this.data = new Object[nbRow + 1][nbCol];
        for (Object[] value : temp) {
            this.data[indice++] = value;
        }
        this.data[indice] = data;
        temp = null;
        this.fireTableDataChanged();
    }

    public boolean isCellEditable(int row, int col) {
        return true;
    }

    public ArrayList<Integer> getNoChangeCol() {
        return noChangeCol;
    }

    public void setNoChangeCol(ArrayList<Integer> noChangeCol) {
        this.noChangeCol = noChangeCol;
    }
}
