package tabpane;

//Importation des packages n�cessaires
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import org.jdesktop.swingx.JXFrame;


/**
 * @author sNiPeR91
 * @version 1.0
 */
public class JMyTabbedPane extends JTabbedPane {
	private static final long serialVersionUID = 1L;
	
	private Icon myIcon;
	private SkinTabbedPaneUI ui = null;
        private Rectangle rect;
        private JMyTabbedPane tab;
        private JXFrame g=null;
        
        public JMyTabbedPane(boolean draganddrog) {
		super();
                this.tab=this;
                jbinit(false, draganddrog,null);
	}
	
	public JMyTabbedPane(boolean icon, boolean draganddrog) {
		super();
                this.tab=this;
		jbinit(icon, draganddrog,null);
	}

        public JMyTabbedPane(boolean icon, boolean draganddrog, Icon ic) {
		super();
		this.tab=this;
                jbinit(icon, draganddrog,ic);
	}
        
        public JMyTabbedPane(boolean icon, boolean draganddrog, Icon ic,JXFrame g) {
		super();
                this.tab=this;
                this.g=g;
		jbinit(icon, draganddrog,ic);
	}
        
        
	public void addTab(String title, Component component) {
		if(myIcon == null){
			super.addTab(title, component);
		}
		else{
			super.addTab(title, myIcon, component);
		}
	}

	public void addTab(String title, Icon icon, Component component, String tip) {
		if(myIcon == null){
			super.addTab(title, icon, component, tip);
		}
		else{
			super.addTab(title, myIcon, component, tip);
		}
	}

	public void addTab(String title, Icon icon, Component component) {
		if(myIcon == null){
			super.addTab(title, icon, component);
		}
		else{
			super.addTab(title, myIcon, component);
		}
	}
	
	public Icon getIcon() {
		return myIcon;
	}
	
	public int getIconHeight() {
		return myIcon.getIconHeight();
	}
	
	public int getIconWidth() {
		return myIcon.getIconWidth();
	}
	
	private Image getImage() {
		BufferedImage img = new BufferedImage(12, 12, BufferedImage.BITMASK);
		Graphics2D g2d = img.createGraphics();
		
		g2d.setColor(Color.white);
		g2d.fill3DRect(0, 0, 12, 12, true);
		
		g2d.setColor(Color.black);
		g2d.drawLine(2, 2, 8, 8);
		
		g2d.setColor(Color.black);
		g2d.drawLine(2, 8, 8, 2);
		
		g2d.setColor(Color.white);
		g2d.fill3DRect(0, 0, 12, 12, true);
		
		g2d.setColor(Color.black);
		g2d.drawLine(2, 2, 8, 8);
		
		g2d.setColor(Color.black);
		g2d.drawLine(2, 8, 8, 2);
		
		g2d.dispose();
		
		return img;
	}
	
	/**M�thode retournant l'indice
	 * @param tabbedpane JTabbedPane associ�e
	 * @param x Coordonn�e X
	 * @param y Coordonn�e Y
	 * @return Retourne l'indice
	 */
	public static int indexOfChild(JTabbedPane tabbedpane, int x, int y) {
		for(int i = 0; i < tabbedpane.getTabCount(); ++i){
			Rectangle r = tabbedpane.getBoundsAt(i);
			
			if (!r.contains(x, y))
				continue;
			return i;
		}
		return -1;
	}
	
	/**M�thode permettant d'initialiser
	 * @param icon Gestion de la fermeture
	 * @param draganddrop Gestion du Drag and Drog
	 */
	private void jbinit(boolean icon, boolean draganddrop, Icon ic) {
		if(this.g!=null){
                    this.g=g;
                }
                if(icon){
                    if(ic!=null){
                        myIcon=ic;
                        //ui = new SkinTabbedPaneUI();
			//this.setUI(ui);
                    }else{
                        myIcon = new ImageIcon(getImage());
                        //ui = new SkinTabbedPaneUI();
			//this.setUI(ui);
                    }
			
                        this.addMouseListener( new MouseAdapter() {
				public void mouseReleased(MouseEvent e){
                                    rect=getBoundsAt(getSelectedIndex());
                                    Rectangle r=new Rectangle((int)rect.getMinX(),0,myIcon.getIconWidth(),myIcon.getIconHeight());
                                    if (!e.isConsumed() && isEnabledAt(getSelectedIndex()) && r.contains(e.getX(),e.getY())&& getTabCount()>1){
						remove(getSelectedIndex());
						e.consume();
                                                if(g!=null && getSelectedIndex()==-1){
                                                    g.remove(tab);
                                                    tab=null;
                                                    g.repaint();
                                                }
				     }	
                                    
                                    
				}
			});
		}

		if(draganddrop){
			this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
			this.setMinimumSize(new Dimension(1, 1));
			this.setPreferredSize(new Dimension(1, 1));
			
	        DragSource dragsource = DragSource.getDefaultDragSource ();
	        dragsource.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_MOVE,
	            new TabbedDragGesture(this));
	        
	        new DropTarget (this, new TabbedDropTarget(this));
		}
	}	
}