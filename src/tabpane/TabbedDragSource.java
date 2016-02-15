package tabpane;

import java.awt.Component;
import java.awt.Point;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;

import javax.swing.JRootPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

/**
* @author sNiPeR91
 */
public class TabbedDragSource implements DragSourceListener {
	//Variables globales
	private final JTabbedPane tabbedpane;
	
	/**Constructeur
	 * @param tabbedpane JtabbedPane associ�
	 */
	public TabbedDragSource(JTabbedPane tabbedpane) {
		this.tabbedpane = tabbedpane;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.dnd.DragSourceListener#dragEnter(java.awt.dnd.DragSourceDragEvent)
	 */
	public void dragEnter(DragSourceDragEvent dsde) {
	}
	
	/* (non-Javadoc)
	 * @see java.awt.dnd.DragSourceListener#dragOver(java.awt.dnd.DragSourceDragEvent)
	 */
	public void dragOver(DragSourceDragEvent dsde) {
		Point vp = new Point (dsde.getX(), dsde.getY());
		
		JRootPane rootpane = (JRootPane) SwingUtilities.getAncestorOfClass(
				JRootPane.class, tabbedpane);
		
		if (rootpane == null){
			dsde.getDragSourceContext().setCursor (DragSource.DefaultMoveNoDrop);
		} 
		else{
			SwingUtilities.convertPointFromScreen (vp, rootpane);
			
			Component c = SwingUtilities.getDeepestComponentAt (rootpane, vp.x, vp.y);
			
			if (c != null && !(c instanceof JTabbedPane)){
				c = SwingUtilities.getAncestorOfClass(JTabbedPane.class, c);
			}
			
			if (!(c instanceof JTabbedPane)){
				dsde.getDragSourceContext().setCursor (DragSource.DefaultMoveNoDrop);
			}
			else{
				dsde.getDragSourceContext().setCursor (DragSource.DefaultMoveDrop);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.dnd.DragSourceListener#dragExit(java.awt.dnd.DragSourceEvent)
	 */
	public void dragExit(DragSourceEvent dse){
		dse.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.dnd.DragSourceListener#dropActionChanged(java.awt.dnd.DragSourceDragEvent)
	 */
	public void dropActionChanged(DragSourceDragEvent dsde){
	}
	
	/* (non-Javadoc)
	 * @see java.awt.dnd.DragSourceListener#dragDropEnd(java.awt.dnd.DragSourceDropEvent)
	 */
	public void dragDropEnd(DragSourceDropEvent dsde){
		dsde.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop);
		
	}
}
