/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.entity;

import controler.AbstractControler;
import java.util.ArrayList;
import model.entity.IEntityModel;
import ui.IUIMain;

/**
 *
 * @author Jeremy.CHAUT
 */
public interface IUIEntity extends IUIMain{

    public void load();

    public IEntityModel getModelEntity();

    public void control(AbstractControler controller);

    public void loadData();

    public ArrayList<String> getData();

    public void show();

    public void showInDialog();

    public void showInFrame();

    public void setContainerType(int containerType);

    public void close();

    public void setIdLink(int id);

    public int getIdLink();

    public void setId(int id);

    public int getId();

    public void setShowType(int type);
    
    public void setTitle(java.lang.String t);
    
    public String getTitle();
}
