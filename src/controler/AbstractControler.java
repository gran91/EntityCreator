/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.event.DocumentListener;
import ui.entities.IUIEntities;

/**
 *
 * @author Jeremy.CHAUT
 */
public abstract class AbstractControler implements ActionListener, DocumentListener, MouseListener {
    IUIEntities view;
    public IUIEntities getView() {
        return view;
    }
}
