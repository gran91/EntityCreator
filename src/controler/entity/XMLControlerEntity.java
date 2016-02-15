/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler.entity;

import controler.AbstractControler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import ui.entity.IUIEntity;

/**
 *
 * @author JCHAUT
 */
public class XMLControlerEntity extends AbstractControler implements ActionListener, DocumentListener {

    private IUIEntity view;
    private AbstractControler controler;

    public XMLControlerEntity(IUIEntity w) {
        view = w;
        controler = this;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        String act = e.getActionCommand();
        if (act.equals("Apply")) {
            if (view.getModelEntity().checkData(view.getData())) {
                view.getModelEntity().setData(view.getData());
                if (view.getModelEntity().add()) {
                    view.getModelEntity().saveData();
                }
            } else {
            }
        } else if (act.equals("OK")) {
            if (view.getModelEntity().checkData(view.getData())) {
                view.getModelEntity().setData(view.getData());
                if (view.getModelEntity().add()) {
                    view.getModelEntity().saveData();
                }
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        view.close();
                    }
                });
            }else{
            //TODO
            }
        } else if (act.equals("Cancel")) {
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    view.close();
                }
            });
        }else if (act.equals("changelink")) {
            view.getModelEntity().setIdLink(view.getIdLink());
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
