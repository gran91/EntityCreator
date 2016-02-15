/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler.entities;

import controler.AbstractControler;
import controler.entity.XMLControlerEntity;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.SwingUtilities;
import static javax.swing.SwingUtilities.isEventDispatchThread;
import javax.swing.event.DocumentEvent;
import model.entities.IEntitiesModel;
import model.entity.XMLEntityModel;
import ui.entities.IUIEntities;
import ui.entity.XMLUIEntity;

/**
 *
 * @author JCHAUT
 */
public class DefaultControlerEntities extends AbstractControler implements ActionListener, MouseListener {

    protected final IEntitiesModel model;
    protected IUIEntities view;
    protected DefaultControlerEntities controler;

    public DefaultControlerEntities(IEntitiesModel m, IUIEntities w) {
        controler = this;
        model = m;
        view = w;
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

    public DefaultControlerEntities(IUIEntities w) {
        controler = this;
        model = w.getModel();
        view = w;
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

    private void reloadAll() {
        view.loadData();
    }

    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            view.getTableView().getPopup().show(e.getComponent(),
                    e.getX(), e.getY());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        String act = e.getActionCommand();
        switch (act) {
            case "add":
                addEntity();
                break;
            case "del":
                delEntity();
                break;
            case "OK":
                reloadAll();
                break;
            case "show":
                modifyEntity(0);
                break;
            case "modify":
                modifyEntity(1);
                break;
            case "changeLink":
                model.setIdLink(view.getIdLink());
                view.loadData();
                break;
            case "change":
                view.loadData();
                break;
        }
    }

    protected void addEntity() {
        view.addEntity();
        view.getViewEntity().control(controler);
    }

    protected void delEntity() {
        view.delEntity();
        reloadAll();
    }

    protected void modifyEntity(int n) {
        view.modifyEntity(n);
        if (view.getModelEntity() != null) {
            XMLUIEntity v = new XMLUIEntity((XMLEntityModel) view.getModelEntity());
            v.setShowType(n);
            XMLControlerEntity c = new XMLControlerEntity(v);
            v.control(controler);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
            modifyEntity(0);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        if (e.getButton() == MouseEvent.BUTTON3) {
//            int nb_sel_rows = view.getTableView().getSelectedRowCount();
//            if (nb_sel_rows < 2) {
//                view.getTableView().changeSelection(view.getTableView().rowAtPoint(e.getPoint()), 0, false, false);
//            }
//            if (view.getTableView().getPopup() != null) {
//                view.getTableView().getPopup().loadElementPopUp();
//                maybeShowPopup(e);
//            }
//        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            int nb_sel_rows = view.getTableView().getSelectedRowCount();
            if (nb_sel_rows < 2) {
                view.getTableView().changeSelection(view.getTableView().rowAtPoint(e.getPoint()), 0, false, false);
            }
            if (view.getTableView().getPopup() != null) {
                view.getTableView().getPopup().loadElementPopUp();
                maybeShowPopup(e);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public IUIEntities getView() {
        return view;
    }

    public void setView(IUIEntities view) {
        this.view = view;
    }
}
