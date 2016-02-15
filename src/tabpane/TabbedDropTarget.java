package tabpane;

//Importation des packages n�cessaires
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;

import javax.swing.JTabbedPane;

/**
* @author sNiPeR91
 */
public class TabbedDropTarget implements DropTargetListener {
	//Variables globales
	private JTabbedPane tabbedpane;
	
	/**Constructeur
	 * @param tabbedpane JTabbedPane associ�
	 */
	public TabbedDropTarget(JTabbedPane tabbedpane) {
		this.tabbedpane = tabbedpane;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.dnd.DropTargetListener#dragEnter(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dragEnter(DropTargetDragEvent dtde) {
		if (dtde.isDataFlavorSupported(TabbedTransferable.DATAFLAVOR)){
			dtde.acceptDrag(DnDConstants.ACTION_MOVE);
		}
		else{
			dtde.rejectDrag();
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.dnd.DropTargetListener#dragExit(java.awt.dnd.DropTargetEvent)
	 */
	public void dragExit(DropTargetEvent dte) {
	}
	
	/* (non-Javadoc)
	 * @see java.awt.dnd.DropTargetListener#dragOver(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dragOver(DropTargetDragEvent dtde) {
		if (dtde.isDataFlavorSupported(TabbedTransferable.DATAFLAVOR)){
			dtde.acceptDrag(DnDConstants.ACTION_MOVE);
		}
		else{
			dtde.rejectDrag();
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.dnd.DropTargetListener#dropActionChanged(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dropActionChanged(DropTargetDragEvent dtde) {
	}
	
	/* (non-Javadoc)
	 * @see java.awt.dnd.DropTargetListener#drop(java.awt.dnd.DropTargetDropEvent)
	 */
	public void drop(DropTargetDropEvent dtde) {
		if (!dtde.isDataFlavorSupported (TabbedTransferable.DATAFLAVOR) ||
				dtde.getSourceActions() != DnDConstants.ACTION_MOVE) {
			dtde.rejectDrop();
		}
		else{
			
			try
			{
				TabbedTransferable windowSelection = (TabbedTransferable)
				dtde.getTransferable().getTransferData(TabbedTransferable.DATAFLAVOR);
				
				int index = JMyTabbedPane.indexOfChild(tabbedpane, dtde.getLocation().x, dtde.getLocation().y);
				
				if (tabbedpane == windowSelection.getSource() &&
						index == windowSelection.getIndex()){
					dtde.rejectDrop ();
				}
				else{
					windowSelection.getSource().removeTabAt(
							windowSelection.getIndex());
					
					int insertIndex = index == -1 ? tabbedpane.getTabCount() : index;
					tabbedpane.insertTab(
							windowSelection.getTitle(),
							null,
							windowSelection.getComponent(),
							null,
							insertIndex);
					
					tabbedpane.setSelectedIndex(insertIndex);
					
					dtde.acceptDrop (DnDConstants.ACTION_MOVE);
				}				
			}
			catch (IOException e){
				dtde.rejectDrop ();
			}
			catch (UnsupportedFlavorException e){
				dtde.rejectDrop ();
			}
		}
	}
}
