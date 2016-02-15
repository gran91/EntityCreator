package tabpane;

//Importation des packages n�cessaires
import java.awt.Component;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

import javax.swing.JTabbedPane;

/**
* @author sNiPeR91
 */
public class TabbedDragGesture implements DragGestureListener {
	//Variables globales
	private JTabbedPane tabbedpane;
	
	/**Constructeur
	 * @param tabbedpane JTabbedPane utilis�
	 */
	public TabbedDragGesture(JTabbedPane tabbedpane) {
		this.tabbedpane = tabbedpane;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.dnd.DragGestureListener#dragGestureRecognized(java.awt.dnd.DragGestureEvent)
	 */
	public void dragGestureRecognized(DragGestureEvent e) {
		InputEvent inputEvent = e.getTriggerEvent();
		if ((inputEvent instanceof MouseEvent)){
			
			MouseEvent mouseEvent = (MouseEvent) inputEvent;
			
			int x = mouseEvent.getX();
			int y = mouseEvent.getY();
			
			int index = JMyTabbedPane.indexOfChild(tabbedpane, x, y);
			
			if (index != -1){				
				Component child = tabbedpane.getComponentAt(index);
				String title = tabbedpane.getTitleAt(index);
				
				Transferable transferable = new TabbedTransferable (
						tabbedpane, index, child, title);
				e.startDrag(
						DragSource.DefaultMoveDrop,
						transferable,
						new TabbedDragSource(tabbedpane));
			}
		}
	}
}
