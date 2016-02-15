package tabpane;

//Importation des packages n�cessaires
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 * @author sNiPeR91
 */
public class MyBasicTabbedPaneUI extends BasicTabbedPaneUI {
	//Variables globales
        private Icon icone;
	private boolean ownImage;
	private Vector listHeader = new Vector();
	
	/**Constructeur
	 * @param ownImage gestion de notre propre image ?
	 */
	public MyBasicTabbedPaneUI(boolean ownImage) {
		super();
		this.ownImage = ownImage;
	}
        
        public MyBasicTabbedPaneUI(boolean ownImage,Icon icone) {
		super();
		this.ownImage = ownImage;
                this.icone=icone;
	}
	
	/**M�thode retournant la surface d'un icone
	 * @param index Indice
	 * @return Retourne la surface
	 */
	public Rectangle getIconArea(int index) {
		return (Rectangle)listHeader.get(index);
	}
	
	/**M�thode retournant les surfaces des ic�nes
	 * @return Retourne les surfaces
	 */
	public Vector getIconAreas() {
		return listHeader;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicTabbedPaneUI#paint(java.awt.Graphics, javax.swing.JComponent)
	 */
	public void paint(Graphics g, JComponent c) {
		listHeader.removeAllElements();
		super.paint(g, c);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.plaf.basic.BasicTabbedPaneUI#paintIcon(java.awt.Graphics, int, int, javax.swing.Icon, java.awt.Rectangle, boolean)
	 */
	protected void paintIcon(Graphics g, int tabPlacement, int tabIndex, Icon icon, Rectangle iconRect, boolean isSelected) {
		if(icon != null && ownImage){
			super.paintIcon(g, tabPlacement, tabIndex, icon, iconRect, isSelected);
			listHeader.add(iconRect);
		}
                if( ownImage && icone!=null){
                    iconRect.setSize(icone.getIconWidth(), icone.getIconHeight());
			super.paintIcon(g, tabPlacement, tabIndex, icone, iconRect, isSelected);
			listHeader.add(iconRect);
		}
	}
}
