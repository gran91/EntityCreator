package tabpane;

//Importation des packages n�cessaires
import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JTabbedPane;

/**
* @author sNiPeR91
 */
public class TabbedTransferable implements Transferable {
	//Variables globales
	public static final DataFlavor DATAFLAVOR;
	private JTabbedPane source;
	private int index;
	private Component component;
	private String title;
	
	static{
		try{
			DATAFLAVOR = new DataFlavor (
					DataFlavor.javaJVMLocalObjectMimeType
					+ ";class=" + TabbedTransferable.class.getName());
		}
		catch (ClassNotFoundException cnfe){
			throw new IllegalStateException ("class missing for data flavor.");
		}
	} 
	
	/**Constructeur
	 * @param source JTabbedPane associ�
	 * @param index Indice
	 * @param component Composant
	 * @param title Titre
	 */
	public TabbedTransferable(JTabbedPane source, int index, Component component, String title) {
		this.source = source;
		this.index = index;
		this.component = component;
		this.title = title;
	}
	
	/**M�thode retournant le JTabbedPane
	 * @return Retourne le JTabbedPane
	 */
	public JTabbedPane getSource() {
		return source;
	}
	
	/**M�thode retournant l'indice
	 * @return Retourne l'indice
	 */
	public int getIndex() {
		return index;
	}
	
	/**M�thode retournant le composant
	 * @return Retourne le composant
	 */
	public Component getComponent() {
		return component;
	}
	
	/**M�thode retourant le titre
	 * @return Retourne le titre
	 */
	public String getTitle() {
		return title;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.datatransfer.Transferable#getTransferDataFlavors()
	 */
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { DATAFLAVOR };
	}
	
	/* (non-Javadoc)
	 * @see java.awt.datatransfer.Transferable#isDataFlavorSupported(java.awt.datatransfer.DataFlavor)
	 */
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.equals (DATAFLAVOR);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.datatransfer.Transferable#getTransferData(java.awt.datatransfer.DataFlavor)
	 */
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException
	{
		if (!flavor.equals (DATAFLAVOR)) {
			throw new UnsupportedFlavorException(flavor);
		}
		
		return this;
	}
}
