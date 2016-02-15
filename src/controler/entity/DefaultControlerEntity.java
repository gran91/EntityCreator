/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler.entity;

import controler.AbstractControler;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import static javax.swing.SwingUtilities.isEventDispatchThread;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import model.entity.IEntityModel;
import ui.entity.IUIEntity;

/**
 *
 * @author JCHAUT
 */
public class DefaultControlerEntity extends AbstractControler implements ActionListener, DocumentListener {

    private final IEntityModel model;
    private IUIEntity view;
    private AbstractControler controler;

    public DefaultControlerEntity(IEntityModel m, IUIEntity w) {
        model = m;
        view = w;
        controler = this;
        if (isEventDispatchThread() || EventQueue.isDispatchThread()) {
            view.load();
            view.loadData();
            view.show();
            view.control(controler);
        } else {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    view.load();
                    view.loadData();
                    view.show();
                    view.control(controler);
                }
            });
        }
    }

    public DefaultControlerEntity(IUIEntity w) {
        model = w.getModelEntity();
        view = w;
        controler = this;
        if (isEventDispatchThread() || EventQueue.isDispatchThread()) {
            view.load();
            view.loadData();
            view.show();
            view.control(controler);
        } else {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    view.load();
                    view.loadData();
                    view.show();
                    view.control(controler);
                }
            });
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        String act = e.getActionCommand();
        if (act.equals("Apply")) {
            if (model.checkData(view.getData())) {
                model.setData(view.getData());
                if (model.add()) {
                    model.saveData();
                }
            } else {
            }
        } else if (act.equals("OK")) {
            if (model.checkData(view.getData())) {
                model.setData(view.getData());
                if (model.add()) {
                    model.saveData();
                }
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        view.close();
                    }
                });
            }
        } else if (act.equals("Cancel")) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    view.close();
                }
            });
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }

//    public IUIEntity getView() {
//        return view;
//    }
//
//    public void setView(IUIEntity view) {
//        this.view = view;
//    }
    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
