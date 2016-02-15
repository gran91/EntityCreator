/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;
import model.entities.IEntitiesModel;
import ui.utils.IUIList;

/**
 *
 * @author JCHAUT
 */
public class ControlerList implements  ActionListener {

    private final IEntitiesModel model;
    private IUIList view;

    public ControlerList(IEntitiesModel m, IUIList w) {
        model = m;
        view = w;
        view.load();
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                view.loadData();
            }
        });
        view.control(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        String act = e.getActionCommand();
        if (act.equals("add")) {
            view.getViewEntity().load();
            view.getViewEntity().showInDialog();        
        }
        if (act.equals("del")) {
        }
    }

    public IUIList getView() {
        return view;
    }

    public void setView(IUIList view) {
        this.view = view;
    }
}
