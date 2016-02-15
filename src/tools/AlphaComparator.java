/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

/**
 *
 * @author Jeremy.CHAUT
 */
public class AlphaComparator implements java.util.Comparator {

    private int column;

    public AlphaComparator(int column) {
        this.column = column;
    }

    public int compare(Object o1, Object o2) {
        return ((Comparable) ((Object[]) o1)[column]).compareTo(((Object[]) o2)[column]);
    }
}
