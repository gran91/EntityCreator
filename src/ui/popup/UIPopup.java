/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.popup;

import controler.AbstractControler;
import java.awt.event.KeyEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

/**
 *
 * @author Jeremy.CHAUT
 */
public class UIPopup extends JPopupMenu {

    private JMenuItem itemDel, itemAdd, itemShow, itemModify;
    private AbstractControler controler;

    public UIPopup() {
        super();
    }

    public void loadElementPopUp() {
        removeAll();
        addDefault();
        control();
    }

    public void setControler(AbstractControler c) {
        controler = c;
    }

    public void control() {
        itemDel.addActionListener(controler);
        itemAdd.addActionListener(controler);
        itemShow.addActionListener(controler);
        itemModify.addActionListener(controler);
    }

    public void addDel() {
        itemDel = new JMenuItem(i18n.Language.getLabel(2));
        itemDel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        itemDel.setActionCommand("del");
        add(itemDel);
    }

    public void addAdd() {
        itemAdd = new JMenuItem(i18n.Language.getLabel(1));
        itemAdd.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0));
        itemAdd.setActionCommand("add");
        add(itemAdd);
    }

    public void addShow() {
        itemShow = new JMenuItem(i18n.Language.getLabel(128));
        itemShow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
        itemShow.setActionCommand("show");
        add(itemShow);
    }

    public void addModify() {
        itemModify = new JMenuItem(i18n.Language.getLabel(15));
        itemModify.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.CTRL_MASK));
        itemModify.setActionCommand("modify");
        add(itemModify);
    }

    public void addDefault() {
        addAdd();
        addDel();
        addShow();
        addModify();
    }
}
