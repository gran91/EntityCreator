/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import controler.entities.DefaultControlerEntities;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import static javax.swing.SwingUtilities.isEventDispatchThread;
import javax.swing.event.DocumentEvent;
import model.entities.XMLEntitiesModel;
import ui.entities.XMLUITableEntities;
import ui.entity.DefaultUIEntity;
import ui.entity.IUIEntity;
import ui.popup.UIPopup;

/**
 *
 * @author JCHAUT
 */
public class MenuControlerEntity extends AbstractControler implements ActionListener {

    private IUIEntity view;
    private AbstractControler controler;

    public MenuControlerEntity(IUIEntity w) {
        view = w;
        controler = this;
        if (isEventDispatchThread() || EventQueue.isDispatchThread()) {
            view.load();
            view.control(controler);
        } else {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    view.load();
                    view.control(controler);
                }
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        action(e.getActionCommand());
    }

    public void action(String act) {
        if (act.startsWith("entities")) {
            String[] tab = act.split("_");
            if (tab.length > 1) {
                XMLUITableEntities viewTable = new XMLUITableEntities(new XMLEntitiesModel(tab[1]));
                viewTable.setContainerType(DefaultUIEntity.DIALOG);
                viewTable.setPopup(new UIPopup());
                new DefaultControlerEntities(viewTable);
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
