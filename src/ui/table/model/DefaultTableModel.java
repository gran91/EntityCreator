/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.table.model;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author JCHAUT
 */
public class DefaultTableModel extends AbstractTableModel {

    private Object[][] data;
    private String[] title;

    //Constructeur
    public DefaultTableModel(Object[][] data, String[] title) {
        this.data = data;
        this.title = title;
    }
    
    public void setColumns(String[] s){
        title=s;
    }

    //Retourne le titre de la colonne à l'indice spécifié
    public String getColumnName(int col) {
        return this.title[col];
    }

    //Retourne le nombre de colonnes
    public int getColumnCount() {
//        if (data == null) {
//            return 0;
//        }
        return this.title.length;
    }

    //Retourne le nombre de lignes
    public int getRowCount() {
        if (data == null) {
            return 0;
        }
        return this.data.length;
    }

    //Retourne la valeur à l'emplacement spécifié
    public Object getValueAt(int row, int col) {
        return this.data[row][col];
    }

    //Définit la valeur à l'emplacement spécifié
    public void setValueAt(Object value, int row, int col) {
        this.data[row][col] = value;

    }

    //Retourne la classe de la donnée de la colonne
    public Class getColumnClass(int col) {
        if (data == null || data.length==0) {
            return String.class;
        }
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
        return false;
    }

    public void setData(Object[][] o) {
        data = o;
    }
}
