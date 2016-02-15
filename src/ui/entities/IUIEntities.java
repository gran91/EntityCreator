/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.entities;

import model.entities.IEntitiesModel;
import model.entity.IEntityModel;
import ui.entity.IUIEntity;
import ui.popup.UIPopup;
import ui.table.UITable;
import ui.table.model.DefaultTableModel;

/**
 *
 * @author Jeremy.CHAUT
 */
public interface IUIEntities extends IUIEntity {
    public IUIEntity getViewEntity();
    public void addModelEntityToUIEntity(IEntityModel e);
    public UITable getTableView();
    public DefaultTableModel getTableModel();
    public UIPopup getPopup();
    public void setPopup(UIPopup p);
    public void loadData(Object[][] o);
    public void addEntity();
    public void delEntity();
    public void modifyEntity(int type);
    public IEntitiesModel getModel();
}
