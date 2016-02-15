/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

/**
 *
 * @author Jeremy.CHAUT
 */
public class IntegerComparator implements java.util.Comparator {
      private int column;

    public IntegerComparator(int column) {
        this.column = column;
    }

    public int compare(Object o1, Object o2) {
        return ((Comparable) (Integer)((Object[]) o1)[column]).compareTo((Integer)((Object[]) o2)[column]);
    }
}
