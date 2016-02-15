/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.utils;

import controler.utils.ControlerList;
import model.entities.IEntitiesModel;
import ui.entity.IUIEntity;

/**
 *
 * @author Jeremy.CHAUT
 */
public interface IUIList {
    public void control(ControlerList controller);
    public void load();
    public void loadData();
    public void loadData(int col);
    public void removeButton();
    public void setEnabled(boolean b);
    public IUIEntity getViewEntity();
    public IEntitiesModel getModel();
}
